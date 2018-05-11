package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NguyenVanThao on 2/17/2018.
 */

public class listAdapterTinNhan extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<userOnline> al;

    public listAdapterTinNhan(Context context, int layout, ArrayList<userOnline> al) {
        this.context = context;
        this.layout = layout;
        this.al = al;

    }

    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
public class hower{
        private CircleImageView anhDaiDien;
        private TextView tenuser, tinNhanCuoi,thoiGian;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final hower c;
        if(view==null){
            c=new hower();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            c.anhDaiDien=(CircleImageView) view.findViewById( R.id.anhDaiDien_item_tinNhan );
            c.tenuser=(TextView) view.findViewById( R.id.item_ten_user_tin_nhan );
            c.thoiGian=(TextView) view.findViewById( R.id.item_thoi_gian_nhan );
            c.tinNhanCuoi=(TextView) view.findViewById( R.id.txt_item_tinnhancuoi );
            view.setTag( c );
        }else {
           c=(hower) view.getTag();
        }
        if (al.get( i ).getLinkHinh().length()>0){
            try {
                Picasso.get().load( al.get( i ).getLinkHinh().toString().trim()).into(c.anhDaiDien);
            }catch (Exception e){
                c.anhDaiDien.setBackgroundResource( R.drawable.anhnam );
            }
        }
        c.tenuser.setText( al.get( i ).getEmail() );
                final String [] k0=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
                 userOnline banBe=al.get( i );
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                 final String [] k1=banBe.getEmail().toString().trim().split( "@" );
                        mDatabase.child( "M"+k0[0]+k1[0] ).addChildEventListener( new ChildEventListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        goiTin goiTin1= dataSnapshot.getValue(goiTin.class);
                        //
                        c.thoiGian.setText( null);
                        if (goiTin1.getTenNguoiGui().toString().equals( DangKiDangNhapOnlineActivity.tk.toString() )){
                            c.tinNhanCuoi.setText(null);

                            if (goiTin1.getThongDiep().toString().length()>1){
                                c.tinNhanCuoi.setText( goiTin1.getThongDiep() );
                                c.tinNhanCuoi.setTextColor( R.color.xanh  );
                                c.thoiGian.setText( goiTin1.getThoiGian());
                            }
                            else if (goiTin1.getLinkHinhThongDiep().toString().length()>1){
                                c.tinNhanCuoi.setText( goiTin1.getLinkHinhThongDiep().toString() );
                                c.tinNhanCuoi.setTextColor( R.color.xanh  );

                                c.thoiGian.setText( goiTin1.getThoiGian());
                            }

                        }else {
                            c.tinNhanCuoi.setText(null);
                            if (goiTin1.getThongDiep().toString().length()>1){
                                c.tinNhanCuoi.setText( goiTin1.getThongDiep() );
                                c.tinNhanCuoi.setTextColor( R.color.den  );
                                c.thoiGian.setText( goiTin1.getThoiGian());
                            }

                            else  if (goiTin1.getLinkHinhThongDiep().toString().length()>1){
                                c.tinNhanCuoi.setText( goiTin1.getLinkHinhThongDiep().toString() );
                                c.tinNhanCuoi.setTextColor( R.color.den  );
                                c.thoiGian.setText( goiTin1.getThoiGian());
                            }
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

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
