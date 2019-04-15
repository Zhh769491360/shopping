package com.example.pc.bottomlansu;

import org.litepal.crud.DataSupport;

/**
 * Created by ZHH on 2018/10/27.
 */

public class User extends DataSupport{
    private String user_id;//手机号
    private String user_name;
    private String password;//登录密码
    private String payword;//支付密码
    private String user_address;
    private double user_money;
    private byte[] headshot;//头像

    public User(String user_id,String password,String address){
        this.user_id = user_id;
        this.password = password;
        this.user_address = address;
    }

    public String getId() {
        return user_id;
    }
    public void setId(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return user_name;
    }
    public void setName(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayword() {
        return payword;
    }
    public void setPayword(String payword) {
        this.payword = payword;
    }

    public String getAddress() {
        return user_address;
    }
    public void setAddress(String user_address) {
        this.user_address = user_address;
    }

    public double getUser_money(){
        return user_money;
    }
    public void setUser_money(double user_money){
        this.user_money = user_money;
    }

    public User(){
        super();
    }
    public User(byte[] headshot){
        this.headshot = headshot;
    }
    public  byte[] getHeadshot(){
        return headshot;
    }
    public void setHeadshot(byte[] headshot){
        this.headshot = headshot;
    }
}
