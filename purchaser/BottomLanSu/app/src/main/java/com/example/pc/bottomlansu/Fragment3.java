package com.example.pc.bottomlansu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Fragment3 extends BaseFragment {

    private ConvenientBanner convenientBanner;
    private List<Integer> imgs=new ArrayList<>();
    private Button watch;
    private TextView show;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment3, container, false);
    }

    @Override
    public void onStart() {
        imgs.add(R.drawable.e1);
        imgs.add(R.drawable.e2);
        imgs.add(R.drawable.e4);
        imgs.add(R.drawable.e5);
        //找控件
        convenientBanner = (ConvenientBanner)getView().findViewById(R.id.convenientBanner);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        },imgs) //设置需要切换的View
                .setPointViewVisible(true)    //设置指示器是否可见
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dr,R.drawable.select})
                //设置指示器位置（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(2000)     //设置自动切换（同时设置了切换时间间隔）
                .setManualPageable(true)  //设置手动影响（设置了该项无法手动切换）

        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer)
        ;
//        convenientBanner.setManualPageable(false);//设置不能手动影响

        watch = getView().findViewById( R.id.watch_buy );
        show = getView().findViewById( R.id.watch_content );
        watch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "http://192.168.43.82:8080/Try/sell/get_record_user.jsp";
                String link = "record";
                String content = "";
                List<User> users = DataSupport.findAll( User.class );
                for (User user:users){
                    content = user.getId();
                }

                HttpUtilEila.PostSendHttpRequester( address, link, content, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.d( "Fragment3","false" );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseData = response.body().string() ;
                        getActivity().runOnUiThread( new Runnable() {
                            @Override
                            public void run() {
                                show.setText( responseData );
                            }
                        } );
                    }
                } );
            }
        } );



        super.onStart();
    }
    public class LocalImageHolderView implements Holder<Integer>{

        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

}
