package com.example.shoppingcart;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private ProductDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ProductDBHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("ShoppingCartPreferences", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            addPredefinedProducts();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        }

        RecyclerView shoppingCartList = findViewById(R.id.shopping_cart_list);
        shoppingCartList.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productList, this);
        shoppingCartList.setAdapter(productAdapter);

        Button addItemButton = findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        Button viewCartButton = findViewById(R.id.view_cart_button);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartDialog();
            }
        });

        loadProducts();
    }


    private void addPredefinedProducts() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 商品列表
        List<Product> predefinedProducts = new ArrayList<>();
        predefinedProducts.add(new Product("iPhone 13", 1099.00, 1));
        predefinedProducts.add(new Product("Samsung Galaxy S22", 999.00, 1));
        predefinedProducts.add(new Product("Google Pixel 6", 899.00, 1));

        for (Product product : predefinedProducts) {
            ContentValues values = new ContentValues();
            values.put(ProductDBHelper.COLUMN_NAME, product.getName());
            values.put(ProductDBHelper.COLUMN_PRICE, product.getPrice());
            values.put(ProductDBHelper.COLUMN_QUANTITY, product.getQuantity());

            long newRowId = db.insert(ProductDBHelper.TABLE_NAME, null, values);
            if (newRowId == -1) {
                Toast.makeText(this, "添加商品失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addProduct() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductDBHelper.COLUMN_NAME, "商品" + (productList.size() + 1));
        values.put(ProductDBHelper.COLUMN_PRICE, 9.99);
        values.put(ProductDBHelper.COLUMN_QUANTITY, 1);

        long newRowId = db.insert(ProductDBHelper.TABLE_NAME, null, values);
        if (newRowId != -1) {
            loadProducts();
            Toast.makeText(this, "商品已添加", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "添加商品失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProducts() {
        productList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ProductDBHelper.COLUMN_ID,
                ProductDBHelper.COLUMN_NAME,
                ProductDBHelper.COLUMN_PRICE,
                ProductDBHelper.COLUMN_QUANTITY
        };

        Cursor cursor = db.query(
                ProductDBHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ProductDBHelper.COLUMN_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(ProductDBHelper.COLUMN_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndex(ProductDBHelper.COLUMN_QUANTITY));
            int id = cursor.getInt(cursor.getColumnIndex(ProductDBHelper.COLUMN_ID));
            productList.add(new Product(id, name, price, quantity));
        }
        cursor.close();
        productAdapter.notifyDataSetChanged();
    }


    @Override
    public void onProductClick(Product product) {
        showEditProductDialog(product);
    }

    private void showEditProductDialog(final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑商品");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameEditText = new EditText(this);
        nameEditText.setText(product.getName());
        layout.addView(nameEditText);

        final EditText priceEditText = new EditText(this);
        priceEditText.setText(String.valueOf(product.getPrice()));
        layout.addView(priceEditText);

        final NumberPicker quantityPicker = new NumberPicker(this);
        quantityPicker.setMinValue(1);
        quantityPicker.setMaxValue(100);
        quantityPicker.setValue(product.getQuantity());
        layout.addView(quantityPicker);

        builder.setView(layout);

        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = nameEditText.getText().toString();
                double newPrice = Double.parseDouble(priceEditText.getText().toString());
                int newQuantity = quantityPicker.getValue();

                Product updatedProduct = new Product(product.getId(), newName, newPrice, newQuantity);
                dbHelper.updateProduct(updatedProduct);
                loadProducts();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteProduct(product.getId());
                loadProducts();
            }
        });

        builder.show();
    }
    private void showCartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("购物车");

        StringBuilder cartItems = new StringBuilder();
        double total = 0;
        for (Product product : productList) {
            if (product.isSelected()) {
                cartItems.append(product.getName())
                        .append(" x ")
                        .append(product.getQuantity())
                        .append(" = ")
                        .append(String.format(Locale.getDefault(), "%.2f", product.getPrice() * product.getQuantity()))
                        .append("\n");
                total += product.getPrice() * product.getQuantity();
            }
        }

        cartItems.append("\n总计：").append(String.format(Locale.getDefault(), "%.2f", total));
        builder.setMessage(cartItems.toString());
        builder.setPositiveButton("确定", (dialog, which) -> dialog.dismiss());

        builder.setNegativeButton("清空购物车", (dialog, which) -> {
            for (Product product : productList) {
                product.setSelected(false);
            }
            productAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.show();
    }

}