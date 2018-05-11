package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    LinearLayout manHinh;
    Button dangNhap;
    Button dangKi;
    Button ngonNgu;
    int xxx=789;
    RadioGroup radio;
    RadioButton online, offline;
    public  static int trangThai=1;
    public static String tenUser="";
   public static DataBaseSQLine dataBase;
    //----------------------------------------------------------------
//Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  this.setTitle("De tai nhom 4");
        setContentView(R.layout.activity_main);
//        toolbar=(Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        dataBase=new DataBaseSQLine(this,"UserThao",null,4);
        dataBase.QueryData( "CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT,anh INTEGER,hoTen VARCHAR(100),ngaySinh VARCHAR(200),user VARCHAR(100),password VARCHAR(100),soDienThoai VARCHAR(100),email VARCHAR(100))" );

        anhXa();

     //   manHinh.setBackgroundResource(R.drawable.logo_chinh1);
        dangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();

            }
        });
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trangThai==0){
                    Intent intent=new Intent( MainActivity.this,loginActivity.class );
                    startActivityForResult( intent,xxx );
                }else {
                    Intent intent=new Intent( MainActivity.this,DangKiDangNhapOnlineActivity.class );
                    startActivity( intent );
                }


            }
        });
        ngonNgu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popmenuNgonNgu();
            }
        } );
        radio.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.online:
                        trangThai=1;
                        break;
                    case  R.id.offline:
                        trangThai=0;
                        break;
                }
            }
        } );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==xxx&&resultCode==RESULT_OK&&data!=null){
            String str=data.getStringExtra( "user_password" );
            String[] x=str.split( "\\s" );
            String user1=x[0];
            String pass1=x[1];
            final Cursor data1=dataBase.getData( "SELECT * FROM user" );
                kiemTraDangNhap kiemTra=new kiemTraDangNhap( user1,pass1 );
                if(kiemTra.admin()){
                    Intent intent=new Intent(MainActivity.this,quanLiDanhSachUserActivity.class);
                    startActivity(intent);
                }else if(kiemTra.nguoiDung()&&kiemTra.kiemTraTK( data1 )){
                    tenUser=user1;
                    Intent intent=new Intent(MainActivity.this,NhanTinActivity.class);
                   startActivity(intent);

                }else{
                    toast( "user hoặc password sai, vui lòng nhập lại" );
                }
//                Intent intent=new Intent( MainActivity.this,CaNhanActivity.class );
//                startActivity( intent );

        }

        super.onActivityResult( requestCode, resultCode, data );
    }
    private void popmenuNgonNgu(){
        PopupMenu popupMenu=new PopupMenu( this,ngonNgu );
        popupMenu.getMenuInflater().inflate( R.menu.popmenu_ngon_ngu,popupMenu.getMenu() );
        popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.vietNam:
                    break;
                    case R.id.english:
                        break;
                    case R.id.nhat:
                        break;
                    case R.id.han:
                        break;
                    case  R.id.trungQuoc:
                        break;
                }
                return false;
            }
        } );
        popupMenu.show();
    }
    public void anhXa(){
        manHinh=(LinearLayout) findViewById(R.id.manHinh);

         dangNhap=(Button) findViewById(R.id.dangNhap);
         dangKi=(Button) findViewById(R.id.dangKi);
         ngonNgu=(Button) findViewById( R.id.ngonNgu );
         radio=(RadioGroup) findViewById( R.id.radio );
         online=(RadioButton) findViewById( R.id.online );
         offline=(RadioButton) findViewById( R.id.offline );


    }

   private void dialog(){
        final Dialog dialog=new Dialog(this);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dangki);

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
       //manHinh.setBackgroundResource(R.drawable.manhinh1);
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
                     String chuoi_x="INSERT INTO user VALUES(null,'"+0+"','"+hoTen+"','"+ngaySinh+"','"+user+"','"+password+"','"+soDT+"','"+email+"')";
                     dataBase.QueryData( chuoi_x );
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
        dialog.show();
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
        Toast toast=Toast.makeText(MainActivity.this,thongDiep,Toast.LENGTH_SHORT);
        toast.show();
    }



}
//        manHinh=(RelativeLayout) findViewById(R.id.manHinh);
//        manHinh.setBackgroundResource(R.drawable.manhinh3);
//        trang =(ImageView) findViewById(R.id.trang);
//        song=MediaPlayer.create(getApplicationContext(),R.raw.nen20trang);
//        song.start();
//        Animation f= AnimationUtils.loadAnimation(this,R.anim.sang_toi);
//        f.reset();
//        trang.clearAnimation();
//        trang.startAnimation(f);
//        trang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                song.stop();
//                Intent intent=new Intent(MainActivity.this,DanhSachBaiHat.class);
//                startActivity(intent);
//            }
//        });