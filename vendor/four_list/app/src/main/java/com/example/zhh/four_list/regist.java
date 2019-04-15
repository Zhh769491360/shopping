package com.example.zhh.four_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.*;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*
**注册
**
 */
public class regist extends AppCompatActivity {

    Button btnback, btnregist;

    EditText eduser, edpass, repass, name;


    Boolean Agree_phone_a = true;//用于判断电话号码是否重复
    Boolean Agree_password_a = false;//用于判断密码是否一致
    Boolean Agree_regist = false;//用于判断是否注册成功

    String address = "http://192.168.43.82:8080/Try/sell/regist_seller.jsp";
    String link = "seller_regist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        btnback = (Button) findViewById(R.id.Btn_rgback);
        btnregist = (Button) findViewById(R.id.Btn_ckregist);
        eduser = (EditText) findViewById(R.id.Edit_rguser);
        edpass = (EditText) findViewById(R.id.Edit_rgpass);
        repass = (EditText) findViewById(R.id.Edit_repass);
        name = (EditText) findViewById(R.id.Edit_name); //获取控件

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(regist.this, Login.class);
                startActivity(intent);//返回登录界面
            }
        });

        btnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eduser.getText().toString().equals("")){
                    Toast.makeText(regist.this,
                            "用户名不能为空！",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if (name.getText().toString().equals("")){
                        Toast.makeText(regist.this,
                                "店铺名称不能为空！",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        if (edpass.getText().toString().equals("")){
                            Toast.makeText(regist.this,
                                    "密码不能为空！",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            if (!edpass.getText().toString().equals(repass.getText().toString())){
                                Toast.makeText(regist.this,
                                        "两个密码不相等！",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                final String content = eduser.getText() + "|" + edpass.getText() + "|" + name.getText();
                                HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String responseData = response.body().string();
                                       // if (Boolean.valueOf( responseData.trim().toString() )){
                                        if (responseData.trim().equals("true")){
                                            Agree_regist = true;
                                            DataSupport.deleteAll( Sell.class );
                                            Sell user = new Sell();
                                            user.setSell_admin(eduser.getText().toString());
                                            user.setPassword(edpass.getText().toString());
                                            user.setName(name.getText().toString());
                                            user.setMoney(8000.0);
                                            user.save();
                                            //登陆成功
                                            //Init();
                                        }
                                        else if (responseData.trim().toString().equals("zhuceworng")){
                                            Agree_phone_a = true;
                                        }

                                    }
                                });//////
                                if (Agree_regist){
                                    Toast.makeText(regist.this,
                                            "注册成功",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(regist.this, Login.class);
                                    intent.putExtra("Phone", eduser.getText().toString());
                                    intent.putExtra("Password", edpass.getText().toString());
                                    intent.putExtra("Name", name.getText().toString());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                    Init();
                                }else{
                                    if (Agree_phone_a){
                                        Toast.makeText(regist.this,
                                                "该用户已注册并夕夕",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }
                    }
                }


            }
        });
    }


    public void Init() {
        Agree_phone_a = true;
        Agree_password_a = false;
        Agree_regist = false;
    }
}



