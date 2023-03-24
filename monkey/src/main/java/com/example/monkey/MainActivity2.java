package com.example.monkey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    private Button an;
    private int cnt = 0;  //摘桃子的个数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }
    public void initView() {
        imageView1 = findViewById(R.id.peach_1);
        imageView2 = findViewById(R.id.peach_2);
        imageView3 = findViewById(R.id.peach_3);
        imageView4 = findViewById(R.id.peach_4);
        imageView5 = findViewById(R.id.peach_5);
        imageView6 = findViewById(R.id.peach_6);
        an= findViewById(R.id.btn_exit);

        //监听器
        imageView1.setOnClickListener((View.OnClickListener) this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener((View.OnClickListener) this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        an.setOnClickListener(this);
    }


    public void onClick(View view) {
        //实现点击事件
        switch (view.getId()) {
            case R.id.peach_1:
                info(imageView1);
                break;
            case R.id.peach_2:
                info(imageView2);
                break;
            case R.id.peach_3:
                info(imageView3);
                break;
            case R.id.peach_4:
                info(imageView4);
                break;
            case R.id.peach_5:
                info(imageView5);
                break;
            case R.id.peach_6:
                info(imageView6);
                break;
            case R.id.btn_exit:
                returnData();
                break;
        }
    }

    private void returnData() {
        //将数据回传到上个界面
        Intent intent = new Intent();
        intent.putExtra("cnt", cnt);
        setResult(1002, intent);   //1是返回码
        MainActivity2.this.finish();
    }

    //桃子的点击事件处理
    private void info(ImageView imageView) {
        cnt++;
        imageView.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity2.this, "摘了" + cnt + "个桃子", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            returnData();
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}