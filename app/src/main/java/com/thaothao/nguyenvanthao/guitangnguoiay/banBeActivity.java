package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class banBeActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText txtTinNhan;
    ImageButton tinNhan;
    ImageButton banBe;
    ImageButton bangTin;
    ImageButton amNhac;
    ImageButton caNhan;
    ImageButton load;
    listAdapterBanBe adapterBanBe;
    listAdapterBanBeCoTheBiet adapterBanBeCoTheBiet;
    ListView listBanBe, listNhungNguoiCoTheBiet;
    private DatabaseReference mDatabase;
    ArrayList<userOnline> alBanBe,alNhungNguoiCoTheBiet;
    ArrayList<userOnline> al1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ban_be );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        anhXa();
        al1=new ArrayList<>(  );
        alBanBe=new ArrayList<>(  );
        alNhungNguoiCoTheBiet=new ArrayList<>(  );
        alBanBe.clear();
        al1.clear();
        adapterBanBe=new listAdapterBanBe( banBeActivity.this,R.layout.item_list_ban_be,alBanBe );
        listBanBe.setAdapter( adapterBanBe );
        adapterBanBeCoTheBiet=new listAdapterBanBeCoTheBiet( banBeActivity.this,R.layout.item_list_ban_be,alNhungNguoiCoTheBiet );
        listNhungNguoiCoTheBiet.setAdapter( adapterBanBeCoTheBiet );
        String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
        mDatabase.child( "ban be"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userOnline banBe= dataSnapshot.getValue(userOnline.class);
                banBe.key=dataSnapshot.getKey();

                al1.add( banBe );
               // alBanBe.add( new userOnline( banBe.getTen().toString(), banBe.getEmail().toString(),banBe.getPassword().toString(),banBe.getSoDienThoai().toString(),banBe.getLinkHinh().toString() ));
                alBanBe.add( banBe );
                adapterBanBe.notifyDataSetChanged();
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
//     adapterBanBe=new listAdapterBanBe( banBeActivity.this,R.layout.item_list_ban_be,alBanBe );
//     listBanBe.setAdapter( adapterBanBe );
        alNhungNguoiCoTheBiet.clear();
        mDatabase.child( "user" ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userOnline banBe= dataSnapshot.getValue(userOnline.class);
                if (banBe.getEmail().toString().trim().equals( DangKiDangNhapOnlineActivity.tk.toString().trim())!=true){
                    if(alBanBe.size()>0){
                        int flag=0;
                        for(int i=0;i<alBanBe.size();i++){

                            if(banBe.getEmail().toString().trim().equals( alBanBe.get( i ).getEmail().toString().trim() )){
                                flag=1;
                                break;
                            }

                        }
                        if (flag==0){
                            alNhungNguoiCoTheBiet.add( new userOnline( banBe.getTen().toString(), banBe.getEmail().toString(),banBe.getPassword().toString(),banBe.getSoDienThoai().toString(),banBe.getLinkHinh().toString() ));

                        }
                    }else {
                        alNhungNguoiCoTheBiet.add( new userOnline( banBe.getTen().toString(), banBe.getEmail().toString(),banBe.getPassword().toString(),banBe.getSoDienThoai().toString(),banBe.getLinkHinh().toString() ));

                    }

                }

                adapterBanBeCoTheBiet.notifyDataSetChanged();
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

        listBanBe.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText( banBeActivity.this,"haha",Toast.LENGTH_LONG ).show();
                //     dialogBanBe(alBanBe.get( i ));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        txtTinNhan.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals( "" )){
                    alBanBe.clear();
                    for (userOnline x:al1){
                        alBanBe.add( x );
                    }
                    adapterBanBe.notifyDataSetChanged();
                }{
                    searchItem( charSequence.toString() );
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        listNhungNguoiCoTheBiet.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  dialogNhungNguoiCoTheBiet(alNhungNguoiCoTheBiet.get( i ));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        tinNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this, NhanTinActivity.class );
                startActivity( intent );
            }
        } );
        banBe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
        bangTin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this, BangTinActivity.class );
                startActivity( intent );
            }
        } );
        amNhac.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this, DanhSachBaiHat.class );
                startActivity( intent );
            }
        } );
        caNhan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this, CaNhanActivity.class );
                startActivity( intent );
            }
        } );
        load.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this, banBeActivity.class );
                startActivity( intent );
            }
        } );
    }
    void anhXa(){
        txtTinNhan=(EditText) findViewById( R.id.txtTimKiem );
        tinNhan=(ImageButton) findViewById( R.id.btnTinNhan );
        banBe=(ImageButton) findViewById( R.id.btnBanBe );
        bangTin=(ImageButton) findViewById( R.id.btnBangTin );
        amNhac=(ImageButton) findViewById( R.id.btnAmNhac );
        caNhan=(ImageButton) findViewById( R.id.btnCaNhan );
        listBanBe=(ListView) findViewById( R.id.listBanBe );
        listNhungNguoiCoTheBiet=(ListView) findViewById( R.id.listNhungNguoiCoTheBiet );
        load=(ImageButton) findViewById( R.id.load );
    }
    public void dialogBanBe( final userOnline xx){
        final Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_ban_be );
        dialog.show();
        ImageView imageView=(ImageView) dialog.findViewById( R.id.imageView );
        Button huyKetBan=(Button) dialog.findViewById( R.id.btnHuyKetBan );
        Button chat=(Button) dialog.findViewById( R.id.btnchatBanBe);
        Button email=(Button) dialog.findViewById( R.id.email );
        Button goiDien=(Button) dialog.findViewById( R.id.goiDien_ban_be );
        Button SMS=(Button) dialog.findViewById( R.id.guiTinSMS_ban_be );
        Picasso.get().load( xx.getLinkHinh().toString().trim()).into(imageView);
        email.setText( xx.getEmail().trim() );
        goiDien.setText( xx.getSoDienThoai().trim() );
        SMS.setText( xx.getSoDienThoai().toString() );
        chat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this,trangGuiTin.class );
                intent.putExtra( "du lieu",xx.getEmail().toString().trim().trim() );
                startActivity( intent );


            }
        } );
        SMS.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(  );
                intent.setAction( Intent.ACTION_SENDTO );
                intent.putExtra( "sms_body"," " );
                intent.setData( Uri.parse( "sms:"+xx.getSoDienThoai().trim()));
                //intent.setData( Uri.parse( "sms:01688179019" ) );
                startActivity( intent );
            }
        } );
        goiDien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(  );
                intent.setAction( Intent.ACTION_CALL );
                intent.setData( Uri.parse( "tel:"+xx.getSoDienThoai().trim() ) );
             //   intent.setData( Uri.parse( "tel:01688179019" ) );
                startActivity( intent );
            }
        } );
        alBanBe.clear();
        al1.clear();
        final ArrayList<userOnlineKey> fuck=new ArrayList<>(  );
        huyKetBan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
                DatabaseReference mdata=FirebaseDatabase.getInstance().getReference("ban be" +k[0].toString());
                mdata.child( xx.key.toString() ).removeValue();

                dialog.cancel();
            }
        } );


    }
    public void dialogNhungNguoiCoTheBiet(final userOnline xx){
        final Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_nguoi_co_the_biet );
        dialog.show();
        ImageView imageView=(ImageView) dialog.findViewById( R.id.imageView );
        Button ketBan=(Button) dialog.findViewById( R.id.btnKetBan );
        Button chat=(Button) dialog.findViewById( R.id.btnchatBanBe);
        Button email=(Button) dialog.findViewById( R.id.email );
        Button goiDien=(Button) dialog.findViewById( R.id.goiDien_ban_be );
        Button SMS=(Button) dialog.findViewById( R.id.guiTinSMS_ban_be );
        Picasso.get().load( xx.getLinkHinh().toString().trim()).into(imageView);
        email.setText( xx.getEmail().trim() );
        goiDien.setText( xx.getSoDienThoai().trim() );
        SMS.setText( xx.getSoDienThoai().toString() );
        ketBan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
                mDatabase.child( "ban be" +k[0].toString()).push().setValue( xx );
                Toast.makeText( banBeActivity.this,"ket ban thanh cong",Toast.LENGTH_SHORT ).show();
                dialog.cancel();
            }
        } );
        chat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( banBeActivity.this,trangGuiTin.class );
                intent.putExtra( "du lieu",xx.getEmail().toString().trim().trim() );
                startActivity( intent );
            }
        } );


        SMS.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(  );
                intent.setAction( Intent.ACTION_SENDTO );
                intent.putExtra( "sms_body"," " );
                intent.setData( Uri.parse( "sms:"+xx.getSoDienThoai().trim()));
                startActivity( intent );
            }
        } );
        goiDien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(  );
                intent.setAction( Intent.ACTION_CALL );
                intent.setData( Uri.parse( "tel:"+xx.getSoDienThoai().trim() ) );
                startActivity( intent );
            }
        } );


    }
    public void searchItem(String chuoi){
        for (int i=0;i<alBanBe.size();i++){
            if (alBanBe.get( i ).getEmail().toString().contains( chuoi )!=true){
                alBanBe.remove( i );
            }
        }
        adapterBanBe.notifyDataSetChanged();
    }
//    public void xacNhanXoa1(final int vitri){
//        final AlertDialog.Builder alertDialog=new AlertDialog.Builder( this);
//
//        alertDialog.setMessage( "ban co muon xoa khong" );
//
//        alertDialog.setPositiveButton( "co", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
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

}
