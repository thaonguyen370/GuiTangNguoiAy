package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NguyenVanThao on 2/21/2018.
 */

public class listAdaterHinhAnh extends BaseAdapter {
    private QuanLiHinhAnhActivity context;
    private int layout;
    private ArrayList<hinhAnh> al;

    public listAdaterHinhAnh(QuanLiHinhAnhActivity context, int layout, ArrayList<hinhAnh> al) {
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
            a.hinhAnh=(ImageView) view.findViewById( R.id.item_hinh );
            a.ten=(TextView) view.findViewById( R.id.item_tenHinh );
            a.moTa=(TextView) view.findViewById( R.id.item_moTaHinh );
            view.setTag( a );
        }else {
            a=(hower) view.getTag();
        }
        hinhAnh hinhAnh=al.get( i );
        a.ten.setText( hinhAnh.getTenAnh() );
        a.moTa.setText( hinhAnh.getMoTa() );
        //chuyen mang byte sang hinnh
        byte[] hinh=hinhAnh.getHinh_anh();
        Bitmap bitmap= BitmapFactory.decodeByteArray( hinh,0,hinh.length );
        a.hinhAnh.setImageBitmap( bitmap );

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
