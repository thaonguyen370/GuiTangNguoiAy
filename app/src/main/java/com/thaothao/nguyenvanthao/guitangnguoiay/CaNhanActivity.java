package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class CaNhanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ImageButton tinNhan;
    ImageButton banBe;
    ImageButton bangTin;
    ImageButton amNhac;
    ImageButton caNhan;
    public static ImageView anhNen;
   public static ImageView anhDaiDien;
    TextView tenUser;
    Button btnAnh;
    Button btnNhatKi;
    Button btnDang;

    private DatabaseReference mDatabase;
    private ArrayList<userOnline> doituong;

    int flagAnh=123;
    int flagThuMuc=234;
    int flagAnh1=345;
    int flagThuMuc1=456;
    public  DataBaseSQLine dataBase_hinhDaiDien;
    public  DataBaseSQLine dataBase_hinhNen;
  //  DrawerLayout
    private DrawerLayout mDrawableLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ca_nhan );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        mDrawableLayout=(DrawerLayout) findViewById( R.id.drawer );
        mToggle=new ActionBarDrawerToggle( this,mDrawableLayout,R.string.open,R.string.close );
        mDrawableLayout.addDrawerListener( mToggle );
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        //bat su kien cho navigation
        NavigationView navigationView=(NavigationView) findViewById( R.id.navigation );
        navigationView.setNavigationItemSelectedListener( this );
        anhXa();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        doituong=new ArrayList<>(  );
        if (MainActivity.trangThai==0){
            tenUser.setText( MainActivity.tenUser );
            //  anhNen.setBackgroundResource( R.drawable.nam2 );
            dataBase_hinhDaiDien=new DataBaseSQLine(this,"HinhAnhDaiDien",null,4);
            dataBase_hinhDaiDien.QueryData( "CREATE TABLE IF NOT EXISTS hinhDaiDien(id INTEGER PRIMARY KEY AUTOINCREMENT,hinhAnh BLOB)" );
            dataBase_hinhNen=new DataBaseSQLine(this,"HinhAnhNen",null,4);
            dataBase_hinhNen.QueryData( "CREATE TABLE IF NOT EXISTS hinhNen(id INTEGER PRIMARY KEY AUTOINCREMENT,hinhAnh BLOB)" );

            Cursor cursor=dataBase_hinhDaiDien.getData( "SELECT * FROM hinhDaiDien" );

            if(cursor!=null){
                while (cursor.moveToNext()){

                    byte[] hinh=cursor.getBlob( 1 );
                    Bitmap bitmap= BitmapFactory.decodeByteArray( hinh,0,hinh.length );
                    anhDaiDien.setImageBitmap( bitmap );
                }
                cursor.close();
            }
            Cursor cursor1=dataBase_hinhNen.getData( "SELECT * FROM hinhNen" );

            if(cursor1!=null){
                while (cursor1.moveToNext()){

                    byte[] hinh=cursor1.getBlob( 1 );
                    Bitmap bitmap= BitmapFactory.decodeByteArray( hinh,0,hinh.length );
                    anhNen.setImageBitmap( bitmap );
                }
                cursor1.close();
            }
        }else {
            mDatabase.child( "user" ).addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    userOnline banBe= dataSnapshot.getValue(userOnline.class);
                    if (banBe.getEmail().trim().equals( DangKiDangNhapOnlineActivity.tk.trim() )){
                        doituong.add( banBe );
                        tenUser.setText( doituong.get( 0 ).getTen() );
                        Picasso.get().load( doituong.get( 0 ).getLinkHinh().toString().trim()).into(anhDaiDien);
                        Picasso.get().load( doituong.get( 0 ).getLinkHinh().toString().trim()).into(anhNen);
                    }



                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            } );

        }



        tinNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this, NhanTinActivity.class );
                startActivity( intent );
            }
        } );
        banBe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this, banBeActivity.class );
                startActivity( intent );
            }
        } );
        bangTin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this, BangTinActivity.class );
                startActivity( intent );
            }
        } );
        amNhac.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this, DanhSachBaiHat.class );
                startActivity( intent );
            }
        } );
        caNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
        btnAnh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this,TrangXemAnhActivity.class );
                startActivity( intent );
            }
        } );
        btnNhatKi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this,MapsActivity.class );
            startActivity( intent );
        }
        } );
        anhNen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.trangThai==0){
                    diglogAnhNen();
                }

            }
        } );
        anhDaiDien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.trangThai==0){
                    diglogAnhDaiDien();
                }

            }
        } );

    }
    void anhXa(){
        btnAnh=(Button) findViewById( R.id.btnAnh );
        btnNhatKi=(Button) findViewById( R.id.btnNhatKi );
        anhNen=(ImageView) findViewById( R.id.anhNenCaNhan );
        btnDang=(Button) findViewById( R.id.btnDang );
        anhDaiDien=(ImageView) findViewById( R.id.anhDaiDienCaNhan );

        tenUser=(TextView) findViewById( R.id.tenUserCaNhan );
        tinNhan=(ImageButton) findViewById( R.id.btnTinNhan );
        banBe=(ImageButton) findViewById( R.id.btnBanBe );
        bangTin=(ImageButton) findViewById( R.id.btnBangTin );
        amNhac=(ImageButton) findViewById( R.id.btnAmNhac );
        caNhan=(ImageButton) findViewById( R.id.btnCaNhan );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ca_nhan,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected( item )){
            return true;
        }
        switch (item.getItemId()){
            case R.id.dangXuat_item:
                Intent intent=new Intent( CaNhanActivity.this,MainActivity.class);
                startActivity( intent );

                break;

        }
        return super.onOptionsItemSelected( item );
    }
    public void diglogAnhDaiDien(){
        Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_thay_doi_anh_dai_dien );
        ImageButton folder=(ImageButton) dialog.findViewById( R.id.folderDialog );
        ImageButton chupAnh=(ImageButton) dialog.findViewById( R.id.anhDialog );
        ImageButton coSoDuLieu=(ImageButton) dialog.findViewById( R.id.coSoDuLieuDialog );
        folder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent,flagThuMuc );
            }
        } );
        chupAnh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult( intent,flagAnh );
            }
        } );
        coSoDuLieu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this,QuanLiHinhAnhActivity.class );
                startActivity( intent );
            }
        } );
        dialog.show();
    }
    public void diglogAnhNen(){
        Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_thay_doi_anh_dai_dien );
        ImageButton folder=(ImageButton) dialog.findViewById( R.id.folderDialog );
        ImageButton chupAnh=(ImageButton) dialog.findViewById( R.id.anhDialog );
        ImageButton coSoDuLieu=(ImageButton) dialog.findViewById( R.id.coSoDuLieuDialog );
        folder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent,flagThuMuc1 );
            }
        } );
        chupAnh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult( intent,flagAnh1 );
            }
        } );
        coSoDuLieu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( CaNhanActivity.this,QuanLiHinhAnhActivity.class );
                startActivity( intent );
            }
        } );
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==flagAnh&&resultCode==RESULT_OK&&data!=null){
           dataBase_hinhDaiDien.QueryData( "DELETE FROM hinhDaiDien WHERE id>0 " );
            Bitmap bitmap=(Bitmap) data.getExtras().get( "data" );
            anhDaiDien.setImageBitmap( bitmap );
            BitmapDrawable bitmapDrawable=(BitmapDrawable) anhDaiDien.getDrawable();
            Bitmap bitmap1=bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
            bitmap1.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
            byte [] hinh_anh=byteArrayOutputStream.toByteArray();
            dataBase_hinhDaiDien.INSERT_hinhAnhDaiDien( hinh_anh );
  //          Toast.makeText( CaNhanActivity.this,"da them thanh cong",Toast.LENGTH_SHORT ).show();
        }
        if(requestCode==flagThuMuc&&resultCode==RESULT_OK&&data!=null){
            Uri uri=data.getData();
        dataBase_hinhDaiDien.QueryData( "DELETE FROM hinhDaiDien WHERE id>0 " );
            try {
                InputStream inputStream=getContentResolver().openInputStream( uri );
                Bitmap bitmap= BitmapFactory.decodeStream( inputStream );
                anhDaiDien.setImageBitmap( bitmap );
                BitmapDrawable bitmapDrawable=(BitmapDrawable) anhDaiDien.getDrawable();
                Bitmap bitmap1=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
                bitmap1.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
                byte [] hinh_anh=byteArrayOutputStream.toByteArray();
                dataBase_hinhDaiDien.INSERT_hinhAnhDaiDien( hinh_anh );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        if(requestCode==flagAnh1&&resultCode==RESULT_OK&&data!=null){
            dataBase_hinhNen.QueryData( "DELETE FROM hinhNen WHERE id>0 " );
            Bitmap bitmap=(Bitmap) data.getExtras().get( "data" );

            anhNen.setImageBitmap( bitmap );
            BitmapDrawable bitmapDrawable=(BitmapDrawable) anhNen.getDrawable();
            Bitmap bitmap1=bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
            bitmap1.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
            byte [] hinh_anh=byteArrayOutputStream.toByteArray();
            dataBase_hinhNen.INSERT_hinhNen( hinh_anh );
        }
        if(requestCode==flagThuMuc1&&resultCode==RESULT_OK&&data!=null){
            dataBase_hinhNen.QueryData( "DELETE FROM hinhNen WHERE id>0 " );
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream( uri );
                Bitmap bitmap= BitmapFactory.decodeStream( inputStream );
                anhNen.setImageBitmap( bitmap );
                BitmapDrawable bitmapDrawable=(BitmapDrawable) anhNen.getDrawable();
                Bitmap bitmap1=bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(  );
                bitmap1.compress( Bitmap.CompressFormat.PNG,100,byteArrayOutputStream );
                byte [] hinh_anh=byteArrayOutputStream.toByteArray();
                dataBase_hinhNen.INSERT_hinhNen( hinh_anh );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.phim:
                Intent intent1=new Intent(  );
                intent1.setAction( Intent.ACTION_VIEW );
                intent1.setData( Uri.parse( "http://www.phimmoi.net/" ) );
                startActivity( intent1 );
                break;
            case R.id.googleMap:
//                Toast.makeText( CaNhanActivity.this, "google map", Toast.LENGTH_LONG ).show();
                intent=new Intent( CaNhanActivity.this,MapsActivity.class );
                startActivity( intent );
                break;
            case R.id.troChoi:
                Toast.makeText( CaNhanActivity.this, "tro choi chua dc cai dat", Toast.LENGTH_LONG ).show();
                break;
            case R.id.menuTinNhan:
            intent=new Intent( CaNhanActivity.this,HuongDanActivity.class );
                startActivity( intent );
                break;
            case R.id.menuBanBe:
                 intent=new Intent( CaNhanActivity.this,HuongDanActivity.class );
                startActivity( intent );
                break;
            case R.id.menuBanTin:
               intent=new Intent( CaNhanActivity.this,HuongDanActivity.class );
                startActivity( intent );
                break;
            case R.id.menuAmNhac:
                 intent=new Intent( CaNhanActivity.this,HuongDanActivity.class );
                startActivity( intent );
                break;
            case R.id.menuTrangChu:
                intent=new Intent( CaNhanActivity.this,HuongDanActivity.class );
                startActivity( intent );
                break;
        }
        return false;
    }
}
  //implementation 'com.google.android.gms:play-services:11.8.0'
//HuongDanActivity