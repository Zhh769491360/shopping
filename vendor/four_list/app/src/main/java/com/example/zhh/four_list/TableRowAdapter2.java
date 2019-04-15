package com.example.zhh.four_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZHH on 2018/12/20.
 */

public class TableRowAdapter2 extends ArrayAdapter<TableRow2> {
    private int resourceId;

    public TableRowAdapter2(Context context, int textViewResourceId, List<TableRow2> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TableRow2 row = getItem(position);
        TableRowAdapter2.ViewHolder viewholder = null;
        if(convertView == null) {
            viewholder = new TableRowAdapter2.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewholder.str1 = (TextView) convertView.findViewById(R.id.row_item1);
            viewholder.str2 = (TextView) convertView.findViewById(R.id.row_item2);
            viewholder.str3 = (TextView) convertView.findViewById(R.id.row_item3);
            viewholder.str4 = (TextView) convertView.findViewById(R.id.row_item4);
            viewholder.str5 = (TextView) convertView.findViewById(R.id.row_item5);
            viewholder.str6 = (TextView) convertView.findViewById(R.id.row_item6);
            viewholder.str7 = (TextView) convertView.findViewById(R.id.row_item7);
            viewholder.ifcheck = (CheckBox) convertView.findViewById(R.id.ifcheck);
            convertView.setTag(viewholder);
        } else {
            viewholder = (TableRowAdapter2.ViewHolder) convertView.getTag();
        }
        viewholder.str1.setText("用户:    "+row.getUser_name());
        viewholder.str2.setText("店铺:    "+row.getSell_name());
        viewholder.str3.setText("付款时间:    "+row.getTrade_time());
        viewholder.str4.setText("用户地址:    "+row.getFrom_address());
        viewholder.str5.setText("购买数量:    "+String.valueOf(row.getTrade_num()));
        viewholder.str6.setText("商品名字:  "+row.getTrade_goods_name());
        viewholder.str7.setText("处理状态:    "+row.getTrade_addition());
        viewholder.ifcheck.setChecked(row.getcheck());

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

    private TableRowAdapter2.onCheckListener mOnCheckListener;

    public void setOnCheckListener(TableRowAdapter2.onCheckListener mOnCheckListener) {
        this.mOnCheckListener = mOnCheckListener;
    }

    class ViewHolder {
        TextView str1;
        TextView str2;
        TextView str3;
        TextView str4;
        TextView str5;
        TextView str6;
        TextView str7;
        CheckBox ifcheck;
    }
}
