package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by NguyenVanThao on 2/14/2018.
 */

public class listAdapterUser extends BaseAdapter {
    private quanLiDanhSachUserActivity context;
    private int layout;
    private ArrayList<user> al;

    public listAdapterUser(quanLiDanhSachUserActivity context, int layout, ArrayList<user> al) {
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
        ImageView anh;
        TextView user, password;
        ImageButton delete, sua;
        CheckBox khoa;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        hower v;
        if(view==null){
            v=new hower();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
           v.anh=(ImageView) view.findViewById( R.id.anhDaiDien_item);
            v.user=(TextView) view.findViewById( R.id.user_item);
            v.password=(TextView) view.findViewById( R.id.pass_item);
            v.delete=(ImageButton) view.findViewById( R.id.xoa_item );
            v.sua=(ImageButton) view.findViewById( R.id.sua_item );
            v.khoa=(CheckBox) view.findViewById( R.id.khoa_item);

            view.setTag( v );
        }else {
            v=(hower) view.getTag();
        }

        user user1=al.get( i );
       v.anh.setImageResource( R.drawable.anhnam );
       v.user.setText("user :"+ user1.getUser().toString() );
       v.password.setText("pass :"+ user1.getPassword().toString() );
       v.delete.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               context.xacNhanXoa1( i );

           }
       } );
       v.sua.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               context.dialog_capnhat( i );
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
