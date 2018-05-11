package com.thaothao.nguyenvanthao.guitangnguoiay;

/**
 * Created by NguyenVanThao on 2/14/2018.
 */

public class user {
    private int id;
    private int anh;
    private String hoTen;
    private String ngaySinh;
    private String user;
    private String password;
    private String soDienThoai;
    private String email;

    public user() {
    }

    public user(int id, int anh,String hoTen, String ngaySinh, String user, String password, String soDienThoai, String email) {
        this.id=id;
        this.anh=anh;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.user = user;
        this.password = password;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAnh() {
        return anh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return email;
    }
}
