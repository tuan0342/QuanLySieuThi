package controller;

import dao.SPBayBanDAO;
import dao.ThongTinSPDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.ItemDonHang;
import model.SPBayBan;
import model.ThongTinSP;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HangHoaController implements Initializable {

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        HangHoaController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        HangHoaController.curScene = curScene;
    }

    @FXML
    public Button btnThemMoiSanPham, btnHoanTat, btnHuyBo;
    @FXML
    public TextField tfTenSP, tfPhanLoai, tfGiaNhap, tfGiaBan;
    @FXML
    public Label lbHeader;

    // Các thành phần trong bảng Sản phẩm
    @FXML
    TableView<ThongTinSP> tableSanPham;
    @FXML
    public TableColumn<ThongTinSP, String> colTenSP;
    @FXML
    public TableColumn<ThongTinSP, String> colPhanLoai;
    @FXML
    public TableColumn<ThongTinSP, String> colGiaNhap;
    @FXML
    public TableColumn<ThongTinSP, String> colGiaBan;
    @FXML
    public TableColumn<ThongTinSP, String> colTenDaiLy;
    @FXML
    public TableColumn<ThongTinSP, String> colSoLuongNhap;
    @FXML
    public TableColumn<ThongTinSP, String> colSoLuongBan;
    @FXML
    public TableColumn<ThongTinSP, String> colTongSL;
    @FXML
    public TableColumn<ThongTinSP, String> colMaSP;
    ObservableList<ThongTinSP> listSanPham = null;

    private String maSpUpdate = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load dữ liệu vào bảng Sản phẩm
        setCellTable();
        loadDataFromDB();

        // sự kiện khi click vào 1 hàng trong bảng chi tiết khoản thu
        tableSanPham.setRowFactory(tv -> {
            TableRow<ThongTinSP> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    ThongTinSP clickedRow = row.getItem();

                    setDisableTextField(false);
                    lbHeader.setVisible(true);

                    tfTenSP.setText(clickedRow.getTenSP());
                    tfPhanLoai.setText(clickedRow.getPhanLoai());
                    tfGiaBan.setText(clickedRow.getGiaBan());
                    tfGiaNhap.setText(clickedRow.getGiaNhap());
                    maSpUpdate = clickedRow.getMaSP();

                    setDisableButton(false);
                }
            });
            return row ;
        });

        btnHuyBo.setOnAction(event -> {
            setDisableTextField(true);
            clearTextField();
            setDisableButton(true);
            lbHeader.setVisible(false);
        });
    }

    public void setDisableTextField(boolean value) {
        tfGiaNhap.setDisable(value);
        tfTenSP.setDisable(value);
        tfGiaBan.setDisable(value);
        tfPhanLoai.setDisable(value);
    }

    public void setDisableButton(boolean value) {
        btnHoanTat.setDisable(value);
        btnHuyBo.setDisable(value);
    }

    public void clearTextField() {
        tfGiaBan.clear();
        tfTenSP.clear();
        tfPhanLoai.clear();
        tfGiaNhap.clear();
    }

    // load dữ liệu vào bảng
    private void setCellTable() {
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colPhanLoai.setCellValueFactory(new PropertyValueFactory<>("phanLoai"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colTenDaiLy.setCellValueFactory(new PropertyValueFactory<>("daiLyCungCap"));
        colSoLuongNhap.setCellValueFactory(new PropertyValueFactory<>("slNhap"));
        colSoLuongBan.setCellValueFactory(new PropertyValueFactory<>("slBan"));
        colTongSL.setCellValueFactory(new PropertyValueFactory<>("tongSL"));
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
    }
    // set cột cho bảng
    private void loadDataFromDB() {
        listSanPham = ThongTinSPDAO.getAlLRecords();
        tableSanPham.setItems(listSanPham);
    }

    @FXML
    public void ChinhSuaThongTinSP (Event event) throws SQLException, ClassNotFoundException {
        ThongTinSPDAO.updateSanPham(maSpUpdate, tfTenSP.getText().trim(), tfPhanLoai.getText().trim(),
                tfGiaNhap.getText().trim(), tfGiaBan.getText().trim());
        clearTextField();
        setDisableButton(true);
        lbHeader.setVisible(false);
        setDisableButton(true);
        setCellTable();
        loadDataFromDB();

        CanhBao.showAlertInfo("Cập nhật dữ liệu thành công", null, null);
    }

    @FXML
    public void ThemMoiSanPham (Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ThemMoiSPController.class.getResource("/view/fxml/ThemMoiSP.fxml"));
        AnchorPane Them = fxmlLoader.load();
        controller.ThemMoiSPController.setPreScene(this.curScene);

        Scene scene = new Scene(Them, 1000, 600);
        scene.setFill(Color.GRAY);
        controller.ThemMoiSPController.setCurScene(scene);
        DBUtils.changeScene(scene, event);
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }
}
