package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChonChucNangNVController implements Initializable {
    public static String hoVaTen;
    public static String maNV;
    public static String soDt;

    @FXML
    public Button btnDangXuat;
    @FXML
    public Label lbHoTen, lbMaNV, lbSoDT;

    @FXML
    private static Scene preScene;
    private static Scene curScene;

    public static void setPreScene(Scene preScene) {
        ChonChucNangNVController.preScene = preScene;
    }

    public static void setCurScene(Scene curScene) {
        ChonChucNangNVController.curScene = curScene;
    }

    public Scene ThanhToanHD;
//    private ThanhToanHDController thanhToanHDController;

    private void setMenu () {
        FXMLLoader TTHD = new FXMLLoader(ThanhToanHDController.class.getResource("/view/fxml/ThanhToanHoaDon.fxml"));
        try {
            this.ThanhToanHD = new Scene(TTHD.load());
//            this.ThanhToanHD = TTHD.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbHoTen.setText(hoVaTen);
        lbMaNV.setText(maNV);
        lbSoDT.setText(soDt);
        setMenu();
    }

    @FXML
    public void DangXuat (Event event) {
        DBUtils.changeScene(preScene, event);
    }

    @FXML
    public void chonThanhToanHoaDon (Event event) {
        controller.ThanhToanHDController.setCurScene(this.ThanhToanHD);
        controller.ThanhToanHDController.setPreScene(curScene);
//        thanhToanHDController.dsNhanKhau.refresh();
//        thanhToanHDController.setDsNhanKhau(NhanKhauStatic.getDsNhanKhau());
        DBUtils.changeScene(ThanhToanHD, event);
    }
}
