package com.example.pc.bottomlansu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class FruitActivity extends BaseActivity {

    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    public static final String Buy_ID = "Buy_id";
    public int BuyId = 0;


    public static void actionStart(Context mContext, Fruit fruit) {
        Intent intent = new Intent( mContext, FruitActivity.class );
        intent.putExtra( FruitActivity.FRUIT_NAME, fruit.getName() );
        intent.putExtra( FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId() );
        intent.putExtra( FruitActivity.Buy_ID, fruit.getBuy_ID());
        mContext.startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fruit );
        //序列化传递
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra( FRUIT_NAME );
        int fruitImageId = intent.getIntExtra( FRUIT_IMAGE_ID, 0 );
        BuyId = intent.getIntExtra( Buy_ID,0 );
        LogUtil.d( "getBuyId",BuyId+" " );

        TextView textView = findViewById( R.id.jiage);
        List<App> appList = DataSupport.findAll( App.class );
        for (App app:appList){
            if (app.getSell_id()==BuyId){
                textView.setText(app.getSell_price()+"");
                //getResources().getIdentifier(app.getImage(), "drawable", "com.example.pc.bottomlansu")
            }
        }


        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                findViewById( R.id.collapsing_toolbar );
        ImageView fruitImageView = (ImageView) findViewById( R.id.fruit_Image_view );
        //TextView fruitContentText = (TextView)findViewById( R.id.fruit_content_text );
        Button waitButton = (Button) findViewById( R.id.wait_it_button );
        final Button buybutton = (Button) findViewById( R.id.take_it_button );
        buybutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //快给我那些被选中然后被买入的ID字符串吧
                FinishBuy.actionStartLittle( FruitActivity.this,false,BuyId );
            }
        } );
        waitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buying是购物车的那么我们需要一个存储样本的东西
                //从本地数据库挑选样本加入，购物车
                Toast.makeText(getApplication(), "已将其加入购物车", Toast.LENGTH_LONG ).show();
                List<App> appList = DataSupport.findAll( App.class );
                for (App app:appList){
                    if (app.getSell_id()==BuyId){
                        LogUtil.d( "getBuyId","加入" );
                        List<Buying> list = DataSupport.findAll( Buying.class );
                        int random = 0;
                        random = Fuzhi();
                        Buying buying = new Buying();
                        buying.setStore_img( 1 );
                        buying.setStrore_title(app.getSell_admin_name());
                        buying.setThing_img(1);
                        buying.setThing_num( 1 );
                        buying.setThing_price( app.getSell_price());
                        buying.setThing_title( app.getSell_name() );
                        buying.setGuwuch_rank( random );
                        buying.save();
                        break;
                        //getResources().getIdentifier(app.getImage(), "drawable", "com.example.pc.bottomlansu")
                    }

                }
            }
        } );
        setSupportActionBar( toolbar );
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
            //actionBar.setHomeAsUpIndicator( R.drawable.ic_dashboard_black_24dp );
        }
        collapsingToolbar.setTitle( fruitName );
        Glide.with( this ).load( fruitImageId ).into( fruitImageView );
        //String fruitContent = generateFruitContent(fruitName);
        //fruitContentText.setText(fruitContent);
    }
    private int Fuzhi(){
      int random = 0;
      while(true){
          random = (int)(1+Math.random()*(100));
          if (Fanhui(random)==true){
              break;
          }
      }
      return random;
    }
    private Boolean Fanhui(int rand){
        List<Buying> buyings = DataSupport.findAll( Buying.class );
        for (Buying buying:buyings){
            if (rand==buying.getGuwuch_rank()){
                return false;
            }
        }
        return true;
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; ++i) {
            fruitContent.append( fruitName );
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
