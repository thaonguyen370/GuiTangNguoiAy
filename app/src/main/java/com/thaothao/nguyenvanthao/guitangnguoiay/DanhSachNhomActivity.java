package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Adapter.listDanhSachNhom;
import Modules.nhom;

public class DanhSachNhomActivity extends AppCompatActivity {
    Toolbar toolbar;
    listDanhSachNhom adapter;
    private ArrayList<nhom> al_nhom;
    private DatabaseReference mDatabase;
    ListView listView;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_danh_sach_nhom );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView=(ListView) findViewById( R.id.list_addNhom ) ;
        back=(ImageButton) findViewById( R.id.back_trangDS ) ;
        al_nhom=new ArrayList<>(  );
        adapter=new listDanhSachNhom( DanhSachNhomActivity.this,R.layout.item_danh_sach_nhom,al_nhom );
        listView.setAdapter( adapter );
        String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
        mDatabase.child( "nhom"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                nhom nhom= dataSnapshot.getValue(nhom.class);
                al_nhom.add( nhom );
                adapter.notifyDataSetChanged();
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
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nhom nhom=al_nhom.get( position );
                Intent intent=new Intent( DanhSachNhomActivity.this,trangChatNhomActivity.class );
                intent.putExtra( "nhom",  nhom );
                startActivity( intent );
            }
        } );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( DanhSachNhomActivity.this,NhanTinActivity.class );
                startActivity( intent );
            }
        } );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.danh_sach_nhom,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.taoNhom:
                intent=new Intent( DanhSachNhomActivity.this,TaoNhomChatActivity.class);
                startActivity( intent );

                break;

        }
        return super.onOptionsItemSelected( item );
    }
}
