package controller;

import dao.NhapKhoDAO;
import dao.ThongTinSPDAO;
import dao.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.NhapKho;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class QLNhapKhoController implements Initializable {

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        QLNhapKhoController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        QLNhapKhoController.curScene = curScene;
    }

    // Các thành phần trong bảng Nhân viên
    @FXML
    TableView<NhapKho> tableNhapKho;
    @FXML
    public TableColumn<NhapKho, String> colMaLo;
    @FXML
    public TableColumn<NhapKho, String> colTenSP;
    @FXML
    public TableColumn<NhapKho, String> colTenDL;
    @FXML
    public TableColumn<NhapKho, String> colTenNguoiNhap;
    @FXML
    public TableColumn<NhapKho, String> colSoLuong;
    @FXML
    public TableColumn<NhapKho, String> colGiaNhap;
    @FXML
    public TableColumn<NhapKho, String> colHanSuDung;
    ObservableList<NhapKho> listNhapKho = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();
        loadDataFromDB();
    }

    // load dữ liệu vào bảng
    private void setCellTable() {
        colMaLo.setCellValueFactory(new PropertyValueFactory<>("maLo"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colTenDL.setCellValueFactory(new PropertyValueFactory<>("tenDaiLy"));
        colTenNguoiNhap.setCellValueFactory(new PropertyValueFactory<>("tenNguoiNhap"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colHanSuDung.setCellValueFactory(new PropertyValueFactory<>("hanSuDung"));
    }
    // set cột cho bảng
    private void loadDataFromDB() {
        listNhapKho = NhapKhoDAO.getAlLRecords();
        tableNhapKho.setItems(listNhapKho);
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }

    @FXML
    public void NhapHangVe (Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ThemHangNhapKhoController.class.getResource("/view/fxml/ThemHangNhapKho.fxml"));
        AnchorPane Them = fxmlLoader.load();
        controller.ThemHangNhapKhoController.setPreScene(this.curScene);

        Scene scene = new Scene(Them, 1000, 600);
        scene.setFill(Color.GRAY);
        controller.ThemHangNhapKhoController.setCurScene(scene);
        DBUtils.changeScene(scene, event);
    }

    @FXML
    public void TaiLaiBang(Event event) {
        setCellTable();
        loadDataFromDB();
    }
}
