package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ItemDonHang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDonHangDAO {
    // Thêm dữ liệu mới vào bảng Item đơn hàng
    public static void insertItemDonHang(String maItem, String maSP, String soLuong) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item(MaItem, MaSanPham, SoLuong)\n" +
                "VALUES ('" + maItem +"', '" + maSP + "' ,'" + soLuong + "' );";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("Thêm dữ liệu vào bảng Item không thành công", "Failed insert", null);
        }
    }

    // lất tất cả bản ghi trong bảng Item đơn hàng
    public static ObservableList<ItemDonHang> getAlLRecords() {
        String sql = "";
        ObservableList<ItemDonHang> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                ItemDonHang itemDonHang = new ItemDonHang(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(5),rs.getString(4));
                list.add(itemDonHang);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Item đơn hàng");
        }
        return list;
    }

    // lấy một phần bản ghi trong bảng Item đơn hàng (dựa vào mã số)
    public static ObservableList<ItemDonHang> getPartRecords(String numberCodeItemDonHang) {
        String sql = "select item.MaItem, item.MaSanPham, sp.TenSanPham, sp.GiaBan, item.SoLuong\n" +
                "from [dbo].[Item] item left join [dbo].[SanPham] sp on item.MaSanPham = sp.MaSanPham\n" +
                "where LEFT(item.MaItem, 8) = '" + numberCodeItemDonHang + "'";
        ObservableList<ItemDonHang> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                ItemDonHang itemDonHang = new ItemDonHang(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(5),rs.getString(4));
                list.add(itemDonHang);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu");
        }
        return list;
    }

    // xóa phần tử trong bảng Item đơn hàng
    public static void deleteItemDonHang(String maItem) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Item WHERE MaItem = '" + maItem + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("Xóa phần tử bảng Item không thành công", "Failed delete", null);
        } 
    }
}
