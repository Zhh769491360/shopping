package com.example.zhh.four_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
**更改卖家信息
**
*/
public class sell_change extends AppCompatActivity {

    EditText sell_name,sell_password1,sell_password2;
    Button btn_ok;
    ImageButton btn_fanhui;

    String address = "http://192.168.43.82:8080/Try/sell/change_seller.jsp";
    String link = "change_seller";
    String change_s = "";
    private boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_change);

        sell_name = (EditText)findViewById(R.id.sell_name);
        sell_password1 = (EditText)findViewById(R.id.password1);
        sell_password2 = (EditText)findViewById(R.id.password2);

        btn_fanhui = (ImageButton)findViewById(R.id.goto_list1);
        btn_fanhui.setOnClickListener(new View.OnClickListener() {//注册 前往注册页面
            @Override
            public void onClick(View v) {
                startActivity( new Intent(sell_change.this,List3Activity.class));
            }
        });

        btn_ok = (Button)findViewById(R.id.change_OK);
        btn_ok.setOnClickListener(new View.OnClickListener() {//注册 前往注册页面
            @Override
            public void onClick(View v) {

                if (!sell_password1.getText().toString().equals(sell_password2.getText().toString())){
                    Toast.makeText(sell_change.this,
                            "两个密码不一致！",
                            Toast.LENGTH_SHORT).show();
                }else{
                    List<Sell> sell = DataSupport.findAll(Sell.class);//本地数据
                    for (Sell sells: sell){
                        change_s = sells.getSell_admin();
                    }
                    final String content = change_s + "|" + sell_name.getText() + "|" + sell_password1.getText();
                    HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = response.body().string();
                            if (responseData.trim().toString().equals("true")){

                                Sell user = new Sell();

                                if (!sell_name.getText().toString().equals("")) {
                                    user.updateAll("name = ?",sell_name.getText().toString());
                                }
                                if (!sell_password1.getText().toString().equals("")) {
                                    user.updateAll("password = ?",sell_password1.getText().toString());
                                }

                                change = true;
                            }
                        }
                    });
                    if (change){
                        Toast.makeText(sell_change.this,
                                "修改成功！",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(sell_change.this,
                                "修改失败！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
