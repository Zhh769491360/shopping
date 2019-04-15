package com.example.zhh.four_list;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TableRowAdapter extends ArrayAdapter<TableRow> {

    private int resourceId;

    int[] images_a = new int[]{
            R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,
            R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,
    };

    int[] images_b = new int[]{
            R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,
            R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10,
    };
    int[] images_c = new int[]{
            R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,
            R.drawable.c6,R.drawable.c7,R.drawable.c8,R.drawable.c9,R.drawable.c10,
    };

    public TableRowAdapter(Context context, int textViewResourceId, List<TableRow> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TableRow row = getItem(position);
        ViewHolder viewholder = null;
        if(convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewholder.str1 = (TextView) convertView.findViewById(R.id.row_item1);
            viewholder.str2 = (TextView) convertView.findViewById(R.id.row_item2);
            viewholder.str3 = (TextView) convertView.findViewById(R.id.row_item3);
            viewholder.str4 = (TextView) convertView.findViewById(R.id.row_item4);
            viewholder.str5 = (TextView) convertView.findViewById(R.id.row_item5);
            viewholder.str6 = (TextView) convertView.findViewById(R.id.row_item6);
            viewholder.str7 = (ImageView) convertView.findViewById(R.id.row_item7);
            viewholder.ifcheck = (CheckBox) convertView.findViewById(R.id.ifcheck);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.str1.setText(row.getSell_admin());
        viewholder.str2.setText(row.getSell_admin_name());
        viewholder.str3.setText(String.valueOf(row.getId()));
        viewholder.str4.setText(row.getName());
        viewholder.str5.setText(String.valueOf(row.getPrice()));
        viewholder.str6.setText(String.valueOf(row.getAmount()));
        viewholder.ifcheck.setChecked(row.getcheck());

        if (row.getSell_admin().equals("13566666661")){
            viewholder.str7.setImageDrawable(ContextCompat.getDrawable(getContext(),images_a[position]));
        }else if (row.getSell_admin().equals("13566666662")){
            viewholder.str7.setImageDrawable(ContextCompat.getDrawable(getContext(),images_a[position + 5]));
        }else if (row.getSell_admin().equals("13566666663")){
            viewholder.str7.setImageDrawable(ContextCompat.getDrawable(getContext(),images_b[position]));
        }else if (row.getSell_admin().equals("13566666664")){
            viewholder.str7.setImageDrawable(ContextCompat.getDrawable(getContext(),images_b[position + 5]));
        }else if (row.getSell_admin().equals("13566666665")){
            viewholder.str7.setImageDrawable(ContextCompat.getDrawable(getContext(),images_c[position]));
        }



        ////////

        viewholder.ifcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCheckListener.onCheck(position);
            }
        });

        return convertView;
    }



    public interface onCheckListener {
        void onCheck(int position);
    }

    private onCheckListener mOnCheckListener;

    public void setOnCheckListener(onCheckListener mOnCheckListener) {
        this.mOnCheckListener = mOnCheckListener;
    }

    class ViewHolder {
        TextView str1;
        TextView str2;
        TextView str3;
        TextView str4;
        TextView str5;
        TextView str6;
        ImageView str7;
        CheckBox ifcheck;
    }
}
