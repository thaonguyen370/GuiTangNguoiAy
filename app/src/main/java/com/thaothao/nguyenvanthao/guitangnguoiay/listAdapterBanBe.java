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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NguyenVanThao on 3/14/2018.
 */

public class listAdapterBanBe extends BaseAdapter {
    private banBeActivity context;
    private int layout;
    private ArrayList<userOnline> al;

    public listAdapterBanBe(banBeActivity context, int layout, ArrayList<userOnline> al) {
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
        TextView email;
        ImageButton suKien;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       hower v;
        if(view==null){
            v=new hower();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
            v.anh=(CircleImageView) view.findViewById( R.id.itemAnhBanBe);
            v.email=(TextView) view.findViewById( R.id.itemListBanBeEmail);
            v.suKien=(ImageButton) view.findViewById( R.id.suKien );

            view.setTag( v );
        }else {
            v=(hower) view.getTag();
        }

        final userOnline user1=al.get( i );
        if (user1.getLinkHinh().length()>0){
            try {
                Picasso.get().load( user1.getLinkHinh().toString().trim()).into(v.anh);
            }catch (Exception a ){
                v.anh.setBackgroundResource( R.drawable.anhnam );
            }

        }
        v.email.setText("email:"+ user1.getEmail().toString() );
        v.suKien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogBanBe( user1 );
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
