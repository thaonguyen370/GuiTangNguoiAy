package com.thaothao.nguyenvanthao.guitangnguoiay;

/**
 * Created by NguyenVanThao on 3/29/2018.
 */

public class userOnlineKey extends userOnline {
    public String key;


    public userOnlineKey(String ten, String email, String password, String soDienThoai, String linkHinh, String key) {
        super( ten, email, password, soDienThoai, linkHinh );
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
