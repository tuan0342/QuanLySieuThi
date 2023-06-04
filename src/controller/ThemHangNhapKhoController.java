package controller;

import dao.DaiLyDAO;
import dao.NhapKhoDAO;
import dao.ThongTinSPDAO;
import dao.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.DaiLy;
import model.ThongTinSP;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ThemHangNhapKhoController implements Initializable {

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        ThemHangNhapKhoController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        ThemHangNhapKhoController.curScene = curScene;
    }

    @FXML
    public TextField tfMaLo, tfTenSP, tfTenDaiLy, tfSoLuong, tfGiaNhap, tfHanSuDung;
    @FXML
    public ChoiceBox<String> cbNguoiNhapHang;
    @FXML
    public Button btnLuu, btnHuy;

    ObservableList<User> listQuanLy = null;
    private String maSP = "", maDaiLy = "";

    // Các thành phần trong bảng Sản phẩm
    @FXML
    TableView<ThongTinSP> tableSanPham;
    @FXML
    public TableColumn<ThongTinSP, String> colMaSP;
    @FXML
    public TableColumn<ThongTinSP, String> colTenSP;
    @FXML
    public TableColumn<ThongTinSP, String> colGiaNhap;
    @FXML
    public TableColumn<ThongTinSP, String> colTenDaiLy;
    @FXML
    public TableColumn<ThongTinSP, String> colMaDL;
    ObservableList<ThongTinSP> listSanPham = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellTable();
        loadDataFromDB();

        getDanhSachQuanLy();

        // sự kiện khi click vào 1 hàng trong bảng Sản phẩm
        tableSanPham.setRowFactory(tv -> {
            TableRow<ThongTinSP> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    ThongTinSP clickedRow = row.getItem();

                    maSP = clickedRow.getMaSP().trim();
                    maDaiLy = clickedRow.getMaDaiLy().trim();
                    tfTenSP.setText(clickedRow.getTenSP().trim());
                    tfTenDaiLy.setText(clickedRow.getDaiLyCungCap().trim());
                    tfGiaNhap.setText(clickedRow.getGiaNhap().trim());

                    setDisableInput(false);
                    setDisableBtn(false);
                }
            });
            return row ;
        });
    }

    // load dữ liệu vào bảng
    private void setCellTable() {
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colMaDL.setCellValueFactory(new PropertyValueFactory<>("maDaiLy"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colTenDaiLy.setCellValueFactory(new PropertyValueFactory<>("daiLyCungCap"));
    }
    // set cột cho bảng
    private void loadDataFromDB() {
        listSanPham = ThongTinSPDAO.getAPartRecords();
        tableSanPham.setItems(listSanPham);
    }

    public void getDanhSachQuanLy() {
        listQuanLy = UserDAO.getQuanLy();
        for(User i : listQuanLy) {
            cbNguoiNhapHang.getItems().add(i.getTenNv());
        }
    }

    private void setDisableInput(boolean value) {
        tfGiaNhap.setDisable(value);
        tfTenSP.setDisable(value);
        tfTenDaiLy.setDisable(value);
        tfHanSuDung.setDisable(value);
        tfMaLo.setDisable(value);
        tfSoLuong.setDisable(value);
        cbNguoiNhapHang.setDisable(value);
    }

    private void setDisableBtn(boolean value) {
        btnHuy.setDisable(value);
        btnLuu.setDisable(value);
    }

    private void clearText() {
        tfSoLuong.clear();
        tfGiaNhap.clear();
        tfMaLo.clear();
        tfTenSP.clear();
        tfHanSuDung.clear();
        tfTenDaiLy.clear();
        cbNguoiNhapHang.setValue("");
    }

    private boolean checkEmpty() {
        if(tfMaLo.getText().trim() == "" || tfHanSuDung.getText().trim() == "" || tfGiaNhap.getText().trim() == ""
        || tfTenDaiLy.getText().trim() == "" || tfSoLuong.getText().trim() == "" || tfTenSP.getText().trim() == "" ||
        cbNguoiNhapHang.getValue().trim() == "") {
            return false;
        }
        return true;
    }

    @FXML
    public void Huy(Event event) {
        clearText();
        setDisableInput(true);
        setDisableBtn(true);
        maDaiLy = "";
        maSP = "";
    }

    @FXML
    public void Luu(Event event) throws SQLException, ClassNotFoundException {
        if (checkEmpty() == true) {
            String maNguoiNhapHang = "";
            for (User i: listQuanLy) {
                if(i.getTenNv().trim().equals(cbNguoiNhapHang.getValue().trim())) {
                    maNguoiNhapHang = i.getMaNv();
                    break;
                }
            }
            NhapKhoDAO.insertSanPham(tfMaLo.getText().trim(), maSP, maDaiLy, maNguoiNhapHang.toUpperCase(), tfSoLuong.getText().trim(),
                    tfGiaNhap.getText().trim(), tfHanSuDung.getText().trim());
            CanhBao.showAlertInfo("Thêm thành công!", null, null);

        } else {
            CanhBao.showAlertWarning("Nhập đầy đủ thông tin", "Thêm thất bại", null);
        }

        clearText();
        setDisableInput(true);
        setDisableBtn(true);
        maDaiLy = "";
        maSP = "";
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }
}
