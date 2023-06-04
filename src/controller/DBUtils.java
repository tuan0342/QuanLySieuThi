package controller;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class DBUtils {
    private static String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLySieuThi;encrypt=true;trustServerCertificate=true;";
    private static String user = "sa"; // user trên mỗi máy là khác nhau tùy cá nhân tự đặt
    private static String pass = "123";  // pass trên mỗi máy là khác nhau tùy cá nhân tự đặt
    private static Connection connection ;

    // connect database
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver ở đâu rồi?");
            e.printStackTrace();
        }

        System.out.println("\nKết nối thành công");
        System.out.println("--------------------------------------------------------------");

        try {
            connection = DriverManager.getConnection(dbURL, user, pass);
//            System.out.print("HONGHANH");
//            if (connection != null) System.out.print("NGHIEM PHONG");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại! Check output console" + e);
            e.printStackTrace();
        }
    }

    // disconnect database
    public static void dbDisconnect() throws SQLException, ClassNotFoundException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi disconnect database: " + e);
            e.printStackTrace();
        }
    }

    // this is for insert/update/delete operation
    public static void dbExecuteQuery(String sqlStmt)throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Xảy ra vấn đề ở hàm dbExecuteQuery");
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

    // this is for retriving the record from the database
    public static ResultSet dbExecute(String sqlQuery) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            dbConnect();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(sqlQuery);
            ps = connection.prepareStatement(sqlQuery);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Có lỗi xảy ra ở hàm dbExecute (truy xuất dữ liệu)");
            throw e;
        } finally {

        }
        return rs;
    }

    // Phương thức chuyển màn hình
    public static void changeScene(Scene scene, Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
