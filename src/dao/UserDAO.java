package dao;

import controller.CanhBao;
import controller.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ThongTinSP;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UserDAO {
    // Lấy thông tin user trong database dưới dạng Vector (1: quản lý, 0: nhân viên)
    private static int check  = 1;
    public static int getCheck() {
        return check;
    }
    public static void setCheck(int check) {
        UserDAO.check = check;
    }

    // Lấy thông tin các user có trong database (đã được cấp mật để đăng nhập)
    public static Vector<User> getUserFromDB() throws SQLException, ClassNotFoundException {
        String sql = "select * from [dbo].[NhanVien] nv \n" +
                "right join [dbo].[Login] login on nv.MaNhanVien = login.MaNhanVien";

        try{
            ResultSet rs = DBUtils.dbExecute(sql);
            Vector<User> vec = getVectorUser(rs);
            return vec;
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi trong quá trình lấy records Users từ DB: " + e);
            e.printStackTrace();
            throw e;
        }
    }
    private static Vector<User> getVectorUser(ResultSet rs) {
        Vector<User> vec = new Vector<User>();
        if(rs != null) {
            try {
                while(rs.next()) {
                    User users = new User(rs.getString(1).toLowerCase().trim(), rs.getString(2).trim(),
                            rs.getString(7).trim(), rs.getString(8).trim(),
                            rs.getString(3).trim(), rs.getString(4).trim(),
                            rs.getString(5).trim());
                    vec.add(users);
                }
            } catch (SQLException e) {
                System.out.println("Đã xảy ra lỗi trong quá trình lấy dữ liệu Users dưới dạng vector từ DB: " + e);
                e.printStackTrace();
            }
        }
        return vec;
    }

    // lấy tất cả bản ghi trong bảng Nhân viên dưới dạng ObservableList
    public static ObservableList<User> getAlLRecords() {
        String sql = "select * from [dbo].[NhanVien] nv \n" +
                "right join [dbo].[Login] login on nv.MaNhanVien = login.MaNhanVien";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                User user = new User(rs.getString(1).toLowerCase().trim(), rs.getString(2).trim(),
                        rs.getString(7).trim(), rs.getString(8).trim(),
                        rs.getString(3).trim(), rs.getString(4).trim(),
                        rs.getString(5).trim());
                list.add(user);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return list;
    }

    // lấy danh sách quản lý
    public static ObservableList<User> getQuanLy() {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n" +
                "FROM NhanVien nv LEFT JOIN Login ON nv.MaNhanVien = Login.MaNhanVien \n" +
                "WHERE VaiTro = N'Quản Lý'";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                User user = new User(rs.getString(1).toLowerCase().trim(), rs.getString(2).trim());
                list.add(user);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return list;
    }

    // lấy danh sách nhân viên bán hàng
    public static ObservableList<User> getNhanVienBanHang() {
        String sql = "SELECT nv.MaNhanVien, nv.TenNhanVien\n" +
                "FROM NhanVien nv LEFT JOIN Login ON nv.MaNhanVien = Login.MaNhanVien \n" +
                "WHERE VaiTro = N'Thu Ngân'";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = DBUtils.dbExecute(sql);
            while(rs.next()) {
                User user = new User(rs.getString(1).toLowerCase().trim(), rs.getString(2).trim());
                list.add(user);
            }

        } catch (Exception e) {
            System.out.println("Không lấy được dữ liệu trong bảng Sản phẩm");
        }
        return list;
    }

    // insert vào 2 bảng Nhân Viên và Login
    public static void insertNhanVien(String maNv, String hoTen, String soDt, String ngayVaoLam, String luong,
                                      String matKhau, String vaiTro) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO NhanVien\n" +
                "VALUES ('" + maNv + "', N'" + hoTen + "', '" + soDt + "', '" + ngayVaoLam + "', " + luong + "); \n" +
                "INSERT INTO Login\n" +
                "VALUES ('" + maNv + "', '" + matKhau + "', N'" + vaiTro + "');";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("insert vào 2 bảng Nhân viên và Login thất bại!", "Failed insert", null);
        }
    }

    // update vào 2 bảng Nhân Viên và Login
    public static void updateNhanVien(String maNv, String hoTen, String soDt, String ngayVaoLam, String luong,
                                      String matKhau, String vaiTro) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE NhanVien \n" +
                "SET TenNhanVien = N'" + hoTen + "', SoDienThoai = '" + soDt + "', NgayVaoLam = '" + ngayVaoLam + "', Luong = " + luong + "\n" +
                "WHERE MaNhanVien = '" + maNv + "';\n" +
                "UPDATE Login \n" +
                "SET MatKhau = '" + matKhau + "', VaiTro = N'" + vaiTro + "'\n" +
                "WHERE MaNhanVien = '" + maNv + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("update vào 2 bảng Nhân viên và Login thất bại!", "Failed update", null);
        }
    }

    // update vào 2 bảng Nhân Viên và Login
    public static void deleteNhanVien(String maNv) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Login WHERE MaNhanVien='" + maNv + "';\n" +
                "DELETE FROM NhanVien WHERE MaNhanVien='" + maNv + "';";
        try {
            DBUtils.dbExecuteQuery(sql);
        } catch (SQLException e) {
            CanhBao.showAlertError("Xóa vào 2 bảng Nhân viên và Login thất bại!", "Failed delete", null);
        }
    }
}
