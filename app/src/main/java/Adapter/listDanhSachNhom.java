package Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thaothao.nguyenvanthao.guitangnguoiay.DanhSachNhomActivity;
import com.thaothao.nguyenvanthao.guitangnguoiay.R;
import com.thaothao.nguyenvanthao.guitangnguoiay.banBeActivity;
import com.thaothao.nguyenvanthao.guitangnguoiay.userOnline;

import java.util.ArrayList;

import Modules.nhom;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NguyenVanThao on 3/14/2018.
 */

public class listDanhSachNhom extends BaseAdapter {
    private DanhSachNhomActivity context;
    private int layout;
    private ArrayList<nhom> al;

    public listDanhSachNhom(DanhSachNhomActivity context, int layout, ArrayList<nhom> al) {
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

        TextView tenNhom, ThanhVien;

    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       hower v;
        if(view==null){
            v=new hower();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater để lấy context nào, để nhúng layout vao
            view = inflater.inflate( layout,null);
            v.tenNhom=(TextView) view.findViewById( R.id.txtTenNhom);
            v.ThanhVien=(TextView) view.findViewById( R.id.txtThanhVien);

            view.setTag( v );
        }else {
            v=(hower) view.getTag();
        }

       nhom nhom=al.get( i );


        v.tenNhom.setText(nhom.getTenNhom());
        String tenThanhVienNhom="";

        for (int k=0;k<nhom.getDanhSachNhom().size();k++){
            String [] f=nhom.getDanhSachNhom().get( k ).getEmail().split( "@" );
            tenThanhVienNhom+=f[0]+" ";
        }
        v.ThanhVien.setText( tenThanhVienNhom );



        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
