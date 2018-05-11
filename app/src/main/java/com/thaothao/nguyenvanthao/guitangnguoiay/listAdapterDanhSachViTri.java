
package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NguyenVanThao on 3/14/2018.
 */

public class listAdapterDanhSachViTri extends BaseAdapter {
    private DanhSachVitri context;
    private int layout;
    private ArrayList<ViTri> al;
    private DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference();
    public listAdapterDanhSachViTri(DanhSachVitri  context, int layout, ArrayList<ViTri> al) {
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
        CircleImageView anh;
        TextView email,txt;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final hower v;
        if(view==null){
            v=new hower();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
            v.anh=(CircleImageView) view.findViewById( R.id.itemAnhBanBe);
            v.email=(TextView) view.findViewById( R.id.itemListBanBeEmail1);
            v.txt=(TextView) view.findViewById( R.id.txtKhoangCach );

            view.setTag( v );
        }else {
            v=(hower) view.getTag();
        }
        final ViTri viTri=al.get( i );

        v.anh.setBackgroundResource( R.drawable.anhnam );
        v.email.setText("email:"+ viTri.getEmail().toString() );

        v.txt.setText( " toa do la: "+viTri.getToaDo());
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
