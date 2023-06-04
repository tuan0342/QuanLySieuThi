package model;

/*
       1 hóa đơn gồm nhiều Item đơn hàng
*/
public class ItemDonHang {
    private String maItem;  // được tạo tự động
    private String maSP;
    private String tenSP;
    private String soLuong;
    private String donGia;

    public ItemDonHang() {
    }

    public ItemDonHang(String maItem, String maSP, String tenSP, String soLuong, String donGia) {
        this.maItem = maItem;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaItem() {
        return maItem;
    }

    public void setMaItem(String maItem) {
        this.maItem = maItem;
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

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return "ItemDonHang{" +
                "maItem='" + maItem + '\'' +
                ", maSP='" + maSP + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", soLuong='" + soLuong + '\'' +
                ", donGia='" + donGia + '\'' +
                '}';
    }
}
