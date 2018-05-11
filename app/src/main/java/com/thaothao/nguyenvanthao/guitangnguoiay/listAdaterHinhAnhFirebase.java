package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NguyenVanThao on 3/13/2018.
 */
public class listAdaterHinhAnhFirebase extends BaseAdapter {
    private QuanLyAnhFirebase context;
    private int layout;
    private ArrayList<hinhAnhFirebase> al;

    public listAdaterHinhAnhFirebase(QuanLyAnhFirebase context, int layout, ArrayList<hinhAnhFirebase> al) {
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



    class hower{
        ImageView hinhAnh;
        TextView ten, moTa;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        hower a;
        if(view==null){
            a=new hower();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
            a.hinhAnh=(ImageView) view.findViewById( R.id.item_hinh );
            a.ten=(TextView) view.findViewById( R.id.item_tenHinh );
            a.moTa=(TextView) view.findViewById( R.id.item_moTaHinh );
            view.setTag( a );
        }else {
            a=(hower) view.getTag();
        }
        hinhAnhFirebase hinhAnhFirebase =al.get( i );
        a.ten.setText( hinhAnhFirebase.getTen() );
//        a.moTa.setText( hinhAnhFirebase.getMoTa() );
//        //chuyen mang byte sang hinnh
//        byte[] hinh=hinhAnhFirebase.getHinh_anh();
//        Bitmap bitmap= BitmapFactory.decodeByteArray( hinh,0,hinh.length );
//        a.hinhAnhFirebase.setImageBitmap( bitmap );
        Picasso.get().load( hinhAnhFirebase.getLinkHinh().toString().trim()).into(a.hinhAnh);

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}