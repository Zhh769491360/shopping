package com.example.pc.bottomlansu;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class Fragment1 extends BaseFragment {
    public static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    private CircleImageView picture;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout mDrawerLayout;
    private Fruit[] fruits = new Fruit[60];
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate( R.menu.toolbar, menu );
        //super.onCreateOptionsMenu( menu, inflater );
    }
    //getResources().getIdentifier或者就是这个函数来寻找图片的ID

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText( getActivity(), "保存成功", Toast.LENGTH_LONG ).show();
                break;
            case R.id.delete:
                DataSupport.deleteAll( Buying.class );
                DataSupport.deleteAll( User.class );
                DataSupport.deleteAll( App.class );
                Toast.makeText( getActivity(), "清空本地数据库的一切", Toast.LENGTH_LONG ).show();
                break;
            case R.id.settings:

                Toast.makeText( getActivity(), "设置", Toast.LENGTH_LONG ).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer( GravityCompat.START );
                break;
            default:
        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LogUtil.d( "Fragment1", "开始布局主页面" );
        LoadinGood();//商品进入布局

        for (int i = 0; i < fruits.length; ++i) {
            fruits[i] = new Fruit();
        }
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_fragment1, container, false );
    }

    @Override
    public void onStart() {
        //取得来自非自身xml外的xml中的控件对象开始检测是否登陆
        refreshFruits();//随机加入接收的数据其实很多重复。

        final List<User> users = DataSupport.findAll( User.class );
        if (users.size() == 0) {
            //Toast.makeText( getActivity(),"请登录谢谢",Toast.LENGTH_SHORT ).show();
            startActivity( new Intent( getActivity(), login.class ) );
        }


        Toolbar toolbar = (Toolbar) getView().findViewById( R.id.toolbar );
        setHasOptionsMenu( true );
        ((AppCompatActivity) getActivity()).setSupportActionBar( toolbar );
        picture = (CircleImageView) getView().findViewById( R.id.picture );
        mDrawerLayout = (DrawerLayout) getView().findViewById( R.id.drawer_layout );
        NavigationView navigationView = (NavigationView) getView().findViewById( R.id.nav_view );
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
        navigationView.setCheckedItem( R.id.nav_call );
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_call:
                        for (User user:users){
                            Toast.makeText( getActivity(),"已登录"+"手机号："+user.getId()+"密码："+user.getPassword(),Toast.LENGTH_LONG ).show();
                        }
                        break;
                    case R.id.nav_history:
                        startActivity( new Intent( getActivity(), register.class ) );
                        break;
                    case R.id.nav_location:
                        startActivity( new Intent( getActivity(), login.class ) );
                        //mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        } );

        final RecyclerView recyclerView = (RecyclerView) getView().findViewById( R.id.recycler_view );
        GridLayoutManager layoutManager = new GridLayoutManager( getActivity(), 2 );
        recyclerView.setLayoutManager( layoutManager );
        adapter = new FruitAdapter( fruitList );
        recyclerView.setAdapter( adapter );

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition( 0 );
            }
        } );

        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById( R.id.swipe_refresh );
        swipeRefreshLayout.setColorSchemeResources( R.color.colorPrimary );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        } );
        super.onStart();
    }

    private void LoadinGood() {

        String address = "http://192.168.43.82:8080/Try/sell/SellCommodityInfo_json.jsp";
        HttpUtilEila.sendOkHttpRequester( address, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d( "Fragment1", "接收错误，来自mysql字段未能接收到" );
                //没有获得的权限 没有找对对应的包包
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string() ;
                //showResponse(responseData);
                LogUtil.d( "Fragment1", "接收成功，来自mysql字段能接收到" );
                parseJSONWithGSON( responseData );
            }
        } );
    }

    private void parseJSONWithGSON(String jsonData) {
        LogUtil.d( "jsonData",jsonData );
        Gson gson = new Gson();
        List<App> appList = gson.fromJson( jsonData, new TypeToken<List<App>>() {}.getType() );
        LogUtil.d( "Fragment1", "接收于解析" );
        int i = 0;
        for (App app : appList) {
            if (fruits[i] != null) {
                App app2 = new App();
                app2.set( app );
                app2.save();
                fruits[i].setName( app.getSell_name() );
                fruits[i].setBuy_ID( app.getSell_id() );
                fruits[i].setImageId( getResources().getIdentifier( app.getSell_image(), "drawable", "com.example.pc.bottomlansu" ) );
                i++;
            } else {
                LogUtil.d( "Fragment1_null", "isnull" );
            }

        }
    }

    private void refreshFruits() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep( 500 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing( false );

                    }
                } );
            }
        } ).start();

    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int index = random.nextInt( fruits.length );
            LogUtil.d( "fruits_a", "" + fruits[index].getName() );
            fruitList.add( fruits[index] );
        }
    }
}
