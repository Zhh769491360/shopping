package com.example.zhh.four_list;

import org.litepal.crud.DataSupport;

/*
**交易信息类
**
 */
public class Trade extends DataSupport{
    private String user_name;
    private String sell_name;
    private String trade_time;
    private int trade_num;
    private String trade_goods_name;
    private String trade_addition;
    private String from_address;

    public Trade (String user_name, String sell_name, String trade_time, String from_address, int trade_num, String trade_goods_name,String trade_addition) {
        this.user_name = user_name;
        this.sell_name = sell_name;
        this.trade_time = trade_time;
        this.trade_num = trade_num;
        this.trade_goods_name = trade_goods_name;
        this.trade_addition = trade_addition;
        this.from_address = from_address;

    }
    public Trade(){}

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSell_name() {
        return sell_name;
    }

    public void setSell_name(String sell_name) {
        this.sell_name = sell_name;
    }
    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public int getTrade_num() {
        return trade_num;
    }

    public void setTrade_num(int trade_num) {
        this.trade_num = trade_num;
    }

    public String getTrade_goods_name() {
        return trade_goods_name;
    }

    public void setTrade_goods_name(String trade_goods_name) {
        this.trade_goods_name = trade_goods_name;
    }

    public String getTrade_addition() {
        return trade_addition;
    }

    public void setTrade_addition(String trade_addition) {
        this.trade_addition = trade_addition;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }
}
