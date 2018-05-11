package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.ArrayList;

import Adapter.listAdapterTaonhom;
import Adapter.listDanhSachNhom;
import Modules.nhom;

public class TaoNhomChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    Button taoNhom;
    listAdapterTaonhom adapter;
    private ArrayList<nhom> al_nhom;
    private ArrayList<userOnline> al_banBe;
    private DatabaseReference mDatabase;
    private String chuoi="Thanh vien: ";
    private ArrayList<userOnline> al_thanhVien;
    EditText editText;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tao_nhom_chat );
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView=(ListView) findViewById( R.id.list_addNhom ) ;
        al_nhom=new ArrayList<>(  );
        taoNhom=(Button) findViewById( R.id.btnthanhVien );
        back=(ImageButton) findViewById( R.id.back_trangTaoNhom );
        al_banBe=new ArrayList<>(  );
        al_thanhVien=new ArrayList<>(  );
        al_thanhVien.add( new userOnline( DangKiDangNhapOnlineActivity.tk.toString().trim(),DangKiDangNhapOnlineActivity.tk.toString().trim(),"","","" ) );
        adapter=new listAdapterTaonhom( TaoNhomChatActivity.this,R.layout.item_list_ban_be_tao_nhom,al_banBe );
        listView.setAdapter( adapter );
        String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
        mDatabase.child( "ban be"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userOnline banBe= dataSnapshot.getValue(userOnline.class);
                banBe.key=dataSnapshot.getKey();
                al_banBe.add( new userOnline( banBe.getTen().toString(), banBe.getEmail().toString(),banBe.getPassword().toString(),banBe.getSoDienThoai().toString(),banBe.getLinkHinh().toString() ));
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
        mDatabase.child( "nhom"+k[0].toString() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                nhom nhom= dataSnapshot.getValue(nhom.class);
                al_nhom.add( nhom );
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
        taoNhom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (al_thanhVien.size()>1){
                    int dem=0;
                    if (al_nhom.size()>0){
                        for (int i=0;i<al_nhom.size();i++){
                            if (al_nhom.get( i ).getDanhSachNhom().size()==al_thanhVien.size()){
                                dem=0;
                                for (int ff=0;ff<al_thanhVien.size();ff++){
                                    for (int xx=0;xx<al_thanhVien.size();xx++){
                                        if (al_thanhVien.get( ff ).getEmail().toString().trim().equals( al_nhom.get( i ).getDanhSachNhom().get( xx ).getEmail().toString().trim() )){
                                            dem++;
                                        }
                                    }
                                    if (dem==al_thanhVien.size()){
                                        break;
                                    }
                                }
                            }
                            if (dem==al_thanhVien.size()){
                                break;
                            }
                        }
                    }
                    if (dem==al_thanhVien.size()){
                        Toast.makeText( TaoNhomChatActivity.this,"Nhóm này đã tồn tại",Toast.LENGTH_LONG ).show();
                    }else {
                        openDialog();


                    }
                }
            }
        } );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( TaoNhomChatActivity.this,DanhSachNhomActivity.class );
                startActivity( intent );
            }
        } );



    }
    public void checkBox(int i, Boolean b){
        chuoi="thanh vien: ";
                userOnline userOnline=al_banBe.get( i );
                if (b){
                    al_thanhVien.add( userOnline );
                    for (int x=0;x<al_thanhVien.size();x++){
                        String [] k=al_thanhVien.get( x ).getEmail().trim().split( "@" );
                        chuoi +=k[0]+", ";
                    }
                }else {
                    al_thanhVien.remove( userOnline );
                    for (int x=0;x<al_thanhVien.size();x++){
                        String [] k=al_thanhVien.get( x ).getEmail().trim().split( "@" );
                        chuoi +=k[0]+", ";
                    }
                }

                taoNhom.setText( chuoi );
    }

    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(TaoNhomChatActivity.this);
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText)subView.findViewById(R.id.dialogEditText);
        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        subImageView.setImageDrawable(drawable);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn tạo nhóm này không?");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (subEditText.getText().toString().trim().length()>0){
                    Toast.makeText( TaoNhomChatActivity.this,"Tạo nhóm thanh công",Toast.LENGTH_LONG ).show();
                    nhom nhom=new nhom( subEditText.getText().toString(),new userOnline( DangKiDangNhapOnlineActivity.tk.toString().trim(),DangKiDangNhapOnlineActivity.tk.toString().trim(),"","","" ),al_thanhVien );
                    for (int j=0;j<al_thanhVien.size();j++){
                        String [] k=al_thanhVien.get( j ).getEmail().toString().trim().split( "@" );
                        mDatabase.child( "nhom"+k[0].toString() ).push().setValue( nhom );
                    }
                }else {
                    Toast.makeText( TaoNhomChatActivity.this,"vui lòng nhập tên để tạo nhóm",Toast.LENGTH_LONG ).show();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
