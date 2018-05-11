package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NhanTinActivity extends AppCompatActivity {
Toolbar toolbar;
LinearLayout manHinh;
    EditText txtTinNhan;
    ImageButton tinNhan;
    ImageButton banBe;
    ImageButton bangTin;
    ImageButton amNhac;
    ImageButton caNhan;
    ArrayList<userOnline> al1;
    ListView list;
    private DatabaseReference mDatabase;
    ArrayList<userOnline> al;
    listAdapterTinNhan adapterTinNhan;
    goiTin goiTinCuoi;

    int y=-1;
 //   DataBaseSQLine dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        anhXa();
       // manHinh.setBackgroundResource( R.drawable.tin_nhan );
        list=(ListView) findViewById( R.id.list_tinnhan );
        al1=new ArrayList<>(  );
        goiTinCuoi=new goiTin("","","chưa có","","","" );

        al=new ArrayList<>(  );
        adapterTinNhan=new listAdapterTinNhan(NhanTinActivity.this,R.layout.item_listview_tinnhan,al);
        list.setAdapter( adapterTinNhan );
        al.clear();
        al1.clear();
        String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
        int v;
        mDatabase.child( "ban be"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final userOnline banBe= dataSnapshot.getValue(userOnline.class);
                al1.add( banBe );
                al.add( new userOnline( banBe.getTen().toString(), banBe.getEmail().toString(),banBe.getPassword().toString(),banBe.getSoDienThoai().toString(),banBe.getLinkHinh().toString() ));
              adapterTinNhan.notifyDataSetChanged();
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
        txtTinNhan.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals( "" )){
                   al.clear();
                   for (userOnline x:al1){
                       al.add( x );
                   }
                    adapterTinNhan.notifyDataSetChanged();
                }{
                    searchItem( charSequence.toString() );
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent( NhanTinActivity.this,trangGuiTin.class );
                intent.putExtra( "du lieu",al.get( i ).getEmail().toString().trim() );
                startActivity( intent );
            }
        } );
        tinNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
        banBe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( NhanTinActivity.this,banBeActivity.class );
                startActivity( intent );
            }
        } );
        bangTin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( NhanTinActivity.this, BangTinActivity.class );
                startActivity( intent );
            }
        } );
        amNhac.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( NhanTinActivity.this, DanhSachBaiHat.class );
                startActivity( intent );
            }
        } );
        caNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( NhanTinActivity.this, CaNhanActivity.class );
                startActivity( intent );
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tinnhan,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.taoNhom:
                 intent=new Intent( NhanTinActivity.this,DanhSachNhomActivity.class);
                startActivity( intent );

                break;
            case R.id.ChiaSeViTri:
                 intent=new Intent( NhanTinActivity.this,MainActivity_vi_tri.class);
                startActivity( intent );
                break;
        }
        return super.onOptionsItemSelected( item );
    }
void anhXa(){
        manHinh=(LinearLayout) findViewById( R.id.manHinhTinNhan);
        txtTinNhan=(EditText) findViewById( R.id.txtTinNhan );
    tinNhan=(ImageButton) findViewById( R.id.btnTinNhan );
    banBe=(ImageButton) findViewById( R.id.btnBanBe );
    bangTin=(ImageButton) findViewById( R.id.btnBangTin );
    amNhac=(ImageButton) findViewById( R.id.btnAmNhac );
    caNhan=(ImageButton) findViewById( R.id.btnCaNhan );
}
public void searchItem(String chuoi){
    for (int i=0;i<al.size();i++){
        if (al.get( i ).getEmail().toString().contains( chuoi )!=true){
            al.remove( i );
        }
    }
    adapterTinNhan.notifyDataSetChanged();
}
}
