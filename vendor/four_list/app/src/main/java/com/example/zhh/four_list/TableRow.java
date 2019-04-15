package com.example.zhh.four_list;

import android.app.Application;
/*
**表格类1
 */
public class TableRow  extends Application {
    private String sell_admin;
    private String sell_admin_name;
    private int id;
    private String name;
    private Double price;
    private int amount;
    private String image;
    private boolean ifcheck;

    public TableRow (String sell_admin, String sell_admin_name, int id, String name, Double price, int amount, String image, boolean Ifcheck) {
        this.sell_admin = sell_admin;
        this.sell_admin_name = sell_admin_name;
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.image = image;
        ifcheck = Ifcheck;
    }

    public String getSell_admin() {
        return sell_admin;
    }

    public void setSell_admin(String sell_admin) {
        this.sell_admin = sell_admin;
    }

    public String getSell_admin_name() {
        return sell_admin_name;
    }

    public void setSell_admin_name(String sell_admin_name) {
        this.sell_admin_name = sell_admin_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public boolean getcheck() {
        return ifcheck;
    }

    public void setIfcheck(boolean ifcheck) {
        this.ifcheck = ifcheck;
    }


}
