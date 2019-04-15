package com.example.pc.bottomlansu;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.MenuItem;

import org.litepal.LitePal;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace( R.id.content, new Fragment1() );
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace( R.id.content, new Fragment2() );
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace( R.id.content, new Fragment3() );
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        setDefaultFragment();
        mTextMessage = (TextView) findViewById( R.id.message );
        BottomNavigationView navigation = (BottomNavigationView) findViewById( R.id.navigation );
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );
        //一开始我就要建立数据库litepal
        LitePal.getDatabase();
        //Intent intent = new Intent(MainActivity.this, login.class);
        //startActivity(intent);
    }

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    // 设置默认进来是tab 显示的页面
    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace( R.id.content, new Fragment1() );
        transaction.commit();
    }

}
