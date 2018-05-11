package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import Adapter.listAdapterTrangChatNhom;
import Modules.nhom;

public class trangChatNhomActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton back,cemara,forder,gui;
    EditText thongDiep;
    ListView list;
    ArrayList<goiTin> al;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mDatabase;
    ImageView anh;
    int flagAnh=123;
    int flagThuMuc=234;

    String emailBan="";
    ArrayList<String> taoChuoi;
    listAdapterTrangChatNhom adapterTrangChatNhom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_trang_chat_nhom );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final StorageReference storageRef = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        anhXa();
        Intent intent=getIntent();
        nhom nhom= (nhom) intent.getSerializableExtra( "nhom" );
        taoChuoi=new ArrayList<>(  );

       for (int i=0;i<nhom.getDanhSachNhom().size();i++){
           String[] k=nhom.getDanhSachNhom().get( i ).getEmail().toString().trim().split( "@" );
           taoChuoi.add( k[0] );
       }
       Collections.sort( taoChuoi );
       for (int i=0;i<taoChuoi.size();i++){
           emailBan+=taoChuoi.get( i ).toString()+" ";
       }

       al=new ArrayList<>(  );
        adapterTrangChatNhom=new listAdapterTrangChatNhom( trangChatNhomActivity.this,R.layout.item_list_trang_chat_nhom,al );
        list.setAdapter( adapterTrangChatNhom );
        al.clear();
        mDatabase.child( "ChatNhom:"+emailBan.trim() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                goiTin goiTin1= dataSnapshot.getValue(goiTin.class);
                al.add(new goiTin( goiTin1.getTenNguoiGui().toString(),goiTin1.getTenNguoiNhan().toString(),goiTin1.getThongDiep().toString(),goiTin1.getLinkHinhDaiDien().toString(),goiTin1.getLinkHinhThongDiep().toString(),goiTin1.getThoiGian().toString() ) );

                adapterTrangChatNhom.notifyDataSetChanged();

                //           Toast.makeText( MainActivity.this,goiTin1.getHoTen().toString(),Toast.LENGTH_SHORT ).show();
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
        gui.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tinNhan=thongDiep.getText().toString().trim();
                if (tinNhan.length()>0){
                    Calendar calendar=Calendar.getInstance();
                    goiTin goiTinGui=new goiTin( DangKiDangNhapOnlineActivity.tk.toString().trim(),emailBan.trim(),tinNhan,"","",String.valueOf( calendar.getTime() ) );
                    mDatabase.child( "ChatNhom:"+emailBan.trim() ).push().setValue( goiTinGui );

                    thongDiep.setText( "" );
                }
            }
        } );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( trangChatNhomActivity.this,DanhSachNhomActivity.class );
                startActivity( intent );
            }
        } );
        cemara.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialogDSHinh(  );
                Intent intent=new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult( intent,flagAnh );
            }
        } );
        forder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent,flagThuMuc );
            }
        } );
    }
    private void anhXa(){
        al=new ArrayList<>( );
        back=(ImageButton) findViewById( R.id.back_trangGuiTin );
        cemara=(ImageButton) findViewById( R.id.cemaraTrangGui );
        forder=(ImageButton) findViewById( R.id.forderTrangGui );
        gui=(ImageButton) findViewById( R.id.guiTrangGui );
        thongDiep=(EditText) findViewById( R.id.thongDiep );
        list=(ListView) findViewById( R.id.listChatNhom );
        anh=(ImageView) findViewById( R.id.kkk );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==flagAnh&&resultCode==RESULT_OK&&data!=null){
            Bitmap bitmap1=(Bitmap) data.getExtras().get( "data" );
            anh.setImageBitmap( bitmap1 );
            if (anh!=null){
                final StorageReference storageRef = storage.getReference();
                Calendar calendar=Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("hinh"+calendar.getTimeInMillis()+".png");
                anh.setDrawingCacheEnabled(true);
                anh.buildDrawingCache();
                Bitmap bitmap = anh.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data2 = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data2);//gui len mang byte
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //gui len that bai
                        Toast.makeText( trangChatNhomActivity.this,"UpLoad that bai",Toast.LENGTH_LONG ).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText( trangChatNhomActivity.this,"UpLoad thanh cong",Toast.LENGTH_LONG ).show();
                        Log.d( "link", String.valueOf( downloadUrl ) );
                        anh.setImageBitmap( null );
                        //
                        Calendar calendar=Calendar.getInstance();
                        goiTin goiTinGui=new goiTin( DangKiDangNhapOnlineActivity.tk.toString().trim(),emailBan.trim(),"","",String.valueOf( downloadUrl ) ,String.valueOf( calendar.getTime() ) );
                        mDatabase.child( "ChatNhom:"+emailBan.trim()).push().setValue( goiTinGui );
                    }
                });
            }

        }
        if(requestCode==flagThuMuc&&resultCode==RESULT_OK&&data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream( uri );
                Bitmap bitmap1= BitmapFactory.decodeStream( inputStream );
                anh.setImageBitmap( bitmap1 );

                if (anh!=null){
                    final StorageReference storageRef = storage.getReference();
                    Calendar calendar=Calendar.getInstance();
                    StorageReference mountainsRef = storageRef.child("hinh"+calendar.getTimeInMillis()+".png");
                    anh.setDrawingCacheEnabled(true);
                    anh.buildDrawingCache();
                    Bitmap bitmap = anh.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data2 = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data2);//gui len mang byte
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //gui len that bai
                            Toast.makeText( trangChatNhomActivity.this,"UpLoad that bai",Toast.LENGTH_LONG ).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText( trangChatNhomActivity.this,"UpLoad thanh cong",Toast.LENGTH_LONG ).show();
                            Log.d( "link", String.valueOf( downloadUrl ) );
                            anh.setImageBitmap( null );
                            //

                            Calendar calendar=Calendar.getInstance();
                            goiTin goiTinGui=new goiTin( DangKiDangNhapOnlineActivity.tk.toString().trim(),emailBan.trim(),"","",String.valueOf( downloadUrl ) ,String.valueOf( calendar.getTime() ) );
                            mDatabase.child( "ChatNhom:"+emailBan.trim() ).push().setValue( goiTinGui );


                        }
                    });
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult( requestCode, resultCode, data );
    }
}
