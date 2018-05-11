package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class quanLiDanhSachUserActivity extends AppCompatActivity {
ArrayList<user> al;
//DataBaseSQLine dataBase;
listAdapterUser adapterUser;
ListView listUser;
Button back;
Toolbar toolbar1;
ImageButton add_user;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quan_li_danh_sach_user );
        toolbar1=(Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        back=(Button) findViewById( R.id.btnBack );
        listUser=(ListView) findViewById( R.id.listUser );

        //toolbar
        add_user= (ImageButton) findViewById( R.id.addUser );
        al=new ArrayList<>(  );
 //       user user2=new user( 1,2,"3","4","thaonguyen","thaonguyen37","7","8" );
 //       al.add( user2 );
        adapterUser=new listAdapterUser( quanLiDanhSachUserActivity.this,R.layout.test_item_list_user,al );
        listUser.setAdapter( adapterUser );
        //tao bang
//        dataBase=new DataBaseSQLine(this,"UserThao",null,1);
//        dataBase.QueryData( "CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT,anh INTEGER,hoTen VARCHAR(100),ngaySinh VARCHAR(200),user VARCHAR(100),password VARCHAR(100),soDienThoai VARCHAR(100),email VARCHAR(100))" );

        user user3=new user(  );
        user3.setUser( "thaothao" );
        user3.setPassword( "thaothao");
        String chuoi="INSERT INTO user VALUES(null,'"+user3.getAnh()+"','"+user3.getHoTen()+"','"+user3.getNgaySinh()+"','"+user3.getUser()+"','"+user3.getPassword()+"','"+user3.getSoDienThoai()+"','"+user3.getEmail()+"')";
//        String ch="DELETE FROM user WHERE id >1";

        Cursor data=MainActivity.dataBase.getData( "SELECT * FROM user" );
        al.clear();

        while (data.moveToNext()){
            al.add( new user( 1,2,"3","4", data.getString(4).toString().trim(),data.getString( 5 ).toString().trim(),"7","8" ) );

        }

        adapterUser.notifyDataSetChanged();
        add_user.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_user();
 //               Toast.makeText( quanLiDanhSachUserActivity.this,"da insert thanh cong", LENGTH_SHORT ).show();
            }
        } );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( quanLiDanhSachUserActivity.this,MainActivity.class );
                startActivity( intent );

            }
        } );

    }
    public void dialog_user(){
        final Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_dangki_user );

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        //anh xa
        final EditText hoTen_dialog;
        final EditText ngaySinh_dialog;
        final EditText user_dialog;
        final EditText password_dialog;
        final EditText laiPassword_dialog;
        final EditText soDT_dialog;
        final EditText email_dialog;
        final LinearLayout manHinh=(LinearLayout) dialog.findViewById(R.id.manHinhDialog);
        manHinh.setBackgroundResource(R.drawable.nam2);
        Button dongY_dialog;
        Button Huy_dialog;
        hoTen_dialog=(EditText) dialog.findViewById(R.id.hoTen);
        ngaySinh_dialog=(EditText) dialog.findViewById(R.id.ngaySinh);
        user_dialog=(EditText) dialog.findViewById(R.id.user);
        password_dialog=(EditText) dialog.findViewById(R.id.password);
        laiPassword_dialog=(EditText) dialog.findViewById(R.id.laiPassword);
        soDT_dialog=(EditText) dialog.findViewById(R.id.soDT);
        email_dialog=(EditText) dialog.findViewById(R.id.Email);
        dongY_dialog=(Button) dialog.findViewById(R.id.dongY);
        Huy_dialog=(Button) dialog.findViewById(R.id.huy);
        dongY_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen=hoTen_dialog.getText().toString().trim();
                String ngaySinh=ngaySinh_dialog.getText().toString().trim();
                String user=user_dialog.getText().toString().trim();
                String password=password_dialog.getText().toString().trim();
                String laiPassword=laiPassword_dialog.getText().toString().trim();
                String soDT=soDT_dialog.getText().toString().trim();
                String email=email_dialog.getText().toString().trim();
                if(kiemTra(hoTen,ngaySinh,user,password,laiPassword,soDT,email)){

                    user x=  new user( 1,2,hoTen,ngaySinh,user,password,soDT,email ) ;
                    String chuoi_x="INSERT INTO user VALUES(null,'"+x.getAnh()+"','"+x.getHoTen()+"','"+x.getNgaySinh()+"','"+x.getUser()+"','"+x.getPassword()+"','"+x.getSoDienThoai()+"','"+x.getEmail()+"')";
                    MainActivity.dataBase.QueryData( chuoi_x );
                    Cursor data=MainActivity.dataBase.getData( "SELECT * FROM user" );

                    while (data.moveToNext()){
                        al.add( new user( 1,Integer.parseInt( data.getString( 1 ).toString().trim() ),data.getString( 2 ).toString().trim(),data.getString( 3 ).toString().trim(), data.getString(4).toString().trim(),data.getString( 5 ).toString().trim(),data.getString( 6 ).toString().trim(),data.getString( 7 ).toString().trim() ) );

                    }
                    adapterUser.notifyDataSetChanged();
                    toast("thanh cong!");
                    dialog.cancel();
                };
            }
        });

        Huy_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }
    public boolean kiemTra(String hoTen, String ngaySinh, String user, String password,String laiPassword, String soDT,String email){
        if(password.equals(laiPassword)!=true){
            toast("password nhap lai khong trung nhau!");
            return false;
        }
        if(hoTen.equals("")||ngaySinh.equals("")||user.equals("")||password.equals("")||laiPassword.equals("")||soDT.equals("")||email.equals("")){
            toast("nhap day du thong tin theo yeu cau!");
            return false;
        }
        return true;
    }
    public void toast(String thongDiep){
        Toast toast=Toast.makeText(quanLiDanhSachUserActivity.this,thongDiep,Toast.LENGTH_SHORT);
        toast.show();
    }
    public void xacNhanXoa1(final int vitri){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder( this);

        alertDialog.setMessage( "ban co muon xoa khong" );

        alertDialog.setPositiveButton( "co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ch="DELETE FROM user WHERE id="+vitri;
                MainActivity.dataBase.QueryData( ch );
                al.remove( vitri );
                adapterUser.notifyDataSetChanged();
                toast( "thanh cong!" );


            }
        } );
        alertDialog.setNegativeButton( "khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        } );
        alertDialog.show();
    }
    public void dialog_capnhat(final int vitri){
        final Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.cap_nhat_user );


        dialog.setCanceledOnTouchOutside(false);
        //anh xa
        final EditText hoTen_dialog;
        final EditText ngaySinh_dialog;
        final EditText user_dialog;
        final EditText password_dialog;
        final EditText laiPassword_dialog;
        final EditText soDT_dialog;
        final EditText email_dialog;
        final LinearLayout manHinh=(LinearLayout) dialog.findViewById(R.id.manHinhDialog);
        manHinh.setBackgroundResource(R.drawable.nam2);
        Button dongY_dialog;
        Button Huy_dialog;
        hoTen_dialog=(EditText) dialog.findViewById(R.id.hoTen);
        ngaySinh_dialog=(EditText) dialog.findViewById(R.id.ngaySinh);
        user_dialog=(EditText) dialog.findViewById(R.id.user);
        password_dialog=(EditText) dialog.findViewById(R.id.password);
        laiPassword_dialog=(EditText) dialog.findViewById(R.id.laiPassword);
        soDT_dialog=(EditText) dialog.findViewById(R.id.soDT);
        email_dialog=(EditText) dialog.findViewById(R.id.Email);
        dongY_dialog=(Button) dialog.findViewById(R.id.dongY);
        Huy_dialog=(Button) dialog.findViewById(R.id.huy);
        dongY_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen=hoTen_dialog.getText().toString().trim();
                String ngaySinh=ngaySinh_dialog.getText().toString().trim();
                String user=user_dialog.getText().toString().trim();
                String password=password_dialog.getText().toString().trim();
                String laiPassword=laiPassword_dialog.getText().toString().trim();
                String soDT=soDT_dialog.getText().toString().trim();
                String email=email_dialog.getText().toString().trim();
                if(kiemTra(hoTen,ngaySinh,user,password,laiPassword,soDT,email)){

                    user x=  new user( 1,2,hoTen,ngaySinh,user,password,soDT,email ) ;
                    String chuoi_x="UPDATE user SET anh="+x.getAnh()+",hoTen='"+x.getHoTen()+"',ngaySinh='"+x.getNgaySinh()+"',user='"+x.getUser()+"',password='"+x.getPassword()+"',soDienThoai='"+x.getSoDienThoai()+"',email='"+x.getEmail()+"'"+" WHERE id ="+vitri;
                    MainActivity.dataBase.QueryData( chuoi_x );
                    al.set( vitri,x );
                    adapterUser.notifyDataSetChanged();
                    toast("thanh cong!");
                    dialog.cancel();
                }{
                    toast( "that bai" );
                };
            }
        });

        Huy_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
