package com.example.pc.bottomlansu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.DataInput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FinishBuy extends AppCompatActivity {

    public static void actionStartLittle(Context mContext, Boolean panduan, int data) {
        Intent intent = new Intent( mContext, FinishBuy.class );
        intent.putExtra( "panduan",panduan );
        intent.putExtra( "finishBuyData", data );
        mContext.startActivity( intent );
    }
    public static void actionStartMuch(Context mContext, Boolean panduan, String datastring, double datadouble, int dataint) {
        Intent intent = new Intent( mContext, FinishBuy.class );
        intent.putExtra( "panduan",panduan );
        intent.putExtra( "finishBuyData3", dataint);
        intent.putExtra( "finishBuyData1", datastring );
        intent.putExtra( "finishBuydata2", datadouble );
        mContext.startActivity( intent );
    }
    private void upLoadinGood(String datastring,double datadouble,int num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date()).toString();// new Date()为获取当前系统时间
        String address = "http://192.168.43.82:8080/Try/sell/upload_record.jsp";
        String link = "finishmuch";
        //user_name trade_time from_address trade_num trade_goods_name
        //韩寒|1988-02-02 12：12|中关村朝阳市|3|瓜子|瓜子|瓜子
        String content = textView2.getText()+"|"+time+"|"+textView3.getText()+"|"+num+"|"+datastring;
        LogUtil.d( "content",content );
        HttpUtilEila.PostSendHttpRequester( address, link, content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.d( "FinishBuy","it is false much" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string() ;
                Log.d( "FinishBuy",responseData );
                if  (Boolean.valueOf( responseData.trim().toString() )){
                    startActivity( new Intent( FinishBuy.this, MainActivity.class ) );
                }else{
                    LogUtil.d( "Finishmuch","false" );
                }
            }
        } );

    }
    private void upLoadinGood(int finiBuyInt) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date()).toString();// new Date()为获取当前系统时间
        String address = "http://192.168.43.82:8080/Try/sell/uoload_record.jsp";
        String link = "finishlittle";
        String content = textView2.getText()+"|"+shanhu+"|"+time+"|"+textView3.getText()+"|"+"1"+"|"+textView.getText();
        LogUtil.d( "content",content );
        //最后给个反馈是否给与发货请求。。。。。。。。。。
        //user_name sell_name trade_time from_address trade_num trade_goods_name
        HttpUtilEila.PostSendHttpRequester( address, link, content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.d( "FinishBuy","it is false little" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string() ;
                Log.d( "FinishBuy",responseData );
                if (Boolean.valueOf( responseData.trim().toString() )){

                    startActivity( new Intent( FinishBuy.this, MainActivity.class ) );

                    //Toast.makeText( FinishBuy.this,"商家已发货 请耐心等待", Toast.LENGTH_LONG ).show();
                }else{
                    LogUtil.d( "Finishlittle","false" );
                }
            }
        } );

    }
    private String datastring;
    private double datadouble;
    private int dataint;
    private int finiBuyInt;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private  TextView textView3;
    private   Button button;
    private String shanhu="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_finish_buy );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace  with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
        //内置的意义在于是重复而不需要取出对象的时候用的。。。
        textView = (TextView)findViewById( R.id.nameID);//商品名称
         textView1 = (TextView)findViewById(R.id.num );//商品数目
         textView2 = (TextView)findViewById( R.id.myname);//用户名字
         textView3 = (TextView)findViewById(R.id.about );//地址
         button = (Button)findViewById( R.id.surebuy );

        //得到来自FruitActivity的商品ID
        Intent intent = getIntent();
        if (intent.getBooleanExtra( "panduan",false )){
            datastring = intent.getStringExtra( "finishBuyData1");
            datadouble = intent.getDoubleExtra( "finishBuyData2",0 );
            dataint = intent.getIntExtra( "finishBuyData3",0 );
            textView.setText( datastring );
            textView1.setText( dataint+"" );
            List<User> users = DataSupport.findAll( User.class );
            for (User user:users){
                textView2.setText( user.getId());
                textView3.setText( user.getAddress());
            }

            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upLoadinGood(datastring,datadouble,dataint);

                    //startActivity( new Intent( FinishBuy.this,MainActivity.class) );
                }
            } );


        }else{
            finiBuyInt = intent.getIntExtra( "finishBuyData",0 );

            List<App> apps = DataSupport.findAll( App.class );
            for (App app:apps){
                if (app.getSell_id()==finiBuyInt){
                    textView.setText( app.getSell_name() );
                    textView1.setText("1");
                    shanhu = app.getSell_admin_name();
                }
            }
            List<User> users = DataSupport.findAll( User.class );
            for (User user:users){
                textView2.setText( user.getId());
                textView3.setText( user.getAddress());
            }
            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upLoadinGood(finiBuyInt);
                    //startActivity( new Intent( FinishBuy.this,MainActivity.class) );
                }
            } );
        }


    }

}
