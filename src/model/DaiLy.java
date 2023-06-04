package model;

public class DaiLy {
    private String maDL;
    private String tenDL;
    private String soDt;
    private String diaChi;

    public DaiLy() {
    }

    public DaiLy(String maDL, String tenDL) {
        this.maDL = maDL;
        this.tenDL = tenDL;
    }

    public DaiLy(String maDL, String tenDL, String soDt, String diaChi) {
        this.maDL = maDL;
        this.tenDL = tenDL;
        this.soDt = soDt;
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "DaiLy{" +
                "maDL='" + maDL + '\'' +
                ", tenDL='" + tenDL + '\'' +
                ", soDt='" + soDt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }

    public String getMaDL() {
        return maDL;
    }

    public void setMaDL(String maDL) {
        this.maDL = maDL;
    }

    public String getTenDL() {
        return tenDL;
    }

    public void setTenDL(String tenDL) {
        this.tenDL = tenDL;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
