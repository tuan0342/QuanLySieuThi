package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.SPBayBan;
import model.ThongTinSP;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongTinSPDAO {
    // lấy tất cả bản ghi trong bảng Sản phẩm
    public static ObservableList<ThongTinSP> getAlLRecords() {
        String sql = "SELECT TenSanPham, PhanLoai, GiaNhap, GiaBan, dl.TenDaiLy, SoLuongDaNhap, SoLuongDaBan, TongSoLuong, MaSanPham\n" +
                "FROM SanPham sp LEFT JOIN DaiLy dl on sp.MaDaiLy = dl.MaDaiLy";
        ObservableList<ThongTinSP> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                ThongTinSP thongTinSP = new ThongTinSP(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
                list.add(thongTinSP);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return list;
    }

    // lấy thông tin bản ghi trong bảng Sản phẩm
    public static ObservableList<ThongTinSP> getAPartRecords() {
        String sql = "SELECT sp.MaSanPham, TenSanPham,  dl.TenDaiLy, GiaNhap, dl.MaDaiLy\n" +
                "FROM SanPham sp LEFT JOIN DaiLy dl on sp.MaDaiLy = dl.MaDaiLy";
        ObservableList<ThongTinSP> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                ThongTinSP thongTinSP = new ThongTinSP(rs.getString(1).trim(), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5).trim());
                list.add(thongTinSP);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return list;
    }

    public static int getSLBanTheoMaSP(String maSP) {
        String sql = "SELECT SoLuongDaBan\n" +
                "FROM SanPham \n" +
                "WHERE MaSanPham = '" + maSP + "';";
        int soLuong = 0;
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                soLuong = Integer.valueOf(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return soLuong;
    }

    // update vào bảng Sản phẩm
    public static void updateSanPham(String maSP, String tenSP, String phanLoai, String giaNhap, String giaBan) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE SanPham \n" +
                "SET TenSanPham = N'" + tenSP + "', PhanLoai = N'" + phanLoai + "', GiaNhap = " + giaNhap + ", GiaBan =" + giaBan + "\n" +
                "WHERE MaSanPham = '" + maSP + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào bảng Sản phẩm thất bại!", "Failed update", null);
        }
    }

    // update vào bảng Sản phẩm
    public static void updateSLBanSanPham(String maSP, int soLuongBan) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE SanPham \n" +
                "SET SoLuongDaBan = " + soLuongBan + "\n" +
                "WHERE MaSanPham = '" + maSP + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update số lượng bán vào bảng Sản phẩm thất bại!", "Failed update", null);
        }
    }

    // insert vào bảng Sản phẩm
    public static void insertSanPham(String maSP, String tenSP, String phanLoai, String giaNhap, String giaBan, String maDL) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO SanPham\n" +
                "VALUES ('" + maSP + "', N'" + tenSP + "', N'" + phanLoai + "', 0," + giaNhap + "," + giaBan + ", '" +
                maDL + "', 0, 0);";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("insert vào báng Sản phẩm thất bại!", "Failed insert", null);
        }
    }

    // update vào bảng Sản phẩm khi thêm mới Item
    public static void updateSanPhamKhiThemItem(String maItem, String maSP) throws SQLException, ClassNotFoundException {
        String sql = "DECLARE @soLuongDaBan int = (select SoLuong from Item where MaItem = '" + maItem + "')\n" +
                "UPDATE SanPham \n" +
                "SET SoLuongDaBan = SoLuongDaBan + @soLuongDaBan\n" +
                "WHERE MaSanPham = '" + maSP + "'";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào bảng Sản phẩm khi thêm mới Item thất bại!", "Failed update", null);
        }
    }

    // update vào bảng Sản phẩm khi thêm mới Item
    public static void updateSanPhamKhiXoaItem(String maItem, String maSP) throws SQLException, ClassNotFoundException {
        String sql = "DECLARE @soLuongDaBan int = (select SoLuong from Item where MaItem = '" + maItem + "')\n" +
                "UPDATE SanPham \n" +
                "SET SoLuongDaBan = SoLuongDaBan - @soLuongDaBan\n" +
                "WHERE MaSanPham = '" + maSP + "'";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào bảng Sản phẩm khi thêm mới Item thất bại!", "Failed update", null);
        }
    }
}
