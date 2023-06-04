package dao;

import controller.CanhBao;
import controller.DBUtils;

import java.sql.SQLException;

public class HoaDonDAO {
    // Thêm dữ liệu mới vào bảng Hóa đơn
    public static void insertHoaDon(String maHoaDon, String dsItem, String ngay, String maNV) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO HoaDon(MaHoaDon, DanhSachMaItem, Ngay, MaNhanVien) \n" +
                "VALUES ('" + maHoaDon + "', '" + dsItem + "', '" + ngay +"', '" + maNV + "');";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("Thêm dữ liệu vào bảng Hóa đơn không thành công", "Failed insert", null);
        }
    }
}
