package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChonChucNangQLController implements Initializable {
    @FXML
    public Button btnDangXuat;
    @FXML
    public Button btnBack;

    public static String maQL = "";
    public static String tenQL = "";

    @FXML
    private static Scene preScene;
    private static Scene curScene;

    public static void setPreScene(Scene preScene) {
        ChonChucNangQLController.preScene = preScene;
    }

    public static void setCurScene(Scene curScene) {
        ChonChucNangQLController.curScene = curScene;
    }

    public Scene HangHoaScreen;
    public Scene QLNhanVienScreen;
    public Scene QLNhapKhoScreen;
    public Scene QLXuatKhoScreen;

    private void setMenu () {
        FXMLLoader hangHoa = new FXMLLoader(HangHoaController.class.getResource("/view/fxml/HangHoa.fxml"));
        try {
            this.HangHoaScreen = new Scene(hangHoa.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader qlNhanVien = new FXMLLoader(HangHoaController.class.getResource("/view/fxml/QLNhanVien.fxml"));
        try {
            this.QLNhanVienScreen = new Scene(qlNhanVien.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader qlNhapKho = new FXMLLoader(HangHoaController.class.getResource("/view/fxml/QLNhapKho.fxml"));
        try {
            this.QLNhapKhoScreen = new Scene(qlNhapKho.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader qlXuatKho = new FXMLLoader(HangHoaController.class.getResource("/view/fxml/QLXuatKho.fxml"));
        try {
            this.QLXuatKhoScreen = new Scene(qlXuatKho.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenu();
    }

    @FXML
    public void DangXuat (Event event) {
        DBUtils.changeScene(preScene, event);
    }

    @FXML
    public void chonHangHoa (Event event) {
        controller.HangHoaController.setCurScene(this.HangHoaScreen);
        controller.HangHoaController.setPreScene(curScene);
        DBUtils.changeScene(HangHoaScreen, event);
    }

    @FXML
    public void chonQLNhanVien (Event event) {
        controller.QLNhanVienController.setCurScene(this.QLNhanVienScreen);
        controller.QLNhanVienController.setPreScene(curScene);
        DBUtils.changeScene(QLNhanVienScreen, event);
    }

    @FXML
    public void chonQLNhapKho (Event event) {
        controller.QLNhapKhoController.setCurScene(this.QLNhapKhoScreen);
        controller.QLNhapKhoController.setPreScene(curScene);
        DBUtils.changeScene(QLNhapKhoScreen, event);
    }

    @FXML
    public void chonQLXuatKho (Event event) {
        controller.QLXuatKhoController.setCurScene(this.QLXuatKhoScreen);
        controller.QLXuatKhoController.setPreScene(curScene);
        DBUtils.changeScene(QLXuatKhoScreen, event);
    }
}
