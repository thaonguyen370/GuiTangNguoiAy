package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.database.Cursor;

/**
 * Created by NguyenVanThao on 2/14/2018.
 */

public class kiemTraDangNhap {
    String user;
    String password;

    public kiemTraDangNhap(String user, String password) {
        this.user = user;
        this.password = password;
    }
    public boolean admin(){
        if(user.equals( "thaonguyen" )&&password.equals( "12345" )&&user.length()>0&&password.length()>0)
            return true;
        return false;
    }
    public boolean nguoiDung(){
        if (user.length()>0&&password.length()>0){
            return true;
        }
        return false;
    }
    public boolean kiemTraTK(Cursor data){
        int flag=0;
        while (data.moveToNext()){
            String userq=data.getString( 4 ).toString().trim();
            String pass=data.getString( 5 ).toString().trim();
            if(userq.equals( user )&&pass.equals( password )){
                flag=1;
                break;
            }
        }
        if (flag==1) return true;
        return false;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
