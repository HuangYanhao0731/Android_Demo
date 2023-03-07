package com.example.index;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnCreateContextMenuListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private String mPassword = "123456";
    private String verifycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        RadioGroup rb_login= findViewById(R.id.rg_login);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        et_phone = findViewById(R.id.et_phone);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        btn_login = findViewById(R.id.btn_login);
        //给rg_login设置单选监听器
        rb_login.setOnCheckedChangeListener(this);
        //给et_phone设置文本监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone,11));
        //给btn_forget设置事件监听
        btn_forget.setOnClickListener(this);
        //给btn_login设置事件监听
        btn_login.setOnClickListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId){
        switch (checkedId){
            //选择了密码登录
            case R.id.rb_password:
                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ck_remember.setVisibility(View.VISIBLE);
                break;
            //选择了验证码登录
            case R.id.rb_verifycode:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                ck_remember.setVisibility(View.GONE);

        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

//    忘记密码监听
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String phone = et_phone.getText().toString();
        switch(view.getId()){
            case R.id.btn_forget:
                if(phone.length()<11){
                    Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                //选择了密码方式校验，此时要跳转到找回密码页面
                if(rb_password.isChecked()){
                    //以下携带手机号码跳转到找回密码页面
                    Intent intent = new Intent(this,LoginForgetActivity.class);
                    intent.putExtra("phone",phone);
                    register.launch(intent);

                }else if(rb_verifycode.isChecked()){
                    //生成随机验证码
                    verifycode = String.format("%06d",new Random().nextInt(999999));
                    //弹出对话框，提示用户记住六位数字
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号"+",本次验证码是"+verifycode+",请输入验证码");
                    builder.setPositiveButton("好的",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
                //点击登录
            case R.id.btn_login:
                //密码校验
                if (rb_password.isChecked()){
                    if(!mPassword.equals(et_password.getText().toString())){
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //提示用户登录成功
                    loginSucess();
                }else if(rb_verifycode.isChecked()){
                    if(!verifycode.equals(et_password.getText().toString())){
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //提示用户登录成功
                    loginSucess();
                }
                break;
        }
    }
//校验通过，登陆成功
    private void loginSucess() {
        String desc = String.format("您的手机号码是%s,恭喜你通过登陆验证，点击”确定“按钮返回上个页面",et_phone.getText().toString());
        //弹出对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回",(dialogInterface, which) ->{
            //结束当前活动页面
            finish();
        });
        builder.setNegativeButton("我再看看",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //隐藏软键盘
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        public HideTextWatcher(EditText v, int maxLength) {
            this.mView=v;
            this.mMaxLength=maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}