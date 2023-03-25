package com.example.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public ProductAdapter(List<Product> productList, OnProductClickListener onProductClickListener) {
        this.productList = productList;
        this.onProductClickListener = onProductClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice()));
        holder.quantityTextView.setText(String.valueOf(product.getQuantity()));
        holder.selectedCheckbox.setChecked(product.isSelected());

        // Handle checkbox click
        holder.selectedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setSelected(!product.isSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView priceTextView;
        public TextView quantityTextView;
        CheckBox selectedCheckbox;

        public ViewHolder(View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.product_name);
            priceTextView = itemView.findViewById(R.id.product_price);
            quantityTextView = itemView.findViewById(R.id.product_quantity);
            itemView.setOnClickListener(this);
            selectedCheckbox = itemView.findViewById(R.id.product_selected_checkbox);
        }

        @Override
        public void onClick(View v) {
            if (onProductClickListener != null) {
                onProductClickListener.onProductClick(productList.get(getAdapterPosition()));
            }
        }
    }
}

