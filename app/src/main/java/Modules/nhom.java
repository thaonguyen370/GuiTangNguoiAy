package Modules;

import com.thaothao.nguyenvanthao.guitangnguoiay.userOnline;

import java.io.Serializable;
import java.util.ArrayList;

public class nhom implements Serializable{
    private String tenNhom;
    private userOnline nguoiTao;
    private ArrayList<userOnline> DanhSachNhom;

    public nhom() {
    }

    public nhom(String tenNhom, userOnline nguoiTao, ArrayList<userOnline> danhSachNhom) {
        this.tenNhom = tenNhom;
        this.nguoiTao = nguoiTao;
        DanhSachNhom = danhSachNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public userOnline getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(userOnline nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public ArrayList<userOnline> getDanhSachNhom() {
        return DanhSachNhom;
    }

    public void setDanhSachNhom(ArrayList<userOnline> danhSachNhom) {
        DanhSachNhom = danhSachNhom;
    }
}
