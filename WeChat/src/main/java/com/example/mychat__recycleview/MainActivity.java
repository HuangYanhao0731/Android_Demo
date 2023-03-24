package com.example.mychat__recycleview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //添加日志的TAG常量
    private static final String TAG = "MainActivity";

    //Fragment
    private final Fragment fragment_first = new Fragment_chat();
    private final Fragment fragment_second = new Fragment_contacts();
    private final Fragment fragment_third = new Fragment_circle_friend();
    private final Fragment fragment_fourth = new Fragment_settings();

    //底端菜单栏LinearLayout
    private LinearLayout linear_first;
    private LinearLayout linear_second;
    private LinearLayout linear_third;
    private LinearLayout linear_fourth;

    //底端菜单栏中的Imageview
    private ImageView imageView_first;
    private ImageView imageView_second;
    private ImageView imageView_third;
    private ImageView imageView_fourth;

    //底端菜单栏中的TextView
    private TextView textView_first;
    private TextView textView_second;
    private TextView textView_third;
    private TextView textView_fourth;


    //FragmentManager
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
        initEvent();
        selectFragment(0);
        //将第一个图标设为选中状态
        imageView_first.setImageResource(R.drawable.chat_green);
        textView_first.setTextColor(getResources().getColor(R.color.colorViewPress));
    }

    @Override
    public void onClick(View view) {
        //每次点击之后，将所有的ImageView和TextView设置为未选中
        restartButton();

        switch (view.getId()) {
            case R.id.chat:
                //选择所点击的菜单对应的图层片段
                selectFragment(0);
                //将该菜单的点击状态置为点击态
                imageView_first.setImageResource(R.drawable.chat_green);
                textView_first.setTextColor(getResources().getColor(R.color.colorViewPress));
                //打印日志信息
                Log.d(TAG, "onClick: 切换为聊天界面成功！");
                break;
            case R.id.contacts:
                selectFragment(1);
                imageView_second.setImageResource(R.drawable.contacts_green);
                textView_second.setTextColor(getResources().getColor(R.color.colorViewPress));
                Log.d(TAG, "onClick: 切换为联系人界面成功！");
                break;
            case R.id.circle_friend:
                selectFragment(2);
                imageView_third.setImageResource(R.drawable.circle_people_green);
                textView_third.setTextColor(getResources().getColor(R.color.colorViewPress));
                Log.d(TAG, "onClick: 切换为朋友圈界面成功！");
                break;
            case R.id.settings:
                selectFragment(3);
                imageView_fourth.setImageResource(R.drawable.settings_green);
                textView_fourth.setTextColor(getResources().getColor(R.color.colorViewPress));
                Log.d(TAG, "onClick: 切换为设置界面成功！");
                break;
            default:
                break;
        }
    }

    //重置菜单的点击状态，设为未点击
    private void restartButton() {
        //设置为未点击状态
        //第一片段
        imageView_first.setImageResource(R.drawable.chat);
        textView_first.setTextColor(getResources().getColor(R.color.black));
        //第二片段
        imageView_second.setImageResource(R.drawable.contacts);
        textView_second.setTextColor(getResources().getColor(R.color.black));
        //第三片段
        imageView_third.setImageResource(R.drawable.circle_people);
        textView_third.setTextColor(getResources().getColor(R.color.black));
        //第四片段
        imageView_fourth.setImageResource(R.drawable.settings);
        textView_fourth.setTextColor(getResources().getColor(R.color.black));
    }

    //初始化中间的部分的图层片段
    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, fragment_first);
        transaction.add(R.id.frame_content, fragment_second);
        transaction.add(R.id.frame_content, fragment_third);
        transaction.add(R.id.frame_content, fragment_fourth);
        //提交事务
        transaction.commit();
    }

    //初始化各底端的LinearLayout、ImageView和TextView组件
    private void initView() {
        linear_first = findViewById(R.id.chat);
        linear_second = findViewById(R.id.contacts);
        linear_third = findViewById(R.id.circle_friend);
        linear_fourth = findViewById(R.id.settings);

        imageView_first = findViewById(R.id.imageView1);
        imageView_second = findViewById(R.id.imageView2);
        imageView_third = findViewById(R.id.imageView3);
        imageView_fourth = findViewById(R.id.imageView4);

        textView_first = findViewById(R.id.textView1);
        textView_second = findViewById(R.id.textView2);
        textView_third = findViewById(R.id.textView3);
        textView_fourth = findViewById(R.id.textView4);

    }

    //初始化点击监听事件
    private void initEvent() {
        linear_first.setOnClickListener(this);
        linear_second.setOnClickListener(this);
        linear_third.setOnClickListener(this);
        linear_fourth.setOnClickListener(this);
    }

    //隐藏所有图层分段
    private void hideView(FragmentTransaction transaction) {
        transaction.hide(fragment_first);
        transaction.hide(fragment_second);
        transaction.hide(fragment_third);
        transaction.hide(fragment_fourth);
    }

    //选择相应的图层分段
    private void selectFragment(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //调用隐藏所有图层函数
        hideView(transaction);
        switch (i) {
            case 0:
                transaction.show(fragment_first);
                Log.d(TAG, "selectFragment: 正在切换聊天界面！");
                break;
            case 1:
                transaction.show(fragment_second);
                Log.d(TAG, "selectFragment: 正在切换联系人界面！");
                break;
            case 2:
                transaction.show(fragment_third);
                Log.d(TAG, "selectFragment: 正在切换朋友圈界面！");
                break;
            case 3:
                transaction.show(fragment_fourth);
                Log.d(TAG, "selectFragment: 正在切换设置界面！");
                break;
            default:
                break;
        }
        //提交转换事务
        transaction.commit();
    }


}