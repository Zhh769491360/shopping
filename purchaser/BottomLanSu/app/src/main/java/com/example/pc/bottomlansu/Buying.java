package com.example.pc.bottomlansu;

import android.widget.CheckBox;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Buying extends DataSupport{

    //店铺标志
    private int store_img;
    //店铺文字
    private String strore_title;
    //商品标志
    private int thing_img;
    //商品文字
    private String thing_title;
    //价格
    private double Thing_price;
    //数目
    private int Thing_num;
    //服务于购物车
    private int Guwuch_rank;

    //private CheckBox chooseBox;
    public Buying(){}
    public int getStore_img() {
        return store_img;
    }

    public void setStore_img(int store_img) {
        this.store_img = store_img;
    }

    public String getStrore_title() {
        return strore_title;
    }

    public void setStrore_title(String strore_title) {
        this.strore_title = strore_title;
    }

    public int getThing_img() {
        return thing_img;
    }

    public void setThing_img(int thing_img) {
        this.thing_img = thing_img;
    }

    public String getThing_title() {
        return thing_title;
    }

    public void setThing_title(String thing_title) {
        this.thing_title = thing_title;
    }

    public double getThing_price() {
        return Thing_price;
    }

    public void setThing_price(double thing_price) {
        Thing_price = thing_price;
    }

    public int getThing_num() {
        return Thing_num;
    }

    public void setThing_num(int thing_num) {
        Thing_num = thing_num;
    }

    public int getGuwuch_rank() {
        return Guwuch_rank;
    }

    public void setGuwuch_rank(int guwuch_rank) {
        Guwuch_rank = guwuch_rank;
    }

    public Buying(int store_img, String strore_title, int thing_img, String thing_title, double thing_price, int thing_num,int Gu) {
        this.store_img = store_img;
        this.strore_title = strore_title;
        this.thing_img = thing_img;
        this.thing_title = thing_title;
        Thing_price = thing_price;
        Thing_num = thing_num;
        this.Guwuch_rank = Gu;
    }
}
