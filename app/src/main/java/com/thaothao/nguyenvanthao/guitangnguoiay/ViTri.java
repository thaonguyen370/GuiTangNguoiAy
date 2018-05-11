package com.thaothao.nguyenvanthao.guitangnguoiay;

public class ViTri {
    private String email;
    private String toaDo;
    private int trinhTrang;

    public ViTri() {
    }

    public ViTri(String email, String toaDo, int trinhTrang) {
        this.email = email;
        this.toaDo = toaDo;
        this.trinhTrang = trinhTrang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToaDo() {
        return toaDo;
    }

    public void setToaDo(String toaDo) {
        this.toaDo = toaDo;
    }

    public int getTrinhTrang() {
        return trinhTrang;
    }

    public void setTrinhTrang(int trinhTrang) {
        this.trinhTrang = trinhTrang;
    }
}
