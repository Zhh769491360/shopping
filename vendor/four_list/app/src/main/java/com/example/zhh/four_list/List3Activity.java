package com.example.zhh.four_list;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
**主页面，包括商家店铺信息、热销商品等等
**
 */
public class List3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView sell_admin, sell_name1, sell_money, sell_name2;
    ImageView image1, image2;

    private String admin = new String();
    private String name = new String();
    private double money = 0;

    String address = "http://192.168.43.82:8080/Try/sell/get_seller.jsp";
    String link = "get_seller";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        LayoutInflater inflater = List3Activity.this.getLayoutInflater();                              //先获取当前布局的填充器
//        View view = inflater.inflate(R.layout.nav_header_ceshi, null);   //通过填充器获取另外一个布局的对象


        sell_admin = (TextView) findViewById(R.id.sell_admin1);
        sell_name1 = (TextView) findViewById(R.id.sell_name1);
        sell_money = (TextView) findViewById(R.id.sell_money1);
        image2 = (ImageView) findViewById(R.id.sell_image1);

        RecMessage();//接收数据

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List3Activity.this, List3Activity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        侧滑栏的两个变量：头像和店铺名

         */
        if(navigationView.getHeaderCount() > 0) {
            View view = navigationView.getHeaderView(0);
            image1 = (ImageView) view.findViewById(R.id.imageView);
            sell_name2 = (TextView) view.findViewById(R.id.sell_name2);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ceshi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_return_login) {
            startActivity(new Intent(List3Activity.this, Login.class));
        } else if (id == R.id.nav_manage_people) {
            startActivity(new Intent(List3Activity.this, sell_change.class));
        } else if (id == R.id.nav_manage_commodity) {
            startActivity(new Intent(List3Activity.this, ListActivity.class));
        } else if (id == R.id.nav_manage_trade) {
            startActivity(new Intent(List3Activity.this, List2Activity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void RecMessage() {
        List<Sell> sell = DataSupport.findAll(Sell.class);//本地数据
        for (Sell sells : sell) {
            admin = sells.getSell_admin();
        }
        final String content = admin;
        HttpUtilEila.PostSendHttpRequester(address, link, content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                String[] str;
                str = responseData.trim().split("\\[");

                if (!str[1].trim().equals("false01")) {
                    parseJSONWithGSON(responseData);//接收数据，存入数组

                    sell_admin.setText(admin);
                    sell_name1.setText(name);
                    sell_name2.setText(name);
                    sell_money.setText(String.valueOf(money));
                    if (admin.equals("13566666661")) {
                        image1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.taowang));
                        image2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.taowang));

                    } else if (admin.equals("13566666662")) {
                        image1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.jingbao));
                        image2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.jingbao));

                    } else if (admin.equals("13566666663")) {
                        image1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.imn));
                        image2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.imn));

                    } else if (admin.equals("13566666664")) {
                        image1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.jianguo));
                        image2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.jianguo));

                    } else if (admin.equals("13566666665")) {
                        image1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blz));
                        image2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blz));

                    }
                }
            }
        });
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<Sell> appList = gson.fromJson(jsonData, new TypeToken<List<Sell>>() {
        }.getType());
        for (Sell app : appList) {
            admin = app.getSell_admin();
            name = app.getName();
            money = app.getMoney();

        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("List3 Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
