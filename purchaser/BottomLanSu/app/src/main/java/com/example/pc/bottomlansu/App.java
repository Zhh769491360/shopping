package com.example.pc.bottomlansu;

import org.litepal.crud.DataSupport;

public class App extends DataSupport{
    private String sell_admin;
    private String sell_admin_name;
    private int sell_id;
    private String sell_name;
    private double sell_price;
    private int sell_amount;
    private String sell_image;

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

    public int getSell_id() {
        return sell_id;
    }

    public void setSell_id(int sell_id) {
        this.sell_id = sell_id;
    }

    public String getSell_name() {
        return sell_name;
    }

    public void setSell_name(String sell_name) {
        this.sell_name = sell_name;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public int getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(int sell_amount) {
        this.sell_amount = sell_amount;
    }

    public String getSell_image() {
        return sell_image;
    }

    public void setSell_image(String sell_image) {
        this.sell_image = sell_image;
    }

    public App(String sell_admin, String sell_admin_name, int sell_id, String sell_name, double sell_price, int sell_amount, String sell_image) {
        this.sell_admin = sell_admin;
        this.sell_admin_name = sell_admin_name;
        this.sell_id = sell_id;
        this.sell_name = sell_name;
        this.sell_price = sell_price;
        this.sell_amount = sell_amount;
        this.sell_image = sell_image;
    }
    public void set(App app) {
        this.sell_admin = app.sell_admin;
        this.sell_admin_name = app.sell_admin_name;
        this.sell_id = app.sell_id;
        this.sell_name = app.sell_name;
        this.sell_price = app.sell_price;
        this.sell_amount = app.sell_amount;
        this.sell_image = app.sell_image;
    }
    public App(){}
}
