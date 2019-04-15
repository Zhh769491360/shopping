package com.example.zhh.four_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static java.sql.Types.NULL;
/*
**交易类
**
 */
public class List2Activity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4;
    ImageButton shuaxin;
    String address = "http://192.168.43.82:8080/Try/sell/get_record_seller.jsp";
    String address2 = "http://192.168.43.82:8080/Try/sell/record_delete.jsp";
    String address3 = "http://192.168.43.82:8080/Try/sell/record_ok.jsp";
    String link = "record_seller";
    String link2 = "record_delete";
    String link3 = "record_ok";

    String[] user_name = new String[1024];
    String[] sell_name = new String[1024];
    String[] trade_time = new String[1024];
    int[] trade_num = new int[1024];
    String[] trade_goods_name = new String[1024];
    String[] trade_addition = new String[1024];
    String[] from_address = new String[1024];
    static int n = 0;
    static int m = 0;
    String delete = "";
    String get_trade = "";



    private List<TableRow2> tableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        RecMessage();//获取交易数据

        final TableRowAdapter2 adapter = new TableRowAdapter2(List2Activity.this, R.layout.tablerow_item2, tableList);
        ListView listView = (ListView) findViewById(R.id.list_item2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TableRow2 row = tableList.get(position);
                Toast.makeText(List2Activity.this, row.getTrade_goods_name(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnCheckListener(new TableRowAdapter2.onCheckListener() {
            @Override
            public void onCheck(int position) {
                TableRow2 row = tableList.get(position);
                if(row.getcheck()/* || row.getStr5() != "false"*/) {
                    //row.setStr5("false");
                    row.setIfcheck(false);
                    //adapter.notifyDataSetChanged();
                } else if(!row.getcheck()/*|| row.getStr5() != "true"*/){
                    //row.setStr5("true");
                    row.setIfcheck(true);
                    //adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
            }
        });

        shuaxin = (ImageButton)findViewById(R.id.shuaxin2);
        shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                Intent intent = new Intent(List2Activity.this, List2Activity.class);
                startActivity(intent);
            }
        });

        btn1 = (Button)findViewById(R.id.btn1);//返回
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                Intent intent = new Intent(List2Activity.this, List3Activity.class);
                startActivity(intent);
            }
        });

        btn2 = (Button)findViewById(R.id.btn2);//确定交易
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage2();
            }
        });

        btn3 = (Button)findViewById(R.id.btn3);//删除交易
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });

        btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn4.getText() != "全  选") {
                    for(int i = 0; i < n; i++) {
                        //tableList.get(i).setStr5("fasle");
                        tableList.get(i).setIfcheck(false);
                    }
                    btn4.setText("全  选");
                    adapter.notifyDataSetChanged();
                    return;
                }
                for(int i = 0; i < n; i++) {
                    //tableList.get(i).setStr5("true");
                    tableList.get(i).setIfcheck(true);
                }
                btn4.setText("不全选");
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void RecMessage(){
        List<Sell> sell = DataSupport.findAll(Sell.class);//本地数据
        //Log.d("MAINNNN",user_name[0]);
        for (Sell sells: sell){
            get_trade = sells.getSell_admin();
        }
        final String content = get_trade;

        HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                String [] str;
                str = responseData.trim().split("\\[");

                //Log.d("MAINNNN",str[1]);
                if (!str[1].trim().equals("false01") ){
                    parseJSONWithGSON(responseData);//接收数据，存入数组
                    initTableRows();
                }
            }
        });
    }

    private void SendMessage(){
        for (int i = 0; i < n; i++){
            delete = "";
            TableRow2 row = tableList.get(i);
            if(row.getcheck()) {

                delete = user_name[i]
                        +"|"+sell_name[i]
                        +"|"+trade_time[i]
                        +"|"+String.valueOf(trade_num[i])
                        +"|"+trade_goods_name[i]
                        +"|"+from_address[i]
                        +"|"+trade_addition[i];

                final String content = delete;
                HttpUtilEila.PostSendHttpRequester(address2, link2, content, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {


                    }
                });
            }

        }
        init();
        startActivity( new Intent(List2Activity.this,List2Activity.class));
    }

    private void SendMessage2(){
        for (int i = 0; i < n; i++){
            delete = "";
            TableRow2 row = tableList.get(i);
            if(row.getcheck()) {

                delete = user_name[i]
                        +"|"+sell_name[i]
                        +"|"+trade_time[i]
                        +"|"+from_address[i]
                        +"|"+String.valueOf(trade_num[i])
                        +"|"+trade_goods_name[i]
                        +"|"+trade_addition[i];

                final String content = delete;
                HttpUtilEila.PostSendHttpRequester(address3, link3, content, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {


                    }
                });
            }

        }
        init();
        startActivity( new Intent(List2Activity.this,List2Activity.class));
    }

    private void initTableRows() {
        for (int j = 0; j < n;j++){
            TableRow2 row = new TableRow2(user_name[j], sell_name[j], trade_time[j], from_address[j],trade_num[j], trade_goods_name[j], trade_addition[j], false);
            tableList.add(row);
        }
    }

    private void parseJSONWithGSON( String jsonData ){
        Gson gson = new Gson();
        List<Trade> appList = gson.fromJson(jsonData,new TypeToken<List<Trade>>(){}.getType());
        for (Trade app : appList){
            user_name[n] = app.getUser_name();
            sell_name[n] = app.getSell_name();
            trade_time[n] = app.getTrade_time();
            from_address[n] = app.getFrom_address();
            trade_num[n] = app.getTrade_num();
            trade_goods_name[n] = app.getTrade_goods_name();
            trade_addition[n] = app.getTrade_addition();
            //Log.d("MAINNNN",trade_time[n]);
            n++;
        }


    }

    private void init(){//初始化函数
        user_name = new String[1024];
        sell_name = new String[1024];
        trade_time = new String[1024];
        trade_num = new int[1024];
        trade_goods_name = new String[1024];
        trade_addition = new String[1024];
        from_address = new String[1024];
        n = 0;
        m = 0;
        delete = "";
        get_trade = "";
        tableList = new ArrayList<>();
    }
}