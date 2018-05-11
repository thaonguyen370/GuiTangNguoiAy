package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NguyenVanThao on 3/16/2018.
 */

public class adapterRecyclerviewBangTin extends RecyclerView.Adapter<adapterRecyclerviewBangTin.viewhower>{
    private ArrayList<goiTin> al;
    private Context context;

    public adapterRecyclerviewBangTin(ArrayList<goiTin> al, Context context) {
        this.al = al;
        this.context = context;
    }

    @Override
    public viewhower onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from( parent.getContext() );
        View item=layoutInflater.inflate( R.layout.item_bang_tin,parent,false );

        return new viewhower( item );
    }

    @Override
    public void onBindViewHolder(viewhower holder, int position) {
        holder.emai.setText( al.get( position ).getTenNguoiGui() );
        holder.thoiGian.setText(al.get( position ).getThoiGian()  );
        holder.thongDiep1.setText( "" );
        holder.thongDiep.setText( "" );

        if (al.get( position ).getLinkHinhThongDiep().length()>0&&al.get( position ).getThongDiep().length()>0){

            holder.thongDiep.setText( al.get( position ).getThongDiep()  );
            try {
                Picasso.get().load( al.get( position ).getLinkHinhThongDiep().toString().trim()).into(holder.hinh);
            }catch (Exception e){
                holder.hinh.setImageResource( R.drawable.manhinh1 );
            }
        }else if (al.get( position ).getLinkHinhThongDiep().length()<1&&al.get( position ).getThongDiep().length()>0){
            holder.hinh.setImageBitmap( null );
            holder.thongDiep.setText( null );
            holder.thongDiep1.setText(  al.get( position ).getThongDiep());
        }else if (al.get( position ).getLinkHinhThongDiep().length()>1){
            try {
                Picasso.get().load( al.get( position ).getLinkHinhThongDiep().toString().trim()).into(holder.hinh);
            }catch (Exception e){
                holder.hinh.setImageResource( R.drawable.manhinh1 );
            }

        }

        if (al.get( position ).getLinkHinhDaiDien().length()>0){
            try{
                Picasso.get().load( al.get( position ).getLinkHinhDaiDien().toString().trim()).into(holder.iconhinh);
            }catch (Exception e){
                holder.iconhinh.setImageResource( R.drawable.anhdaidienmacdinh );
            }

        }
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class viewhower extends RecyclerView.ViewHolder{
        CircleImageView iconhinh;
        ImageView hinh;
        TextView emai,thoiGian,thongDiep, thongDiep1;
        CheckBox like;
        ImageButton binhLuan,xemThem;

        public viewhower(View itemView) {
            super( itemView );
            iconhinh=(CircleImageView) itemView.findViewById( R.id.iconHinh );
            hinh=(ImageView) itemView.findViewById( R.id.itemHinhBangTin );
            emai=(TextView) itemView.findViewById( R.id.itemTenNguoiDungBangTin );
            thoiGian=(TextView) itemView.findViewById( R.id.itemThoiGianBangTin );
            thongDiep=(TextView) itemView.findViewById( R.id.itemThongDiepBanTin );
            thongDiep1=(TextView) itemView.findViewById( R.id.itemThongDiepBanTin1 );
            like=(CheckBox) itemView.findViewById( R.id.itemLikeBangTin );
            binhLuan=(ImageButton) itemView.findViewById( R.id.itemBinhLuanbangTin );
            xemThem=(ImageButton) itemView.findViewById( R.id.itemXemthembangTin);

        }
    }
}
