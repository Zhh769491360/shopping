package com.example.zhh.four_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
**添加商品
**
 */
public class add extends AppCompatActivity {

    String address = "http://192.168.43.82:8080/Try/sell/add_commo_seller.jsp";
    String link = "add_commo_seller";
    Button btn_ok;
    ImageButton btn_return;
    TextView sell_admin;
    EditText sell_name;
    EditText sell_price;
    EditText sell_amount;
    String seller_name;

    boolean add = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        sell_admin = (TextView)findViewById(R.id.sell_admin);
        sell_name = (EditText)findViewById(R.id.sell_name);
        sell_price = (EditText)findViewById(R.id.sell_price);
        sell_amount = (EditText)findViewById(R.id.sell_amount);

        List<Sell> sell = DataSupport.findAll(Sell.class);//本地数据
        for (Sell sells: sell){
            sell_admin.setText(sells.getSell_admin());
            seller_name = sells.getName();
        }

        btn_return = (ImageButton)findViewById(R.id.goto_list1);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add.this, ListActivity.class);
                startActivity(intent);
            }
        });

        btn_ok = (Button)findViewById(R.id.add_OK);
        btn_ok.setOnClickListener(new View.OnClickListener(){//向云端发送数据
            @Override
            public void onClick(View v){
                if (v.getId() == R.id.add_OK) {
                    if (sell_admin.getText().toString().equals("")
                            || sell_name.getText().toString().equals("")
                            || sell_price.getText().toString().equals("")
                            || sell_amount.getText().toString().equals("")) {
                        Toast.makeText(add.this,
                                "请将信息填完整",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        final String content = sell_admin.getText().toString() + "|"
                                + sell_name.getText().toString() + "|"
                                + sell_price.getText().toString() + "|"
                                + sell_amount.getText().toString() + "|"
                                + seller_name;
                        HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();

                                if (responseData.trim().equals("true")){
                                    add = true;
                                }

                            }
                        });
                        if (add) {
                            Toast.makeText(add.this,
                                    "添加成功！",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(add.this,
                                    "添加失败！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

}
