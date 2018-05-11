package com.thaothao.nguyenvanthao.guitangnguoiay;

/**
 * Created by NguyenVanThao on 2/20/2018.
 */

public class hinhAnh {
    private int id;
    private String tenAnh;
    private String moTa;
    private byte[] hinh_anh;

    public hinhAnh(int id, String tenAnh, String moTa, byte[] hinh_anh) {
        this.id = id;
        this.tenAnh = tenAnh;
        this.moTa = moTa;
        this.hinh_anh = hinh_anh;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenAnh(String tenAnh) {
        this.tenAnh = tenAnh;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setHinh_anh(byte[] hinh_anh) {
        this.hinh_anh = hinh_anh;
    }

    public int getId() {
        return id;
    }

    public String getTenAnh() {
        return tenAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public byte[] getHinh_anh() {
        return hinh_anh;
    }
}