package com.thaothao.nguyenvanthao.guitangnguoiay;

import java.io.Serializable;

/**
 * Created by NguyenVanThao on 3/14/2018.
 */

public class userOnline implements Serializable {
    String ten;
    String email;
    String password;
    String soDienThoai;
    String linkHinh;
    public String key="";

    public userOnline() {
    }

    public userOnline(String ten, String email, String password, String soDienThoai, String linkHinh) {
        this.ten = ten;
        this.email = email;
        this.password = password;
        this.soDienThoai = soDienThoai;
        this.linkHinh = linkHinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }
}
