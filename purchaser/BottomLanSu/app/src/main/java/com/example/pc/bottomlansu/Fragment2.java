package com.example.pc.bottomlansu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class  Fragment2 extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    //商品数组,把他公开
    private RecyclerView recyclerView;
    private Buying[] xa = new Buying[60];
    private BuyingAdapter adapter;
    private List<Buying> XbuyList = new ArrayList<>( );
    private Button noButton,yesButton;
    private LinearLayoutManager layoutManager;
    private double pay = 0;
    private int shu = 0;
    private String collection;
    private int temp = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_fragment2, container, false );
    }

    @Override
    public void onStart() {
        noButton = (Button) getView().findViewById( R.id.no_join_button); //取消了购物车的东西
        yesButton = (Button)getView().findViewById( R.id.yes_join_button ); //最终还是买了购物车的东西
        swipeRefreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.swipe_refresh_buycar);

        noButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把勾上的物品剔除
                try{
                    int recy_size = adapter.getItemCount();//指出可见的item的数目
                    LogUtil.d( "recy_size",""+recy_size );
                    for (int i=recy_size-1;i>=0;--i){
                        View view = layoutManager.findViewByPosition(i);	//2为recyclerView中item位置，
                        LinearLayout layout = (LinearLayout)view;
                        //获取布局中任意控件对象
                        CheckBox status = (CheckBox) layout.findViewById(R.id.shopcheckboxId);
                        Button right = (Button)layout.findViewById( R.id.rightbuttonId );
                        //实在说这里确实还有问题，有设计上的问题
                        if (status.isChecked()){
                            //先逻辑变化在ui变化
                            List<Buying> buyings = DataSupport.findAll( Buying.class );
                            for (Buying buying:buyings){
                                if (buying.getGuwuch_rank()== Integer.parseInt( right.getText().toString().trim())){
                                    LogUtil.d( "Fragment2","deleted" );
                                    buying.delete();
                                    break;
                                }
                            }
                            status.setChecked( false );
                            adapter.removeData( i );
                        }

                    }

                }catch (Exception e){
                    LogUtil.d( "Fragment2_a","提出问题 in NO" );
                    e.printStackTrace();

                }


            }
        } );
        yesButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    collection = "";
                    pay = 0;
                    shu = 0;
                    temp = 0;

                    int recy_size = adapter.getItemCount();//指出可见的item的数目
                    LogUtil.d( "recy_size",""+recy_size );
                    for (int i=recy_size-1;i>=0;--i){
                        View view = layoutManager.findViewByPosition(i);	//2为recyclerView中item位置，
                        LinearLayout layout = (LinearLayout)view;
                        //获取布局中任意控件对象
                        CheckBox status = (CheckBox) layout.findViewById(R.id.shopcheckboxId);
                        Button right = (Button)layout.findViewById( R.id.rightbuttonId );
                        //实在说这里确实还有问题，有设计上的问题
                        if (status.isChecked()){
                            //先逻辑变化在ui变化
                            List<Buying> buyings = DataSupport.findAll( Buying.class );
                            for (Buying buying:buyings){
                                if (buying.getGuwuch_rank()== Integer.parseInt( right.getText().toString().trim())){
                                    //得到总数金额
                                    pay+=buying.getThing_price();
                                    if(temp>0)
                                        collection = collection + "|";
                                    collection = collection + buying.getThing_title();
                                    temp+=1;
                                    shu += 1;

                                    buying.delete();
                                    break;
                                }
                            }
                            status.setChecked( false );
                            adapter.removeData( i );
                        }

                    }

            }
                ////把勾上的物品加工，并且启动新的活动
             catch (Exception e){
                    LogUtil.d( "Fragment2_a","提出问题 in Yes" );
                    e.printStackTrace();

                }finally {
                    FinishBuy.actionStartMuch( getActivity(),true,collection,pay,shu );
                    LogUtil.d( "Fragment2_b"," " + collection +" "+ pay + " "+shu);
                }
            }
        } );

        swipeRefreshLayout.setColorSchemeResources( R.color.colorPrimary );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        } );


        getDatabase_buying();
        recyclerView = (RecyclerView)getView().findViewById( R.id.recycle_view );
        layoutManager = new LinearLayoutManager( getContext());
        recyclerView.setLayoutManager( layoutManager );
        adapter = new BuyingAdapter(XbuyList);
        recyclerView.setAdapter( adapter );
        super.onStart();
    }
    //int string int string int int
    private void refreshFruits(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep( 2000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        //清空在加入。。。
                        getDatabase_buying();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing( false );

                    }
                } );
            }
        } ).start();
    }
    private void getDatabase_buying(){
        //清空操作
        XbuyList.clear();
        //重新加入
        List<Buying> buy_join = DataSupport.findAll( Buying.class );
        for (Buying buying:buy_join){
            XbuyList.add(new Buying(R.drawable.a,buying.getStrore_title(),R.drawable.b,buying.getThing_title(),
                    buying.getThing_price(),buying.getThing_num(),buying.getGuwuch_rank()));
        }
    }


}
