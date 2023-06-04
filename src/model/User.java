package model;

public class User {
    private String maNv;
    private String tenNv;
    private String matKhau;
    private String vaiTro;
    private String soDt;
    private String ngayVaoLam;
    private String luong;

    public User() {}

    public User(String maNv, String tenNv, String matKhau, String vaiTro, String soDt, String ngayVaoLam, String luong) {
        this.maNv = maNv;
        this.tenNv = tenNv;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.soDt = soDt;
        this.ngayVaoLam = ngayVaoLam;
        this.luong = luong;
    }

    public User(String maNv, String tenNv) {
        this.maNv = maNv;
        this.tenNv = tenNv;
    }

    public User(String maNv, String tenNv, String soDt, String ngayVaoLam, String luong) {
        this.maNv = maNv;
        this.tenNv = tenNv;
        this.soDt = soDt;
        this.ngayVaoLam = ngayVaoLam;
        this.luong = luong;
    }

    public User(String maNv, String matKhau, String vaiTro) {
        this.maNv = maNv;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getLuong() {
        return luong;
    }

    public void setLuong(String luong) {
        this.luong = luong;
    }

    @Override
    public String toString() {
        return "User{" +
                "maNv='" + maNv + '\'' +
                ", tenNv='" + tenNv + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                ", soDt='" + soDt + '\'' +
                ", ngayVaoLam='" + ngayVaoLam + '\'' +
                ", luong='" + luong + '\'' +
                '}';
    }
}
