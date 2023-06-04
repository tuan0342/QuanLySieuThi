package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.NhapKho;
import model.ThongTinSP;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NhapKhoDAO {
    // lấy tất cả bản ghi trong bảng Sản phẩm
    public static ObservableList<NhapKho> getAlLRecords() {
        String sql = "SELECT MaLo, sp.TenSanPham, dl.TenDaiLy, nv.TenNhanVien, SoLuong, nh.GiaNhap, HanSuDung\n" +
                "FROM NhapHang nh \n" +
                "LEFT JOIN SanPham sp ON nh.MaSanPham = sp.MaSanPham\n" +
                "LEFT JOIN DaiLy dl ON nh.MaDaiLy = dl.MaDaiLy\n" +
                "LEFT JOIN NhanVien nv ON nh.MaNguoiNhap = nv.MaNhanVien";
        ObservableList<NhapKho> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                NhapKho nhapKho = new NhapKho(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
                list.add(nhapKho);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Nhập kho");
        }
        return list;
    }

    // lấy tất cả bản ghi trong bảng Sản phẩm
    public static ObservableList<NhapKho> getAlLRecordsIncludeMaSP() {
        String sql = "SELECT MaLo, sp.MaSanPham, sp.TenSanPham, dl.TenDaiLy, nv.TenNhanVien, SoLuong, nh.GiaNhap, HanSuDung\n" +
                "FROM NhapHang nh\n" +
                "LEFT JOIN SanPham sp ON nh.MaSanPham = sp.MaSanPham\n" +
                "LEFT JOIN DaiLy dl ON nh.MaDaiLy = dl.MaDaiLy\n" +
                "LEFT JOIN NhanVien nv ON nh.MaNguoiNhap = nv.MaNhanVien";
        ObservableList<NhapKho> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                NhapKho nhapKho = new NhapKho(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8));
                list.add(nhapKho);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Nhập kho");
        }
        return list;
    }

    // insert vào bảng Nhập kho và bảng Sản phẩm
    public static void insertSanPham(String maLo, String maSp, String maDaiLy, String maNguoiNhapHang, String soLuong,
                                     String giaNhap, String hanSuDung) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO NhapHang\n" +
                "VALUES ('" + maLo + "', '" + maSp + "', '" + maDaiLy + "', '" + maNguoiNhapHang + "', " + soLuong + ", " + giaNhap + ", '" + hanSuDung + "'); \n" +
                "\n" +
                "DECLARE @soLuongThem int = (select SoLuongDaNhap from SanPham where MaSanPham = '" + maSp + "');\n" +
                "DECLARE @tongSoLuong int = (select TongSoLuong from SanPham where MaSanPham = '" + maSp + "');\n" +
                "UPDATE SanPham \n" +
                "SET SoLuongDaNhap = @soLuongThem + " + soLuong + ", TongSoLuong = @tongSoLuong + " + soLuong + "\n" +
                "WHERE MaSanPham = '" + maSp + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("insert vào báng Sản phẩm và Nhập kho thất bại!", "Failed insert", null);
        }
    }
}
