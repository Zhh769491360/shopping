package com.example.pc.bottomlansu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class register extends AppCompatActivity {
    private Button Goto_login;
    private ImageButton Goto_login2;
    private EditText Edit_phone;
    private EditText Edit_name;
    private EditText Edit_password;
    private CheckBox Xieyi1;
    private ToggleButton Yincang2;
    private EditText addr;

    Boolean Agree1 = false;//用于判断是否同意协议
    Boolean Agree_phone_e = false;//用于判断电话号码是否为空
    Boolean Agree_name_e = false;//用于判断昵称是否为空
    Boolean Agree_password_e = false;//用于判断密码是否为空
    Boolean Agree_phone_a = false;//用于判断电话号码是否重复
    Boolean Agree_name_a = false;//用于判断昵称是否重复

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Goto_login = (Button) findViewById(R.id.goto_login);
        Goto_login2 = (ImageButton) findViewById(R.id.goto_login2);
        Edit_phone = (EditText) findViewById(R.id.edit_phone);
        Edit_name = (EditText) findViewById(R.id.edit_name);
        Edit_password = (EditText) findViewById(R.id.edit_password);
        Xieyi1 = (CheckBox) findViewById(R.id.xieyi1);
        Yincang2 = (ToggleButton) findViewById(R.id.yincang);
        addr = (EditText)findViewById(R.id.edit_address);

//        Yincang2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //如果选中，显示密码
//                    Yincang2.setBackgroundResource(R.drawable.xianshi);
//                    Edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    //否则隐藏密码
//                    Yincang2.setBackgroundResource(R.drawable.yincang);
//                    Edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

        Xieyi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agree1 = true;//同意则为true
            }
        });

        Goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ////////////判断注册信息是否有重复,是否为空
                List<User> books = DataSupport.findAll(User.class);
                for (User user1: books){
                    if (!Edit_phone.getText().toString().equals("")) {
                        if (user1.getId().equals(Edit_phone.getText().toString())) {
                            Agree_phone_a = true;
                        }
                    }else{
                        Agree_phone_e = true;
                    }

                    if (!Edit_name.getText().toString().equals("")) {
                        if (Edit_name.getText().toString().equals(user1.getName()  )) {
                            Agree_name_a = true;
                        }
                    }else{
                        Agree_name_e = true;
                    }

                    if (Edit_password.getText().toString().equals("")){
                        Agree_password_e = true;
                    }
                }


                //////////

                if (Agree_phone_e){
                    Toast.makeText(register.this,
                            "手机号码不能为空",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if (Agree_phone_a){
                        Toast.makeText(register.this,
                                "该手机号码已注册",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if (Agree_name_e){
                    Toast.makeText(register.this,
                            "昵称不能为空",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if (Agree_name_a){
                        Toast.makeText(register.this,
                                "昵称重复",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if (Agree_password_e){
                    Toast.makeText(register.this,
                            "密码不能为空",
                            Toast.LENGTH_SHORT).show();
                }

                if (!Agree_phone_a
                        && !Agree_name_a
                        && !Agree_phone_e
                        && !Agree_name_e
                        && !Agree_password_e) {
                    if (Agree1) {
                        Init2();//初始化
                        //LitePal.getDatabase();
                        //开始上传登陆信息
                        String address = "http://192.168.43.82:8080/sell/page/upload_user.jsp";
                        String link = "user";
                        final String content = Edit_phone.getText() + "|" + Edit_name.getText() + "|" + Edit_password.getText()+ "|" + addr.getText();
                        HttpUtilEila.PostSendHttpRequester( address, link, content, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        LogUtil.d( "register","false" );
                                    }
                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        LogUtil.d( "register",content );
                                        String responseData = response.body().string();
                                        if (Boolean.valueOf( responseData.trim().toString() )){
                                            //注册成功
                                            Intent intent = new Intent(register.this, login.class);
                                            intent.putExtra("Phone", Edit_phone.getText().toString());
                                            intent.putExtra("Password", Edit_password.getText().toString());
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    }
                                }
                        );
                    }else {
                        Toast.makeText(register.this,
                                "请阅读并同意并夕夕用户协议及并夕夕隐私权保护声明",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                Init2();//初始化
            }

        });

        Goto_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void Init2(){
        Agree1 = false;
        Agree_phone_e = false;
        Agree_name_e = false;
        Agree_password_e = false;
        Agree_phone_a = false;
        Agree_name_a = false;
    }

}
