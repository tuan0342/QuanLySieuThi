package controller;

import dao.DaiLyDAO;
import dao.ThongTinSPDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DaiLy;
import model.ThongTinSP;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ThemMoiDaiLyController implements Initializable {

    @FXML
    public TextField tfMaDL, tfTenDL, tfSoDT, tfDiaChi;


    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        ThemMoiDaiLyController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        ThemMoiDaiLyController.curScene = curScene;
    }

    // Các thành phần trong bảng Đại lý
    @FXML
    TableView<DaiLy> tableDaiLy;
    @FXML
    public TableColumn<DaiLy, String> colMaDL;
    @FXML
    public TableColumn<DaiLy, String> colTenDL;
    @FXML
    public TableColumn<DaiLy, String> colSoDT;
    @FXML
    public TableColumn<DaiLy, String> colDiaChi;

    ObservableList<DaiLy> listDaiLy = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load dữ liệu vào bảng Sản phẩm
        setCellTable();
        loadDataFromDB();
    }

    // load dữ liệu vào bảng
    private void setCellTable() {
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colTenDL.setCellValueFactory(new PropertyValueFactory<>("tenDL"));
        colMaDL.setCellValueFactory(new PropertyValueFactory<>("maDL"));
        colSoDT.setCellValueFactory(new PropertyValueFactory<>("soDt"));
    }
    // set cột cho bảng
    private void loadDataFromDB() {
        listDaiLy = DaiLyDAO.getAlLRecords();
        tableDaiLy.setItems(listDaiLy);
    }

    @FXML
    public void LuuThongTin (Event event) throws SQLException, ClassNotFoundException {
        if (checkEmptyTF() == true) {
            DaiLyDAO.insertDaiLy(tfMaDL.getText().trim(), tfTenDL.getText().trim(), tfSoDT.getText().trim(),
                    tfDiaChi.getText().trim());
            tfDiaChi.clear();
            tfMaDL.clear();
            tfSoDT.clear();
            tfTenDL.clear();

            setCellTable();
            loadDataFromDB();
        } else {
            CanhBao.showAlertWarning("Hãy nhập đủ thông tin", "Thất bại", null);
        }


    }
    private boolean checkEmptyTF() {
        if(tfTenDL.getText().trim() == "" || tfMaDL.getText().trim() == "" || tfSoDT.getText().trim() == ""
                || tfDiaChi.getText().trim() == "") {
            return false;
        }
        return true;
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }
}
