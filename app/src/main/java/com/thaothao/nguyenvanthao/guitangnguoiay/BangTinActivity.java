package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class BangTinActivity extends AppCompatActivity {
    LinearLayout manHinh;
    Toolbar toolbar;
    ImageButton tinNhan;
    ImageButton banBe;
    ImageButton bangTin;
    ImageButton amNhac;
    ImageButton caNhan;
    RecyclerView recyclerView;
    Button dangTin;
    TextView editDangTin;
    private DatabaseReference mDatabase;
    ArrayList<goiTin> al;
    adapterRecyclerviewBangTin adapterRecyclerview;
    int flagKetQua= 9999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bang_tin );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        anhXa();
        manHinh.setBackgroundResource( R.drawable.ban_tin );
        mDatabase = FirebaseDatabase.getInstance().getReference();
        al=new ArrayList<>(  );
        initRecyclerview();
        al.clear();
        String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
        mDatabase.child( "bang tin"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                goiTin goiTin1= dataSnapshot.getValue(goiTin.class);

                al.add( new goiTin( goiTin1.getTenNguoiGui().toString(), goiTin1.getTenNguoiNhan().toString(),goiTin1.getThongDiep().toString(),goiTin1.getLinkHinhDaiDien().toString(),goiTin1.getLinkHinhThongDiep().toString(),goiTin1.getThoiGian().toString() ));
                adapterRecyclerview.notifyDataSetChanged();
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
        tinNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( BangTinActivity.this, NhanTinActivity.class );
                startActivity( intent );
            }
        } );
        banBe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( BangTinActivity.this, banBeActivity.class );
                startActivity( intent );
            }
        } );
        bangTin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
        amNhac.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( BangTinActivity.this, DanhSachBaiHat.class );
                startActivity( intent );
            }
        } );
        caNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( BangTinActivity.this, CaNhanActivity.class );
                startActivity( intent );
            }
        } );
        dangTin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent( BangTinActivity.this,QuanLyAnhFirebase.class );
              startActivityForResult( intent,flagKetQua );
//                startActivity( intent );
            }
        } );
//        new Thread( new Runnable() {
//            @Override
//            public void run() {
//                String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
//                Intent intent=getIntent();
//                hinhAnhFirebase hinhAnhFirebase1=(hinhAnhFirebase) intent.getSerializableExtra( "bai dang" );
//                String mess=hinhAnhFirebase1.getTen().toString();
//                String linkHinh=hinhAnhFirebase1.getLinkHinh().toString();
//                Calendar calendar=Calendar.getInstance();
//                goiTin goiTinGui=new goiTin( DangKiDangNhapOnlineActivity.tk.toString().trim(),emailBan.trim(),tinNhan,"","",String.valueOf( calendar.getTime() ) );
//                mDatabase.child( "bang tin"+k[0]).push().setValue( goiTinGui );
//            }
//        } ).start();


    }
    void anhXa(){
        manHinh=(LinearLayout) findViewById( R.id.manHinhBangTin );
        tinNhan=(ImageButton) findViewById( R.id.btnTinNhan );
        banBe=(ImageButton) findViewById( R.id.btnBanBe );
        bangTin=(ImageButton) findViewById( R.id.btnBangTin );
        amNhac=(ImageButton) findViewById( R.id.btnAmNhac );
        caNhan=(ImageButton) findViewById( R.id.btnCaNhan );
        dangTin=(Button) findViewById( R.id.dangTin );
        editDangTin=(TextView) findViewById( R.id.EditDangTin );

    }
    private void initRecyclerview(){
        recyclerView=(RecyclerView) findViewById( R.id.recyclerview );
        recyclerView.setHasFixedSize( true );//toi uu hoa du lieu trong item
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this,LinearLayoutManager.VERTICAL,false );
        recyclerView.setLayoutManager( linearLayoutManager );
        //dai phan cach
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration( this,linearLayoutManager.getOrientation() );
//        recyclerView.addItemDecoration( dividerItemDecoration );
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration( recyclerView.getContext(),DividerItemDecoration.VERTICAL );
        Drawable drawable= ContextCompat.getDrawable( getApplicationContext(),R.drawable.custom_dvider_recyclerview );
        dividerItemDecoration.setDrawable( drawable );
        recyclerView.addItemDecoration( dividerItemDecoration );

         adapterRecyclerview=new adapterRecyclerviewBangTin( al,BangTinActivity.this );
        recyclerView.setAdapter( adapterRecyclerview );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==flagKetQua&&resultCode==RESULT_OK&&data!=null){
            hinhAnhFirebase hinhAnhFirebase1=(hinhAnhFirebase) data.getSerializableExtra( "bai dang" );
            final String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
            final String mess=hinhAnhFirebase1.getTen().toString();
            final String linkHinh=hinhAnhFirebase1.getLinkHinh().toString();
            final Calendar calendar=Calendar.getInstance();
             final goiTin goiTinGui=new goiTin( DangKiDangNhapOnlineActivity.tk.toString().trim(),DangKiDangNhapOnlineActivity.tk.toString().trim(),mess,"",linkHinh,String.valueOf( calendar.getTime() ) );

            mDatabase.child( "bang tin"+k[0]).push().setValue( goiTinGui );
            mDatabase.child( "ban be"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    userOnline banBe= dataSnapshot.getValue(userOnline.class);
                    String mailban=banBe.getEmail().toString();
                    String [] k1=mailban.split( "@" );
                    goiTin goiTinGui=new goiTin( DangKiDangNhapOnlineActivity.tk.toString().trim(),mailban,mess,banBe.getLinkHinh().toString(),linkHinh,String.valueOf( calendar.getTime() ) );

                    mDatabase.child( "bang tin"+k1[0]).push().setValue( goiTinGui );
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


        super.onActivityResult( requestCode, resultCode, data );
    }
}
