package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import com.facebook.FacebookSdk;

public class QuanLyAnhFirebase extends AppCompatActivity {

    EditText tenAnh;
  //  EditText  moTa;
    private ImageView hinhAnhx;
    Button Facebook,Zalo,Intagram;
    CheckBox checkCamXuc,checkHinhAnh;

    ImageButton mayAnh, thuMuc, ok,xemDS;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mDatabase;
    ArrayList<hinhAnhFirebase> al;
    //Toolbar toolbar;
    int flagAnh=123;
    int flagThuMuc=234;
    ShareDialog shareDialog;
    Bitmap bitmapf;
    ShareLinkContent shareLinkContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.quan_ly_anh_firebase );
        al=new ArrayList<hinhAnhFirebase>(  );
        final StorageReference storageRef = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tenAnh=(EditText) findViewById( R.id.txtTenAnh_hinhAnh );
     //   moTa=(EditText) findViewById( R.id.txtMoTa_hinhAnh );
        hinhAnhx=(ImageView) findViewById( R.id.hinhAnh_hinhAnh );
        mayAnh=(ImageButton) findViewById( R.id.mayAnh_hinhAnh );
        thuMuc=(ImageButton) findViewById( R.id.thuwMuc_hinhAnh );
        ok=(ImageButton) findViewById( R.id.ok );
        xemDS=(ImageButton) findViewById( R.id.xemDanhSach );
        checkCamXuc=(CheckBox) findViewById( R.id.checkCamXuc );
        checkHinhAnh=(CheckBox) findViewById( R.id.checkAnh );
        Facebook=(Button) findViewById( R.id.checkFacebook );
        Zalo=(Button) findViewById( R.id.checkZalo );
        Intagram=(Button) findViewById( R.id.checkIntagram );
        shareDialog=new ShareDialog( this );
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
        Facebook.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkHinhAnh.isChecked()){
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(bitmapf)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show( content );
                }
            }
        } );

        ok.setOnClickListener( new View.OnClickListener() {
            //chuyen mang

            @Override
            public void onClick(View view) {
                if (checkCamXuc.isChecked()&&checkHinhAnh.isChecked()){

                    //luu hinh voi ten gi
                    Calendar calendar=Calendar.getInstance();
                    StorageReference mountainsRef = storageRef.child("hinh"+calendar.getTimeInMillis()+".png");
                    hinhAnhx.setDrawingCacheEnabled(true);
                    hinhAnhx.buildDrawingCache();
                    Bitmap bitmap = hinhAnhx.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);//gui len mang byte
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //gui len that bai
                            Toast.makeText( QuanLyAnhFirebase.this,"UpLoad that bai",Toast.LENGTH_SHORT ).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText( QuanLyAnhFirebase.this,"UpLoad thanh cong",Toast.LENGTH_SHORT ).show();
                            Log.d( "link", String.valueOf( downloadUrl ) );
                            final hinhAnhFirebase hinhAnhFirebase1 =new hinhAnhFirebase( tenAnh.getText().toString().trim(),String.valueOf( downloadUrl ) );
                            mDatabase.child( "hinh anh" ).push().setValue( hinhAnhFirebase1, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError==null){
                                        //  al.add( hinhAnhFirebase1 );
                                        hinhAnhx.setImageResource( R.drawable.no_img );
                                        tenAnh.setText( "" );

                                        Toast.makeText( QuanLyAnhFirebase.this,"Luu hinh thanh cong",Toast.LENGTH_SHORT ).show();
                                    }else{
                                        Toast.makeText( QuanLyAnhFirebase.this,"luu hinh that bai",Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );
                            Intent intent=new Intent(  );
                            intent.putExtra( "bai dang",  hinhAnhFirebase1 );
                            setResult( RESULT_OK,intent );
                            finish();
                        }
                    });
                }else if (checkCamXuc.isChecked()&&checkHinhAnh.isChecked()!=true){
                    //luu hinh voi ten gi
                    final hinhAnhFirebase hinhAnhFirebase1 =new hinhAnhFirebase( tenAnh.getText().toString().trim(),"" );
                    Intent intent=new Intent(  );
                    intent.putExtra( "bai dang",  hinhAnhFirebase1 );
                    setResult( RESULT_OK,intent );
                    finish();
                }else if (checkCamXuc.isChecked()!=true&&checkHinhAnh.isChecked()){
                    Calendar calendar=Calendar.getInstance();
                    StorageReference mountainsRef = storageRef.child("hinh"+calendar.getTimeInMillis()+".png");
                    hinhAnhx.setDrawingCacheEnabled(true);
                    hinhAnhx.buildDrawingCache();
                    Bitmap bitmap = hinhAnhx.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);//gui len mang byte
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //gui len that bai
                            Toast.makeText( QuanLyAnhFirebase.this,"UpLoad that bai",Toast.LENGTH_SHORT ).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText( QuanLyAnhFirebase.this,"UpLoad thanh cong",Toast.LENGTH_SHORT ).show();
                            Log.d( "link", String.valueOf( downloadUrl ) );
                            final hinhAnhFirebase hinhAnhFirebase1 =new hinhAnhFirebase( "",String.valueOf( downloadUrl ) );
                            mDatabase.child( "hinh anh" ).push().setValue( hinhAnhFirebase1, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError==null){
                                        //  al.add( hinhAnhFirebase1 );
                                        hinhAnhx.setImageResource( R.drawable.no_img );
                                        tenAnh.setText( "" );

                                        Toast.makeText( QuanLyAnhFirebase.this,"Luu hinh thanh cong",Toast.LENGTH_SHORT ).show();
                                    }else{
                                        Toast.makeText( QuanLyAnhFirebase.this,"luu hinh that bai",Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );
                            Intent intent=new Intent(  );
                            intent.putExtra( "bai dang",  hinhAnhFirebase1 );
                            setResult( RESULT_OK,intent );
                            finish();
                        }
                    });
                }else {
                    Toast.makeText( QuanLyAnhFirebase.this,"yêu cầu check cảm xúc hoặc hình ảnh",Toast.LENGTH_SHORT ).show();
                }

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
            bitmapf=(Bitmap) data.getExtras().get( "data" );
            hinhAnhx.setImageBitmap( bitmapf );
        }
        if(requestCode==flagThuMuc&&resultCode==RESULT_OK&&data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream( uri );
                 bitmapf= BitmapFactory.decodeStream( inputStream );
                hinhAnhx.setImageBitmap( bitmapf );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult( requestCode, resultCode, data );
    }
    public void loadHinh(){
        al.clear();
        mDatabase.child( "hinh anh" ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                hinhAnhFirebase hinhAnhFirebase1 =dataSnapshot.getValue(hinhAnhFirebase.class);
                al.add( new hinhAnhFirebase( hinhAnhFirebase1.getTen().toString(), hinhAnhFirebase1.getLinkHinh().toString() ) );
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
    };
    public void dialogDSHinh(){

        final Dialog dialog=new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_quan_li_hinh_anh_firebase );
        dialog.show();

        ListView listDSHinh=(ListView) dialog.findViewById( R.id.list_thongTinHHinh_dialog );

        loadHinh();
        listAdaterHinhAnhFirebase adaterHinhAnh=new listAdaterHinhAnhFirebase(  QuanLyAnhFirebase.this,R.layout.item_list_quan_li_hinh_firebase,al  );
        listDSHinh.setAdapter(adaterHinhAnh);
    }

    public void toast(String thongDiep){
        Toast toast=Toast.makeText(QuanLyAnhFirebase.this,thongDiep,Toast.LENGTH_SHORT);
        toast.show();
    }
}