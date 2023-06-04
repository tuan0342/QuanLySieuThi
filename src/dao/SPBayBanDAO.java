package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.SPBayBan;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SPBayBanDAO {
    // lấy tất cả bản ghi trong bảng Sản phẩm bày bán
    public static ObservableList<SPBayBan> getAlLRecords() {
        String sql = "select sp.TenSanPham, tb.MaSanPham , sp.GiaBan from [dbo].[TrungBay] tb \n" +
                "inner join [dbo].[SanPham] sp on sp.MaSanPham = tb.MaSanPham";
        ObservableList<SPBayBan> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                SPBayBan spBayBan = new SPBayBan(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(spBayBan);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm bày bán");
        }
        return list;
    }

    // update vào bảng Sản phẩm bày bán khi thêm mới Item
    public static void updateSPBayBanKhiThemItem(String maItem, String maSP) throws SQLException, ClassNotFoundException {
        String sql = "DECLARE @soLuongBan int = (select SoLuong from Item where MaItem = '" + maItem + "');\n" +
                "UPDATE TrungBay \n" +
                "SET SoLuongTrenKe = SoLuongTrenKe - @soLuongBan\n" +
                "WHERE MaSanPham = '" + maSP + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào bảng Sản phẩm bày bán khi thêm mới Item thất bại!", "Failed update", null);
        }
    }

    // update vào bảng Sản phẩm bày bán khi xóa Item
    public static void updateSPBayBanKhiXoaItem(String maItem, String maSP) throws SQLException, ClassNotFoundException {
        String sql = "DECLARE @soLuongXoa int = (select SoLuong from Item where MaItem = '" + maItem + "');\n" +
                "UPDATE TrungBay \n" +
                "SET SoLuongTrenKe = SoLuongTrenKe + @soLuongXoa\n" +
                "WHERE MaSanPham = '" + maSP + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào bảng Sản phẩm bày bán khi xóa Item thất bại!", "Failed update", null);
        }
    }


}
