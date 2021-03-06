package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NguyenVanThao on 2/4/2018.
 */

public class listAdapterBaiHat extends BaseAdapter {
    private DanhSachBaiHat context;
    private int layout;
    private ArrayList<BaiHat> al;

    public listAdapterBaiHat(DanhSachBaiHat context, int layout, ArrayList<BaiHat> al) {
        this.context = context;
        this.layout = layout;
        this.al = al;
    }

    @Override
    public int getCount() {//tra ve so dong, vd tra ve 5 dong thi listView co 5 dong, còn muốn lấy hết thi return về kích thước cái mạng
        return al.size();
    }

    @Override
    public Object getItem(int i) { return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
        view = inflater.inflate(layout, null);
        //anh xa:
        TextView tenBaiHat = (TextView) view.findViewById(R.id.tenBaiHat);
        TextView caSi = (TextView) view.findViewById(R.id.caSi);
        ImageView anh = (ImageView) view.findViewById(R.id.anh);

        BaiHat baiHat = al.get(i);
        tenBaiHat.setText("Ten bai: "+baiHat.getTenBai());
        caSi.setText("Ca si: "+baiHat.getCaSi());
        anh.setImageResource(baiHat.getAnh());
        Animation animation= AnimationUtils.loadAnimation( context,R.anim.dia_xoay_am_nhac );
        anh.startAnimation( animation );



        return view;
    }

}