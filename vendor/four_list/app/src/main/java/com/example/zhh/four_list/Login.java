package com.example.zhh.four_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
**登录
**
 */
public class Login extends AppCompatActivity {

    Button btnlogin, btnregist;

    EditText eduser, edpass;

    TextView sellname, password;

    CheckBox change_password_visable;


    String address = "http://192.168.43.82:8080/Try/sell/check_seller.jsp";
    String link = "sell";
    private boolean Agree_Login = false;//判断是否登录成功
    private boolean user_a = false;//判断用户是否注册
    private boolean password_a = false;//判断密码是否正确
    String getcommo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    eduser.setText(data.getStringExtra("Phone"));
                    edpass.setText(data.getStringExtra("Password"));
                    sellname.setText(data.getStringExtra("Name"));//接收来自注册成功的手机号和密码
                }
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sellname = (TextView)findViewById(R.id.Name_user);

        btnlogin = (Button)findViewById(R.id.Btn_login);
        btnregist = (Button)findViewById(R.id.Btn_regist);
        eduser = (EditText)findViewById(R.id.Edit_user);
        edpass = (EditText)findViewById(R.id.Edit_pass);

        eduser.setText("13566666662");
        edpass.setText("111111");
        change_password_visable = (CheckBox)findViewById(R.id.cbDisplayPassword); //获取控件


        btnregist.setOnClickListener(new View.OnClickListener() {//注册 前往注册页面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, regist.class);
                startActivityForResult(intent, 1);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity( new Intent(Login.this,MainActivity.class));
                if(eduser.getText().toString().equals("")){
                    Toast.makeText(Login.this,
                            "用户名不能为空！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    if(edpass.getText().toString().equals("")){
                        Toast.makeText(Login.this,
                                "密码不能为空！",
                                Toast.LENGTH_SHORT).show();
                    }else {

                        String content = eduser.getText() + "|" + edpass.getText();
                        Log.d("Login",content);

                        HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();

                                if (responseData.trim().equals("true")){
                                    Agree_Login = true;
                                    DataSupport.deleteAll( Sell.class );
                                    Sell user = new Sell();

                                    user.setSell_admin(eduser.getText().toString());
                                    user.setPassword(edpass.getText().toString());
                                    user.setName(sellname.getText().toString());
                                    user.setMoney(8000.0);
                                    user.save();


                                }
                                else if (responseData.trim().toString().equals("zhuceworng")){
                                    user_a = true;

                                }
                                else{
                                    password_a = true;

                                }

                            }
                        });//////

                        if (Agree_Login){
                            Toast.makeText(Login.this,
                                    "登录成功",
                                    Toast.LENGTH_SHORT).show();
                            Init();
                            startActivity( new Intent(Login.this,List3Activity.class));
                        }else{
                            if (user_a){
                                Toast.makeText(Login.this,
                                        "该用户未注册并夕夕",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                if (password_a){
                                    Toast.makeText(Login.this,
                                            "密码不正确",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }


                Init();
            }


        });


        change_password_visable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //选择状态 显示明文--设置为可见的密码
                    edpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本
                    edpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


    }
    private void Init() {
        Agree_Login = false;
        user_a = false;
        password_a = false;
    }



}
