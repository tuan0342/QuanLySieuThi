package model;

public class XuatKho {
    private String maXuatKho;
    private String maSP;
    private String tenSP;
    private String soLuong;
    private String maNguoiXuat;
    private String nguoiXuat;
    private String maNguoiNhan;
    private String nguoiNhan;
    private String ngayXuat;
    private String maLo;

    public XuatKho() {
    }

    public XuatKho(String maXuatKho, String maSP, String tenSP, String soLuong, String maNguoiXuat, String nguoiXuat,
                   String maNguoiNhan, String nguoiNhan, String ngayXuat, String maLo) {
        this.maXuatKho = maXuatKho;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.maNguoiXuat = maNguoiXuat;
        this.nguoiXuat = nguoiXuat;
        this.maNguoiNhan = maNguoiNhan;
        this.nguoiNhan = nguoiNhan;
        this.ngayXuat = ngayXuat;
        this.maLo = maLo;
    }

    public String getMaXuatKho() {
        return maXuatKho;
    }

    public void setMaXuatKho(String maXuatKho) {
        this.maXuatKho = maXuatKho;
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

    public String getMaNguoiXuat() {
        return maNguoiXuat;
    }

    public void setMaNguoiXuat(String maNguoiXuat) {
        this.maNguoiXuat = maNguoiXuat;
    }

    public String getNguoiXuat() {
        return nguoiXuat;
    }

    public void setNguoiXuat(String nguoiXuat) {
        this.nguoiXuat = nguoiXuat;
    }

    public String getMaNguoiNhan() {
        return maNguoiNhan;
    }

    public void setMaNguoiNhan(String maNguoiNhan) {
        this.maNguoiNhan = maNguoiNhan;
    }

    public String getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getMaLo() {
        return maLo;
    }

    public void setMaLo(String maLo) {
        this.maLo = maLo;
    }
}
