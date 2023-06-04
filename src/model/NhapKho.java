package model;

import org.w3c.dom.ls.LSOutput;

public class NhapKho {
    private String maLo;
    private String maSP;
    private String tenSP;
    private String tenDaiLy;
    private String tenNguoiNhap;
    private String soLuong;
    private String giaNhap;
    private String hanSuDung;

    public NhapKho() {
    }

    public NhapKho(String maLo, String tenSP, String tenDaiLy, String tenNguoiNhap, String soLuong, String giaNhap,
                   String hanSuDung) {
        this.maLo = maLo;
        this.tenSP = tenSP;
        this.tenDaiLy = tenDaiLy;
        this.tenNguoiNhap = tenNguoiNhap;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.hanSuDung = hanSuDung;
    }

    public NhapKho(String maLo, String maSP, String tenSP, String tenDaiLy, String tenNguoiNhap, String soLuong,
                   String giaNhap, String hanSuDung) {
        this.maLo = maLo;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenDaiLy = tenDaiLy;
        this.tenNguoiNhap = tenNguoiNhap;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.hanSuDung = hanSuDung;
    }

    @Override
    public String toString() {
        return "NhapKho{" +
                "maLo='" + maLo + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", tenDaiLy='" + tenDaiLy + '\'' +
                ", tenNguoiNhap='" + tenNguoiNhap + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", giaNhap='" + giaNhap + '\'' +
                ", hanSuDung='" + hanSuDung + '\'' +
                '}';
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaLo() {
        return maLo;
    }

    public void setMaLo(String maLo) {
        this.maLo = maLo;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenDaiLy() {
        return tenDaiLy;
    }

    public void setTenDaiLy(String tenDaiLy) {
        this.tenDaiLy = tenDaiLy;
    }

    public String getTenNguoiNhap() {
        return tenNguoiNhap;
    }

    public void setTenNguoiNhap(String tenNguoiNhap) {
        this.tenNguoiNhap = tenNguoiNhap;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(String giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
}
