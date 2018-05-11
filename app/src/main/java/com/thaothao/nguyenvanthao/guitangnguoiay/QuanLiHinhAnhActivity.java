package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class QuanLiHinhAnhActivity extends AppCompatActivity {
EditText tenAnh, moTa;
ImageView hinhAnh;
ImageButton mayAnh, thuMuc, ok,xemDS;
//Toolbar toolbar;
;
int flagAnh=123;
int flagThuMuc=234;
public  DataBaseSQLine dataBase_hinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quan_li_hinh_anh );
//        toolbar=(Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        dataBase_hinh=new DataBaseSQLine(this,"HinhAnh1",null,4);
        dataBase_hinh.QueryData( "CREATE TABLE IF NOT EXISTS hinh(id INTEGER PRIMARY KEY AUTOINCREMENT,tenAnh VARCHAR(100),moTa VARCHAR(200),hinhAnh BLOB)" );

        tenAnh=(EditText) findViewById( R.id.txtTenAnh_hinhAnh );
        moTa=(EditText) findViewById( R.id.txtMoTa_hinhAnh );
        hinhAnh=(ImageView) findViewById( R.id.hinhAnh_hinhAnh );
        mayAnh=(ImageButton) findViewById( R.id.mayAnh_hinhAnh );
        thuMuc=(ImageButton) findViewById( R.id.thuwMuc_hinhAnh );
        ok=(ImageButton) findViewById( R.id.ok );
        xemDS=(ImageButton) findViewById( R.id.xemDanhSach );
       mayAnh.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult( intent,flagAnh );
           }
       } );

        thuMuc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent,flagThuMuc );
            }
        } );
        ok.setOnClickListener( new View.OnClickListener() {
            //chuyen mang

            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable=(BitmapDrawable) hinhAnh.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
                bitmap.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
                byte [] hinh_anh=byteArrayOutputStream.toByteArray();
                dataBase_hinh.INSERT_hinhAnh( tenAnh.getText().toString().trim(),moTa.getText().toString().trim(),hinh_anh );
                Toast.makeText( QuanLiHinhAnhActivity.this,"da them thanh cong",Toast.LENGTH_SHORT ).show();

            }
        } );
        xemDS.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDSHinh();
            }
        } );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==flagAnh&&resultCode==RESULT_OK&&data!=null){
            Bitmap bitmap=(Bitmap) data.getExtras().get( "data" );
            hinhAnh.setImageBitmap( bitmap );
        }
        if(requestCode==flagThuMuc&&resultCode==RESULT_OK&&data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream( uri );
                Bitmap bitmap= BitmapFactory.decodeStream( inputStream );
                hinhAnh.setImageBitmap( bitmap );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult( requestCode, resultCode, data );
    }
    public void dialogDSHinh(){

        final Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_quan_li_hinh_anh );
        dialog.show();

        ListView listDSHinh=(ListView) dialog.findViewById( R.id.list_thongTinHHinh_dialog );

        ArrayList<hinhAnh> al=new ArrayList<hinhAnh>(  );
        Cursor data=dataBase_hinh.getData( "SELECT * FROM hinh");
        if(data!=null){
            while (data.moveToNext()){
                al.add( new hinhAnh(Integer.parseInt( data.getString( 0 ).toString().trim() ),data.getString( 1 ).toString().trim(),data.getString( 2 ).toString(),data.getBlob( 3 )) );

            }
        }
        listAdaterHinhAnh adaterHinhAnh=new listAdaterHinhAnh(  QuanLiHinhAnhActivity.this,R.layout.item_list_quan_li_hinh,al  );
        listDSHinh.setAdapter(adaterHinhAnh);

    }
//    public void xacNhanXoa1(final int vitri){
//        final AlertDialog.Builder alertDialog=new AlertDialog.Builder( this);
//
//        alertDialog.setMessage( "ban co muon xoa khong" );
//
//        alertDialog.setPositiveButton( "co", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                String ch="DELETE FROM hinh WHERE id= '"+vitri+"'";
//                dataBase_hinh.QueryData( ch );
//                dialogDSHinh();
//                toast( "thanh cong!" );
//
//
//            }
//        } );
//        alertDialog.setNegativeButton( "khong", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        } );
//        alertDialog.show();
//    }
    public void toast(String thongDiep){
        Toast toast=Toast.makeText(QuanLiHinhAnhActivity.this,thongDiep,Toast.LENGTH_SHORT);
        toast.show();
    }
}
