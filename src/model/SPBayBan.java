package model;

public class SPBayBan {
    private String tenSP;
    private String maHang;
    private String giaBan;

    public SPBayBan() {
    }

    public SPBayBan(String tenSP, String giaBan) {
        this.tenSP = tenSP;
        this.giaBan = giaBan;
    }

    public SPBayBan(String tenSP, String maHang, String giaBan) {
        this.tenSP = tenSP;
        this.maHang = maHang;
        this.giaBan = giaBan;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }
}
