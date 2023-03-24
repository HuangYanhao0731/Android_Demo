package com.example.monkey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn_peach; //去桃园按钮
    private TextView tv_cnt;
    private int totalCnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        View btn1 = findViewById(R.id.btn_enter);
        tv_cnt = findViewById(R.id.tv_cnt);
        //为"去桃园"按钮增加监听事件，点击这个按钮，跳转到桃园界面
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivityForResult(intent, 1001);
            }
        });
    }

    //  用来接收上个界面传过来的信息的
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            int cnt = data.getIntExtra("cnt", 0);
            totalCnt = totalCnt + cnt;
            tv_cnt.setText("摘了" + totalCnt + "桃子");
        }
    }
}