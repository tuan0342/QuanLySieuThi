package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.NhapKho;
import model.XuatKho;

import java.sql.ResultSet;
import java.sql.SQLException;

public class XuatKhoDAO {
    // lấy tất cả bản ghi trong bảng Xuất kho
    public static ObservableList<XuatKho> getAlLRecords() {
        String sql = "SELECT MaXuatKho, xk.MaSanPham, sp.TenSanPham, xk.SoLuong, xk.MaNguoiXuat, nv1.TenNhanVien, \n" +
                "xk.MaNguoiNhan, nv2.TenNhanVien, xk.NgayXuat, xk.MaLo\n" +
                "FROM XuatKho xk\n" +
                "LEFT JOIN SanPham sp ON sp.MaSanPham = xk.MaSanPham\n" +
                "LEFT JOIN NhanVien nv1 ON nv1.MaNhanVien = xk.MaNguoiXuat\n" +
                "LEFT JOIN NhanVien nv2 ON nv2.MaNhanVien = xk.MaNguoiNhan";
        ObservableList<XuatKho> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                XuatKho xuatKho = new XuatKho(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10));
                list.add(xuatKho);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Xuất kho");
        }
        return list;
    }

    // insert vào bảng Nhập kho và bảng Xuất kho và bảng Trưng bày
    public static void insertXuatKho(String maXK, String maSp, String soLuong, String maNguoiXuat, String maNguoiNhan,
                                     String ngayXuat, String maLo) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO XuatKho\n" +
                "VALUES ('" + maXK + "', '" + maSp + "'," + soLuong + ", '" + maNguoiXuat + "', '" + maNguoiNhan + "', '" + ngayXuat + "', '" + maLo + "'); \n" +
                "DECLARE @SoLuongBayBanTrenKe int = (SELECT SoLuongTrenKe FROM TrungBay \n" +
                "\t\t\t\t\t\t\t\t\tWHERE MaLo = '" + maLo + "' AND MaSanPham = '" + maSp + "')\n" +
                "IF @SoLuongBayBanTrenKe IS NULL\n" +
                "\tINSERT INTO TrungBay\n" +
                "\tVALUES ('" + maSp + "', '" + maLo + "', " + soLuong + ");\n" +
                "ELSE \n" +
                "\tUPDATE TrungBay \n" +
                "\tSET SoLuongTrenKe = @SoLuongBayBanTrenKe + " + soLuong + "\n" +
                "\tWHERE MaSanPham = '" + maSp + "' AND MaLo = '" + maLo + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
            CanhBao.showAlertInfo("Xuất hàng thành công", null, null);
        } catch (SQLException e) {
            CanhBao.showAlertError("insert vào bảng Xuất kho và Trưng bày thất bại!", "Failed insert", null);
        }
    }

    // update vào bảng Nhập kho và bảng Xuất kho và bảng Trưng bày
    public static void updateXuatKho(String maXK, String maSp, String soLuong, String maNguoiXuat, String maNguoiNhan,
                                     String ngayXuat, String maLo) throws SQLException, ClassNotFoundException {
        String sql = "DECLARE @SoLuongXuatBanDau int = (SELECT SoLuong FROM XuatKho WHERE MaXuatKho = '" + maXK + "')\n" +
                "DECLARE @SoLuongBayBanTrenKeUpdate int = (SELECT SoLuongTrenKe FROM TrungBay \n" +
                "\t\t\t\t\t\t\t\t\tWHERE MaLo = '" + maLo + "' AND MaSanPham = '" + maSp + "')\n" +
                "UPDATE XuatKho\n" +
                "SET SoLuong = " + soLuong + ", MaNguoiXuat = '" + maNguoiXuat + "', MaNguoiNhan = '" + maNguoiNhan + "', NgayXuat = '" + ngayXuat + "'\n" +
                "WHERE MaXuatKho = '" + maXK + "'\n" +
                "UPDATE TrungBay \n" +
                "SET SoLuongTrenKe = @SoLuongBayBanTrenKeUpdate - @SoLuongXuatBanDau + " + soLuong + "\n" +
                "WHERE MaSanPham = '" + maSp + "' AND MaLo = '" + maLo + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
            CanhBao.showAlertInfo("Chỉnh sửa hàng thành công", null, null);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào bảng Xuất kho và Trưng bày thất bại!", "Failed update", null);
        }
    }
}
