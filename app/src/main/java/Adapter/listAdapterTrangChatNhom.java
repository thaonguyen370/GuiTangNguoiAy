package Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thaothao.nguyenvanthao.guitangnguoiay.DangKiDangNhapOnlineActivity;
import com.thaothao.nguyenvanthao.guitangnguoiay.R;
import com.thaothao.nguyenvanthao.guitangnguoiay.goiTin;
import com.thaothao.nguyenvanthao.guitangnguoiay.trangChatNhomActivity;
import com.thaothao.nguyenvanthao.guitangnguoiay.trangGuiTin;

import java.util.ArrayList;

/**
 * Created by NguyenVanThao on 3/15/2018.
 */


public class listAdapterTrangChatNhom extends BaseAdapter {
    private trangChatNhomActivity context;
    private int layout;
    private ArrayList<goiTin> al;

    public listAdapterTrangChatNhom(trangChatNhomActivity context, int layout, ArrayList<goiTin> al) {
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
        ImageView anhMinh,anhBan,hinhDaiDienMinh,hinhDaiDienBan;
        TextView thongDiepMinh,thongDiepBan, tenDich;

    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        hower v;
        if(view==null){
            v=new hower();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
            v.anhMinh=(ImageView) view.findViewById( R.id.itemAnhMinh_trangguiTin);
            v.anhBan=(ImageView) view.findViewById( R.id.itemAnhBan_trangGuiTin);
            v.thongDiepMinh=(TextView) view.findViewById( R.id.item_txtMinh_trangGuiTin);
            v.thongDiepBan=(TextView) view.findViewById( R.id.item_txtBan_trangGuiTin);
            v.hinhDaiDienMinh=(ImageView) view.findViewById( R.id.itemHinhDaiDienMinh );
            v.hinhDaiDienBan=(ImageView) view.findViewById( R.id.itemHinhDaiDienBan );
            v.tenDich=(TextView) view.findViewById( R.id.tenDich );

            view.setTag( v );
        }else {
            v=(hower) view.getTag();
        }

        final goiTin goiTin1=al.get( i );
        v.tenDich.setText( "" );
        if (goiTin1.getTenNguoiGui().toString().equals( DangKiDangNhapOnlineActivity.tk.toString() )){
            v.anhBan.setImageBitmap( null );
            v.hinhDaiDienBan.setImageBitmap( null );
            v.thongDiepBan.setText( null );
            if (goiTin1.getThongDiep().toString().length()>1){
                v.anhMinh.setImageBitmap( null );
                v.thongDiepMinh.setText( goiTin1.getThongDiep().toString() );
            }
             else if (goiTin1.getLinkHinhThongDiep().toString().length()>1){
                v.thongDiepMinh.setText( null );
                Picasso.get().load(goiTin1.getLinkHinhThongDiep().toString()).into(v.anhMinh);
            }
            v.hinhDaiDienMinh.setImageResource( R.drawable.people );
        }else {
            v.anhMinh.setImageBitmap( null );
            v.hinhDaiDienMinh.setImageBitmap( null );
            v.thongDiepMinh.setText( null );
            if (goiTin1.getThongDiep().toString().length()>1){
                v.anhBan.setImageBitmap( null );
                String[] g=goiTin1.getTenNguoiGui().toString().trim().split( "@" );
                v.tenDich.setText( g[0] );
                v.thongDiepBan.setText( goiTin1.getThongDiep().toString() );
            }

            else  if (goiTin1.getLinkHinhThongDiep().toString().length()>1){
                v.thongDiepBan.setText( null );
                String[] g=goiTin1.getTenNguoiGui().toString().trim().split( "@" );
                v.tenDich.setText( g[0] );
                Picasso.get().load(goiTin1.getLinkHinhThongDiep().toString()).into(v.anhBan);
            }
            v.hinhDaiDienBan.setImageResource( R.drawable.people );
        }



        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}