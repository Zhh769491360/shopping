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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.sql.Types.NULL;

public class ListActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4;//btn1暂定为向云端发送信息(delete)
    ImageButton shuaxin;
    String address = "http://192.168.43.82:8080/Try/sell/get_commo_seller.jsp";
    String address2 = "http://192.168.43.82:8080/Try/sell/delete_commo_seller.jsp";

    String link = "commo_seller";
    String link2 = "delete_commo_seller";

    String[] Ssell_ad = new String[1024];
    String[] Ssell_name = new String[1024];
    int[] Iid = new int[1024];
    String[] Sname = new String[1024];
    double[] Dprice = new double[1024];
    int[] Iamount = new int[1024];
    String[] Simage = new String[1024];
    String delete = "";
    String getcommo = "";
    public Boolean [] table_check = new Boolean[1024];
    static int n = 0;
    static int m = 0;


    private List<TableRow> tableList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);

        RecMessage();//接收云端信息并显示

        final TableRowAdapter adapter = new TableRowAdapter(ListActivity.this, R.layout.tablerow_item, tableList);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TableRow row = tableList.get(position);
                Toast.makeText(ListActivity.this, row.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        adapter.setOnCheckListener(new TableRowAdapter.onCheckListener() {
            @Override
            public void onCheck(int position) {
                TableRow row = tableList.get(position);
                if(row.getcheck()/* || row.getStr5() != "false"*/) {
                    //row.setStr5("false");
                    row.setIfcheck(false);
                    //adapter.notifyDataSetChanged();
                } else if(!row.getcheck()/*|| row.getStr5() != "true"*/){
                   // row.setStr5("true");
                    row.setIfcheck(true);
                    //adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
            }
        });

        shuaxin = (ImageButton)findViewById(R.id.shuaxin1);
        shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                Intent intent = new Intent(ListActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                Intent intent = new Intent(ListActivity.this, List3Activity.class);
                startActivity(intent);
            }
        });

        btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                Intent intent = new Intent(ListActivity.this, add.class);
                startActivity(intent);
            }
        });

        btn3 = (Button)findViewById(R.id.btn3);
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
                       // tableList.get(i).setStr5("fasle");
                        tableList.get(i).setIfcheck(false);
                        table_check[i] = false;
                    }
                    btn4.setText("全  选");
                    adapter.notifyDataSetChanged();
                    return;
                }
                for(int i = 0; i < n; i++) {
                    //tableList.get(i).setStr5("true");
                    tableList.get(i).setIfcheck(true);
                    table_check[i] = true;
                }
                btn4.setText("不全选");
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void RecMessage(){
        List<Sell> sell = DataSupport.findAll(Sell.class);//本地数据
        for (Sell sells: sell){
            getcommo = sells.getSell_admin();
        }
        final String content = getcommo;
        HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                String [] str;
                str = responseData.trim().split("\\[");

                if (!str[1].trim().equals("false01") ) {
                    parseJSONWithGSON(responseData);//接收数据，存入数组
                    initTableRows();
                }
            }
        });
    }

    private void SendMessage(){
        //List<App> sell = DataSupport.findAll(App.class);//本地数据
        for (int i = 0; i < n; i++){

            TableRow row = tableList.get(i);
            if(row.getcheck()) {

                delete = String.valueOf(Iid[i]);
                //i++;

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
        startActivity( new Intent(ListActivity.this,ListActivity.class));
    }

    private void initTableRows() {
        for (int j = 0; j < n;j++){
            TableRow row = new TableRow(Ssell_ad[j], Ssell_name[j], Iid[j], Sname[j], Dprice[j], Iamount[j], Simage[j], false);
            tableList.add(row);
        }
    }

    private void parseJSONWithGSON( String jsonData ){
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
            for (App app : appList){
                Ssell_ad[n] = app.getSell_admin();
                Ssell_name[n] = app.getSell_admin_name();
                Iid[n] = app.getSell_id();
                Sname[n] = app.getSell_name();
                Dprice[n] = app.getSell_price();
                Iamount[n] = app.getSell_amount();
                Simage[n] = app.getSell_image();
                n++;
        }


    }

    private void init(){
        Ssell_ad = new String[1024];
        Ssell_name = new String[1024];
        Iid = new int[1024];
        Sname = new String[1024];
        Dprice = new double[1024];
        Iamount = new int[1024];
        Simage = new String[1024];
        n = 0;
        m = 0;
        delete = "";
        getcommo = "";
        tableList = new ArrayList<>();
    }
}