package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DaiLy;
import model.ThongTinSP;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DaiLyDAO {
    // lấy tất cả bản ghi trong bảng Đại Lý
    public static ObservableList<DaiLy> getAlLRecords() {
        String sql = "SELECT * FROM DaiLy";
        ObservableList<DaiLy> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                DaiLy daiLy = new DaiLy(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4));
                list.add(daiLy);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return list;
    }

    // insert vào bảng Sản phẩm
    public static void insertDaiLy(String maDL, String tenDL, String soDT, String diaChi) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO DaiLy\n" +
                "VALUES ('" + maDL + "', N'" + tenDL + "', '" + soDT + "', N'" + diaChi + "');";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("insert vào báng Đại lý thất bại!", "Failed insert", null);
        }
    }

}
