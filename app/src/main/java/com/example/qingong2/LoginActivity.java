package com.example.qingong2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qingong2.R;
import com.example.qingong2.login.userData;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    //声明变量

//    private CircleImageView mimageView;//头像

    private Boolean login_state = false;//登录状态

    private TextView mregister;//

    private EditText account_edtTxt;//账号

    private EditText password_edtTxt;//密码

    private Button login_btn;//注册按钮

    private Button mconfirm;//登录按钮

    private ImageButton mimageButton;

    private userData data;

    private CheckBox checkBox;

    private String LOCAL_USER_NAME="chirmy";







    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //找布局控件对应ID
//        mimageView = findViewById(R.id.login_cimg_head);//头像
        account_edtTxt = findViewById(R.id.login_et_username);//账号
        password_edtTxt = findViewById(R.id.login_et_password);//密码
        mregister = findViewById(R.id.login_btn_register);//注册？？
        mconfirm = findViewById(R.id.login_btn_confirm);//登录
        checkBox = findViewById(R.id.login_cb_rm);//记住密码
        data = new userData(LoginActivity.this);//userData类



        initLogin();
        mregister.setOnClickListener(new View.OnClickListener() {//注册点击事件
            @Override
            public void onClick(View v) {
                if(data.getRegister(account_edtTxt.getText().toString(),password_edtTxt.getText().toString()))//注册成功
                {
                    Toast.makeText(LoginActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"注册失败！用户名重复或者为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.verifyPassword(account_edtTxt.getText().toString(),password_edtTxt.getText().toString()))
                {
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    saveLogin(login_state);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,account_edtTxt.getText().toString()+"欢迎您！",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"登陆失败！请检查用户是否存在或者密码错误！",Toast.LENGTH_SHORT).show();
                }

            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                login_state = isChecked;
            }
        });



      /*  ActionBar actionBar=getSupportActionBar();
        actionBar.hide();*/
       /* account_edtTxt=(EditText)findViewById(R.id.account_edtTxt);
        password_edtTxt=(EditText)findViewById(R.id.password_edtTxt);
        login_btn=(Button)findViewById(R.id.login_btn);
        Log.d("444","sd");

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String account=account_edtTxt.getText().toString();
                String password=password_edtTxt.getText().toString();
                //如果账号是admin且密码是123456，就认为登录成功
                if(account.equals("5201314")&&password.equals("rmlIloveu"))
                {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(account.equals("")&&password.equals(""))
                {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(!account.equals("5201314")){
                    Toast.makeText(LoginActivity.this,"This account is non-existent",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"account or password are invalid",Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    //保存数据
    private void saveLogin(boolean flag)
    {//参数name：命名 mode：模式，包括 MODE_PRIVATE（只能被自己的应用程序访问）
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("ACCOUNT_REMEMBER", MODE_PRIVATE);//使用Activity类的getSharedPreferences方法获得SharedPreferences对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//使用SharedPreferences接口的edit获得SharedPreferences.Editor对象
        String secreat_name = account_edtTxt.getText().toString();
        String secreat_password = password_edtTxt.getText().toString();
        if(sharedPreferences.getBoolean("flag",false))
        {
            return;
            //验证通过，不需要再次保存了
        }
        if(flag) {
            secreat_password= Base64.encodeToString(secreat_password.getBytes(),Base64.NO_WRAP);
            editor.putString("name",secreat_name);//通过SharedPreferences.Editor接口的putXXX方法保存key-value对
            editor.putString("password", secreat_password);
            editor.putBoolean("flag", flag);
            editor.commit();//通过过SharedPreferences.Editor接口的commit方法保存key-value对
        }
        else{
            editor.clear();
            editor.putString("name",secreat_name);
            editor.putBoolean("flag",false);
            editor.commit();
        }

    }
    //清除
    private void cleanState()
    {
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("ACCOUNT_REMEMBER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    //初始化登录
    private void initLogin()
    {
        SharedPreferences sharedPreferences=LoginActivity.this.getSharedPreferences("ACCOUNT_REMEMBER", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("flag",false)){
            String decode_password=sharedPreferences.getString("password","");
            decode_password= new String(Base64.decode(decode_password.getBytes(),Base64.NO_WRAP));
            account_edtTxt.setText(sharedPreferences.getString("name",""));
            password_edtTxt.setText(decode_password);
            checkBox.setChecked(true);
        }
        else {
            account_edtTxt.setText(sharedPreferences.getString("name",""));
            checkBox.setChecked(false);
        }
    }
}
