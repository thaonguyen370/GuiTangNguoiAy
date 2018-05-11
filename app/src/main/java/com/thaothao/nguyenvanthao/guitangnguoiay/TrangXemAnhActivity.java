package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TrangXemAnhActivity extends AppCompatActivity {
    Toolbar toolbar;
    GridView gridView;
    ImageButton back;
    gritviewAdaterHinhAnh gritviewAdaterHinhAnh;
    ArrayList<hinhAnh> al;
    public DataBaseSQLine dataBase_hinh;
    public  DataBaseSQLine dataBase_hinhDaiDien;
    public  DataBaseSQLine dataBase_hinhNen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_trang_xem_anh );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        anhXa();
        al=new ArrayList<>(  );
        dataBase_hinh=new DataBaseSQLine(this,"HinhAnh1",null,4);



        dataBase_hinh.QueryData( "CREATE TABLE IF NOT EXISTS hinh(id INTEGER PRIMARY KEY AUTOINCREMENT,tenAnh VARCHAR(100),moTa VARCHAR(200),hinhAnh BLOB)" );
//        dataBase_hinhDaiDien=new DataBaseSQLine(this,"HinhAnhDaiDien",null,4);
//        dataBase_hinhDaiDien.QueryData( "CREATE TABLE IF NOT EXISTS hinhDaiDien(id INTEGER PRIMARY KEY AUTOINCREMENT,hinhAnh BLOB)" );
//        dataBase_hinhNen=new DataBaseSQLine(this,"HinhAnhNen",null,4);
//        dataBase_hinhNen.QueryData( "CREATE TABLE IF NOT EXISTS hinhNen(id INTEGER PRIMARY KEY AUTOINCREMENT,hinhAnh BLOB)" );

//        Cursor cursor=dataBase_hinh.getData( "SELECT * FROM hinh");
//        if(cursor!=null){
//            while (cursor.moveToNext()){
//                al.add( new hinhAnh(Integer.parseInt( cursor.getString( 0 ).toString().trim() ),cursor.getString( 1 ).toString().trim(),cursor.getString( 2 ).toString(),cursor.getBlob( 3 )) );
//
//            }
//            cursor.close();
//        }
//        gritviewAdaterHinhAnh=new gritviewAdaterHinhAnh( TrangXemAnhActivity.this,R.layout.item_gritview_hinh_anh,al );
//        gridView.setAdapter( gritviewAdaterHinhAnh );
//       gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//   //        diglogGritview(i);
//           }
//       } );
//       back.setOnClickListener( new View.OnClickListener() {
//
//           @Override
//           public void onClick(View view) {
//               Intent intent=new Intent( TrangXemAnhActivity.this,CaNhanActivity.class);
//                startActivity( intent );
//           }
//       } );

    }
    public void anhXa(){
        gridView=(GridView) findViewById( R.id.GritviewAnh );
 //       back=(ImageButton) findViewById( R.id.back_trangXem );

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_gritview_anh,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.themAnh:
//                Intent intent=new Intent( TrangXemAnhActivity.this,QuanLiHinhAnhActivity.class);
//                startActivity( intent );
//                break;
//        }
//        return super.onOptionsItemSelected( item );
//    }
//    public void diglogGritview(final int viTri){
//        Dialog dialog=new Dialog( this );
//        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
//        dialog.setContentView( R.layout.dialog_gritview_anh );
//        dialog.show();
//        ImageButton xoa=(ImageButton) dialog.findViewById( R.id.xoaAnh );
//        ImageButton anhNen=(ImageButton) dialog.findViewById( R.id.chonLamAnhNen );
//        ImageButton anhDaiDien=(ImageButton) dialog.findViewById( R.id.chonLamAnhDaiDien );
//
//        xoa.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        } );
//        anhNen.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dataBase_hinhNen.QueryData( "DELETE FROM hinhNen WHERE id>0 " );
//                hinhAnh hinhAnh=al.get( viTri );
//                //chuyen mang byte sang hinnh
//                byte[] hinh=hinhAnh.getHinh_anh();
////                Bitmap bitmap= BitmapFactory.decodeByteArray( hinh,0,hinh.length );
////                CaNhanActivity.anhNen.setImageBitmap( bitmap );
////                //
////
////                BitmapDrawable bitmapDrawable=(BitmapDrawable) CaNhanActivity.anhNen.getDrawable();
////                Bitmap bitmap1=bitmapDrawable.getBitmap();
////                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
////                bitmap1.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
////                byte [] hinh_anh=byteArrayOutputStream.toByteArray();
//                dataBase_hinhNen.INSERT_hinhNen( hinh );
//            }
//        } );
//        anhDaiDien.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dataBase_hinhDaiDien.QueryData( "DELETE FROM hinhDaiDien WHERE id>0 " );
//                hinhAnh hinhAnh=al.get( viTri );
//                //chuyen mang byte sang hinnh
//                byte[] hinh=hinhAnh.getHinh_anh();
////                Bitmap bitmap= BitmapFactory.decodeByteArray( hinh,0,hinh.length );
////                CaNhanActivity.anhDaiDien.setImageBitmap( bitmap );
//////
////                BitmapDrawable bitmapDrawable=(BitmapDrawable) CaNhanActivity.anhDaiDien.getDrawable();
////                Bitmap bitmap1=bitmapDrawable.getBitmap();
////                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
////                bitmap1.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
////                byte [] hinh_anh=byteArrayOutputStream.toByteArray();
//               dataBase_hinhDaiDien.INSERT_hinhAnhDaiDien( hinh );
//
//            }
//        } );
//
//}
}
