package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import Modules.nhom;

public class DangKiDangNhapOnlineActivity extends AppCompatActivity {
    LinearLayout manHinh;
    EditText email, password;
    Button dangNhap, dangKy;
    CheckBox nhoThongTin;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    public static String tk = "";
    public static String linkHinhDaiDien =  "https://firebasestorage.googleapis.com/v0/b/umet-704d4.appspot.com/o/hinh1525246845308.png?alt=media&token=0e0720b3-449e-4495-b334-8e0077c7771a".toString();
    SharedPreferences sharedPreferences;
    int flagAnh=123;
    int flagThuMuc=234;
    int flagAnh1=345;
    int flagThuMuc1=456;
    Bitmap bitmapf;
    ImageView hinhAnhx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dang_ki_dang_nhap_online );
        final StorageReference storageRef = storage.getReference();
        anhXa();
        sharedPreferences = getSharedPreferences( "datalogin", MODE_PRIVATE );
        //lay ra gia tri
        email.setText( sharedPreferences.getString( "email", "" ) );
        password.setText( sharedPreferences.getString( "password", "" ) );
        nhoThongTin.setChecked( sharedPreferences.getBoolean( "checked", false ) );
        //  manHinh.setBackgroundResource( R.drawable.dang_nhap_online );
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dangNhap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap();

            }
        } );
        dangKy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dangKy();
                dialog_dangKi();
            }
        } );
    }

    private void anhXa() {
        manHinh = (LinearLayout) findViewById( R.id.manHinhDangNhapOnline );
        email = (EditText) findViewById( R.id.emailOnline );
        password = (EditText) findViewById( R.id.passwordOnline );
        dangNhap = (Button) findViewById( R.id.dangNhapOnline );
        dangKy = (Button) findViewById( R.id.dangKiOnline );
        nhoThongTin = (CheckBox) findViewById( R.id.checkBoxNhothongTin );

    }

    private void dangKy(final String ho, final String ten, final String email1, final String password1, final String soDT) {
//        final String email1 = email.getText().toString();
//        final String password1 = password.getText().toString();
        mAuth.createUserWithEmailAndPassword( email1, password1 )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            tk = email1;
                            Toast.makeText( DangKiDangNhapOnlineActivity.this, "thanh cong", Toast.LENGTH_LONG ).show();
                            mDatabase.child( "user" ).push().setValue( new userOnline( ho+ten, email1, password1, soDT, linkHinhDaiDien ) );
                            Intent intent = new Intent( DangKiDangNhapOnlineActivity.this, NhanTinActivity.class );
                            startActivity( intent );
                            if (nhoThongTin.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString( "email", email1 );
                                editor.putString( "password", password1 );
                                editor.putBoolean( "checked", true );
                                editor.commit();
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove( "email" );
                                editor.remove( "password" );
                                editor.remove( "checked" );
                                editor.commit();
                            }
                        } else {
                            Toast.makeText( DangKiDangNhapOnlineActivity.this, "loi", Toast.LENGTH_LONG ).show();
                        }

                        // ...
                    }
                } );
    }

    private void dangNhap() {
        final String email2 = email.getText().toString();
        final String password1 = password.getText().toString();
        mAuth.signInWithEmailAndPassword( email2, password1 )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            tk = email2;
                            Toast.makeText( DangKiDangNhapOnlineActivity.this, "thanh cong", Toast.LENGTH_LONG ).show();
                            Intent intent = new Intent( DangKiDangNhapOnlineActivity.this, NhanTinActivity.class );
                            startActivity( intent );
                            if (nhoThongTin.isChecked()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString( "email", email2 );
                                editor.putString( "password", password1 );
                                editor.putBoolean( "checked", true );
                                editor.commit();
                            } else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove( "email" );
                                editor.remove( "password" );
                                editor.remove( "checked" );
                                editor.commit();
                            }
                        } else {
                            Toast.makeText( DangKiDangNhapOnlineActivity.this, "email hoac mật khẩu không đúng hoặc do đường truyền", Toast.LENGTH_LONG ).show();
                        }

                        // ...
                    }
                } );
    }

    private void dialog_dangKi() {
        final Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dang_ki_online );

        dialog.setCanceledOnTouchOutside( false );
         hinhAnhx = (ImageView) dialog.findViewById( R.id.diaLogDaiDien );
        final EditText ho = (EditText) dialog.findViewById( R.id.dialogHo );
        final EditText ten = (EditText) dialog.findViewById( R.id.dialogten );
        final EditText email = (EditText) dialog.findViewById( R.id.dialogGmail );
        final EditText matKhau = (EditText) dialog.findViewById( R.id.dialogMatKhau );
        final EditText nhapLaimatKhau = (EditText) dialog.findViewById( R.id.dialogNhapLaiMatKhau );
        final EditText soDT = (EditText) dialog.findViewById( R.id.dialogSoDT );
        final Button dangKi = (Button) dialog.findViewById( R.id.dialogDangKi );
        dangKi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (KiemTra( ho.getText().toString(), ten.getText().toString(), email.getText().toString(), matKhau.getText().toString(), nhapLaimatKhau.getText().toString(), soDT.getText().toString() )==true) {
                    //luu hinh voi ten gi
                    final StorageReference storageRef = storage.getReference();
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
                            Toast.makeText( DangKiDangNhapOnlineActivity.this,"UpLoad that bai",Toast.LENGTH_SHORT ).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText( DangKiDangNhapOnlineActivity.this,"UpLoad thanh cong",Toast.LENGTH_SHORT ).show();
                            linkHinhDaiDien=String.valueOf( downloadUrl );
                            Log.d( "link", String.valueOf( downloadUrl ) );
                            final hinhAnhFirebase hinhAnhFirebase1 =new hinhAnhFirebase( "thao",String.valueOf( downloadUrl ) );
                            mDatabase.child( "hinh anh" ).push().setValue( hinhAnhFirebase1, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError==null){
                                        //  al.add( hinhAnhFirebase1 );
                                        hinhAnhx.setImageResource( R.drawable.no_img );

                                        Toast.makeText( DangKiDangNhapOnlineActivity.this,"Luu hinh thanh cong",Toast.LENGTH_SHORT ).show();
                                        dangKy( ho.getText().toString(),ten.getText().toString(),email.getText().toString().trim(),matKhau.getText().toString().trim(),soDT.getText().toString() );

                                    }else{
                                        Toast.makeText( DangKiDangNhapOnlineActivity.this,"luu hinh that bai",Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );

                        }
                    });


                }
            }
        } );
        hinhAnhx.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diglogAnhDaiDien();
            }
        } );
        dialog.show();
    }
//
    private boolean KiemTra(String ho, String ten, String email, String pass, String laiPass, String soDT) {
        if (ho.length() <= 0 || ten.length() <= 0 || pass.length() <= 0 || laiPass.length() <= 0 || soDT.length() <= 0) {
            Toast.makeText( this, "không được để trống!", Toast.LENGTH_SHORT ).show();
            return false;
        }


        if (pass.equals( laiPass ) != true) {
            Toast.makeText( this, "mật khẩu không trùng nhau!", Toast.LENGTH_SHORT ).show();
            return false;

        }
        return true;


    }

    public void diglogAnhDaiDien() {
        Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_thay_doi_anh_dai_dien );
        ImageButton folder = (ImageButton) dialog.findViewById( R.id.folderDialog );
        ImageButton chupAnh = (ImageButton) dialog.findViewById( R.id.anhDialog );
        ImageButton coSoDuLieu = (ImageButton) dialog.findViewById( R.id.coSoDuLieuDialog );
        folder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent, flagThuMuc );
            }
        } );
        chupAnh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent, flagAnh );
            }
        } );
        dialog.show();

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
}