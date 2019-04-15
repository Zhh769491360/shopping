package com.example.pc.bottomlansu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BuyingAdapter extends RecyclerView.Adapter<BuyingAdapter.ViewHolder>  {

    private List<Buying> mbuyList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox Ecb;
        ImageView shopimage,thingimage;
        Button leftbutton,rightbutton,shopbutton,thingbutton;
        TextView thingprice,thingnum;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            Ecb = (CheckBox)itemView.findViewById( R.id.shopcheckboxId );//单选框
            LogUtil.d( "checkBox"," "+Ecb.isChecked() );

            shopimage = (ImageView)itemView.findViewById( R.id.shopimageId );
            thingimage = (ImageView)itemView.findViewById( R.id.thingimageId );

            leftbutton = (Button)itemView.findViewById( R.id.leftbuttonId );
            rightbutton = (Button)itemView.findViewById( R.id.rightbuttonId );
            shopbutton = (Button)itemView.findViewById( R.id.shopbuttonId );
            thingbutton = (Button)itemView.findViewById( R.id.thingbuttonId );

            thingprice = (TextView)itemView.findViewById( R.id.thingpriceId );
            thingnum = (TextView)itemView.findViewById( R.id.thingnumId );

        }
    }

    public BuyingAdapter(List<Buying> mbuyList) {
        this.mbuyList = mbuyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).
                inflate( R.layout.buy_item,viewGroup,false );
        ViewHolder holder = new ViewHolder( view );
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Buying buying = mbuyList.get( i );

        //将复选框的初始全都设置为选中
        viewHolder.shopimage.setImageResource( buying.getStore_img());
        viewHolder.shopimage.setImageResource( buying.getStore_img());

        viewHolder.shopbutton.setText( buying.getStrore_title() );
        viewHolder.thingbutton.setText( buying.getThing_title() );
        viewHolder.thingimage.setImageResource( buying.getThing_img() );
        viewHolder.thingnum.setText( buying.getThing_num()+"   编号");
        viewHolder.thingprice.setText( "$"+buying.getThing_price() );

        viewHolder.leftbutton.setText( "数目: " );
        viewHolder.rightbutton.setText( " "+buying.getGuwuch_rank() );
        //现在没有数据传递
        //viewHolder.leftbutton.setText( xunHao.get_title() );
        //viewHolder.rightbutton.setText( xunHao.get_newWord());
    }
    //  添加数据
    public void addData(int position,Buying buying) {
//      在list中添加数据，并通知条目加入一条
        //一起插入数据库中的对象谢谢
        mbuyList.add(position,buying);
        //添加动画
        notifyItemInserted(position);
    }
    //  删除数据
    public void removeData(int position) {
        //一起删除数据库中的对象谢谢
        mbuyList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mbuyList.size();
    }
}
