package com.example.zhh.four_list;

import org.litepal.crud.DataSupport;

/**
 * 卖家类
 */

public class Sell extends DataSupport {

    private String sell_admin;
    private String password;
    private String name;
    private double money;


    public String getSell_admin() {
        return sell_admin;
    }
    public void setSell_admin(String sell_admin) {
        this.sell_admin = sell_admin;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMoney(){
        return money;
    }
    public void setMoney(double money){
        this.money = money;
    }

}
