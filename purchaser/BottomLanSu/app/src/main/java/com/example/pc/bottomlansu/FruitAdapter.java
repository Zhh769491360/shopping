package com.example.pc.bottomlansu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context mContext;
    private List<Fruit>mFruitList;


    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       if (mContext==null){

           mContext=viewGroup.getContext();

        }
        View view = LayoutInflater.from( mContext ).inflate(
                R.layout.fruit_item,viewGroup,false
        );
       final ViewHolder holder = new ViewHolder( view );
       holder.cardView.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int position = holder.getAdapterPosition();
               Fruit fruit = mFruitList.get( position );
               FruitActivity.actionStart(mContext, fruit);
           }
       } );
       //return new ViewHolder( view );
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Fruit fruit = mFruitList.get( i );
        viewHolder.fruitName.setText( fruit.getName() );
        Glide.with(mContext).load( fruit.getImageId()).into
                (viewHolder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view.findViewById( R.id.CardView_Id );
            fruitImage=(ImageView)view.findViewById( R.id.fruit_image );
            fruitName=(TextView)view.findViewById( R.id.fruit_name );
        }
    }


}
