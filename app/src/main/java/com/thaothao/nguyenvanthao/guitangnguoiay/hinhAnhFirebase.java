package com.thaothao.nguyenvanthao.guitangnguoiay;

import java.io.Serializable;

/**
 * Created by NguyenVanThao on 3/17/2018.
 */
public class hinhAnhFirebase implements Serializable {
    String ten;
    String linkHinh;

    public hinhAnhFirebase() {
    }

    public hinhAnhFirebase(String ten, String linkHinh) {
        this.ten = ten;
        this.linkHinh = linkHinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }
}