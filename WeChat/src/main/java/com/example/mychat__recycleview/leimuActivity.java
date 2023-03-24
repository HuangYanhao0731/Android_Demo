package com.example.mychat__recycleview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class leimuActivity extends AppCompatActivity {
    //好友名字
    private TextView friend_name;
    //好友信息
    private TextView friend_info;
    //好友照片
    private ImageView friend_pic;
    //返回上个页面按钮
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leimu_tab);
        init();
        setListeners();
    }

    public void init() {
        friend_name = (TextView) findViewById(R.id.friend_name);
        friend_info = (TextView) findViewById(R.id.friend_info);
        friend_pic = (ImageView) findViewById(R.id.friend_pic);
        btn_back = (Button) findViewById(R.id.btn_back);
    }

    private void setListeners() {
        btn_back.setOnClickListener(backMain);
    }

    private Button.OnClickListener backMain = new Button.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            leimuActivity.this.finish();
        }
    };

}
