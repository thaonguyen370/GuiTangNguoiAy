package com.thaothao.nguyenvanthao.guitangnguoiay;

/**
 * Created by NguyenVanThao on 3/15/2018.
 */

public class goiTin {
    String tenNguoiGui;
    String tenNguoiNhan;
    String thongDiep;
    String linkHinhDaiDien;
    String linkHinhThongDiep;
    String thoiGian;

    public goiTin() {
    }

    public goiTin(String tenNguoiGui, String tenNguoiNhan, String thongDiep, String linkHinhDaiDien, String linkHinhThongDiep, String thoiGian) {
        this.tenNguoiGui = tenNguoiGui;
        this.tenNguoiNhan = tenNguoiNhan;
        this.thongDiep = thongDiep;
        this.linkHinhDaiDien = linkHinhDaiDien;
        this.linkHinhThongDiep = linkHinhThongDiep;
        this.thoiGian = thoiGian;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getThongDiep() {
        return thongDiep;
    }

    public void setThongDiep(String thongDiep) {
        this.thongDiep = thongDiep;
    }

    public String getLinkHinhDaiDien() {
        return linkHinhDaiDien;
    }

    public void setLinkHinhDaiDien(String linkHinhDaiDien) {
        this.linkHinhDaiDien = linkHinhDaiDien;
    }

    public String getLinkHinhThongDiep() {
        return linkHinhThongDiep;
    }

    public void setLinkHinhThongDiep(String linkHinhThongDiep) {
        this.linkHinhThongDiep = linkHinhThongDiep;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
