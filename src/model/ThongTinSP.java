package model;

public class ThongTinSP {
    private String maSP;
    private String tenSP;
    private String phanLoai;
    private String giaNhap;
    private String giaBan;
    private String daiLyCungCap;
    private String maDaiLy;
    private String slNhap;
    private String slBan; 
    private String tongSL;

    public ThongTinSP() {
    }

    public ThongTinSP(String maSP ,String tenSP, String daiLyCungCap, String giaNhap, String maDaiLy) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.daiLyCungCap = daiLyCungCap;
        this.maDaiLy = maDaiLy;
    }

    public ThongTinSP(String tenSP, String phanLoai, String giaNhap, String giaBan, String daiLyCungCap, String slNhap,
                      String slBan, String tongSL, String maSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.phanLoai = phanLoai;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.daiLyCungCap = daiLyCungCap;
        this.slNhap = slNhap;
        this.slBan = slBan;
        this.tongSL = tongSL;
    }

    public String getMaDaiLy() {
        return maDaiLy;
    }

    public void setMaDaiLy(String maDaiLy) {
        this.maDaiLy = maDaiLy;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }

    public String getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(String giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getDaiLyCungCap() {
        return daiLyCungCap;
    }

    public void setDaiLyCungCap(String daiLyCungCap) {
        this.daiLyCungCap = daiLyCungCap;
    }

    public String getSlNhap() {
        return slNhap;
    }

    public void setSlNhap(String slNhap) {
        this.slNhap = slNhap;
    }

    public String getSlBan() {
        return slBan;
    }

    public void setSlBan(String slBan) {
        this.slBan = slBan;
    }

    public String getTongSL() {
        return tongSL;
    }

    public void setTongSL(String tongSL) {
        this.tongSL = tongSL;
    }

    @Override
    public String toString() {
        return "ThongTinSP{" +
                "maSP='" + maSP + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", phanLoai='" + phanLoai + '\'' +
                ", giaNhap='" + giaNhap + '\'' +
                ", giaBan='" + giaBan + '\'' +
                ", daiLyCungCap='" + daiLyCungCap + '\'' +
                ", maDaiLy='" + maDaiLy + '\'' +
                ", slNhap='" + slNhap + '\'' +
                ", slBan='" + slBan + '\'' +
                ", tongSL='" + tongSL + '\'' +
                '}';
    }
}
