package model;

public class HoaDon {
    private String maHoaDon;  // tạo tự động khi hoàn thành 1 hóa đơn
    private String dsMaItem;
    private String ngayThanhToan;
    private String maNV;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String dsMaItem, String ngayThanhToan, String maNV) {
        this.maHoaDon = maHoaDon;
        this.dsMaItem = dsMaItem;
        this.ngayThanhToan = ngayThanhToan;
        this.maNV = maNV;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getDsMaItem() {
        return dsMaItem;
    }

    public void setDsMaItem(String dsMaItem) {
        this.dsMaItem = dsMaItem;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
