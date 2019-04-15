package com.example.pc.bottomlansu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class login extends AppCompatActivity {

    private Button Goto_homepage;
    private CheckBox Xieyi2;
    public EditText In_phone;
    private EditText In_password;
    private ToggleButton Yincang;

    Boolean Agree2 = false;//用于判断是否同意协议
    Boolean Agree_login = false;//用于判断是否能登录
    Boolean Agree_phone = false;//用于判断电话号码是否注册
    Boolean Agree_password = false;//用于判断密码是否正确
    Boolean Agree_phone_e2 = false;//用于判断电话号码是否为空
    Boolean Agree_password_e2 = false;//用于判断密码是否为空


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    In_phone.setText(data.getStringExtra("Phone"));
                    In_password.setText(data.getStringExtra("Password"));//接收来自注册成功的手机号和密码
                }
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);

        TextView Goto_zhuce = (TextView) findViewById(R.id.goto_zhuce);
        Goto_homepage = (Button) findViewById(R.id.goto_homepage);
        Xieyi2 = (CheckBox) findViewById(R.id.xieyi2);
        In_phone = (EditText) findViewById(R.id.in_phone);
        In_password = (EditText) findViewById(R.id.in_password);
        Yincang = (ToggleButton) findViewById(R.id.yincang);

//        Yincang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //如果选中，显示密码
//                    Yincang.setBackgroundResource(R.drawable.xianshi);
//                    In_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    Yincang.setBackgroundResource(R.drawable.yincang);
//                    In_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

        Xieyi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agree2 = true;//同意则为true
            }
        });


        Goto_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivityForResult(intent, 1);
            }
        });

        Goto_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///检测电话号码和密码是否为空或是否正确 到云端去检验 现在只要把电话号码和密码发送上去即可
                String address = "http://192.168.43.82:8080/Try/sell/check_user.jsp";
                String link = "user";
                final String content = In_phone.getText() + "|" + In_password.getText();
                HttpUtilEila.PostSendHttpRequester( address, link, content, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.d( "login_a","false" );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtil.d( "login_a",content );
                        String responseData = response.body().string().trim();
                        String[] temp = responseData.split("\\|");
                        String adressuse = temp[1];
                        String jiaodui = temp[0];
                        LogUtil.d( "login_a",responseData );
                        if (Boolean.valueOf(jiaodui)){

                            DataSupport.deleteAll( User.class );

                            User user = new User(In_phone.getText().toString(),In_password.getText().toString(),adressuse);
                            user.save();
                            //登陆成功 跳转主页面
                            startActivity( new Intent(login.this,MainActivity.class));
                        }else{
                            startActivity( new Intent(login.this,login.class));
                            LogUtil.d( "login_a","登陆失败" );
                        }

                    }
                } );

            }
        });

    }

    public void Init(){
        Agree2 = false;
        Agree_login = false;
        Agree_phone = false;
        Agree_password = false;
        Agree_phone_e2 = false;
        Agree_password_e2 = false;
    }
}
