package controller;

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
import model.ItemDonHang;
import model.ThongTinSP;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class QLNhanVienController implements Initializable {

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        QLNhanVienController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        QLNhanVienController.curScene = curScene;
    }

    @FXML
    public TextField tfMaNV, tfTenNV, tfSoDT, tfNgayVaoLam, tfLuong, tfMatKhau;
    @FXML
    public ChoiceBox<String> cbVaiTro;
    @FXML
    public Button btnChinhSuaTT, btnXoaNhanVien, btnLuu, btnHuyBo, btnThemNhanVien;

    // Các thành phần trong bảng Nhân viên
    @FXML
    TableView<User> tableNhanVien;
    @FXML
    public TableColumn<User, String> colMaNV;
    @FXML
    public TableColumn<User, String> colTenNV;
    @FXML
    public TableColumn<User, String> colSoDT;
    @FXML
    public TableColumn<User, String> colNgayVaoLam;
    @FXML
    public TableColumn<User, String> colLuong;
    @FXML
    public TableColumn<User, String> colMatKhau;
    @FXML
    public TableColumn<User, String> colVaiTro;
    ObservableList<User> listNhanVien = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load dữ liệu vào bảng Sản phẩm
        setCellTable();
        loadDataFromDB();

        cbVaiTro.getItems().add("Thu Ngân");
        cbVaiTro.getItems().add("Quản Lý");

        // sự kiện khi click vào 2 hàng trong bảng Item
        tableNhanVien.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    User clickedRow = row.getItem();
                    btnChinhSuaTT.setDisable(false);
                    btnXoaNhanVien.setDisable(false);
                    btnHuyBo.setDisable(false);
                    btnThemNhanVien.setDisable(true);
                    setDisable(false);

                    tfMaNV.setText(clickedRow.getMaNv().trim());
                    tfTenNV.setText(clickedRow.getTenNv().trim());
                    tfLuong.setText(clickedRow.getLuong().trim());
                    tfMatKhau.setText(clickedRow.getMatKhau().trim());
                    tfNgayVaoLam.setText(clickedRow.getNgayVaoLam().trim());
                    tfSoDT.setText(clickedRow.getSoDt().trim());
                    cbVaiTro.setValue(clickedRow.getVaiTro().trim());
                }
            });
            return row ;
        });
    }

    // load dữ liệu vào bảng
    private void setCellTable() {
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNv"));
        colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNv"));
        colSoDT.setCellValueFactory(new PropertyValueFactory<>("soDt"));
        colNgayVaoLam.setCellValueFactory(new PropertyValueFactory<>("ngayVaoLam"));
        colLuong.setCellValueFactory(new PropertyValueFactory<>("luong"));
        colMatKhau.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
        colVaiTro.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));
    }
    // set cột cho bảng
    private void loadDataFromDB() {
        listNhanVien = UserDAO.getAlLRecords();
        tableNhanVien.setItems(listNhanVien);
    }

    private void setDisable(boolean value) {
        tfMaNV.setDisable(value);
        tfTenNV.setDisable(value);
        tfMatKhau.setDisable(value);
        tfLuong.setDisable(value);
        tfNgayVaoLam.setDisable(value);
        tfSoDT.setDisable(value);
        cbVaiTro.setDisable(value);
    }

    private void clearText() {
        tfSoDT.clear();
        tfLuong.clear();
        tfMatKhau.clear();
        tfTenNV.clear();
        tfNgayVaoLam.clear();
        tfMaNV.clear();
        cbVaiTro.setValue("");
    }

    private boolean checkEmpty() {
        if (tfMaNV.getText().trim() == "" || tfTenNV.getText().trim() == "" || tfLuong.getText().trim() == "" ||
        tfMatKhau.getText().trim() == "" || tfSoDT.getText().trim() == "" || tfNgayVaoLam.getText().trim() == "" ||
        cbVaiTro.getValue().trim() == "") {
            return false;
        }
        return true;
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }

    @FXML
    public void ThemNhanVien(Event event) {
        setDisable(false);
        btnHuyBo.setDisable(false);
        btnLuu.setDisable(false);
    }

    @FXML
    public void ChinhSuaNhanVien(Event event) throws SQLException, ClassNotFoundException {
        UserDAO.updateNhanVien(tfMaNV.getText().trim(), tfTenNV.getText().trim(), tfSoDT.getText().trim(),
                tfNgayVaoLam.getText().trim(), tfLuong.getText().trim(), tfMatKhau.getText().trim(),
                cbVaiTro.getValue().trim());
        clearText();
        setDisable(true);
        CanhBao.showAlertInfo("Cập nhật nhân viên thành công", null, null);

        setDisable(true);
        btnChinhSuaTT.setDisable(true);
        btnHuyBo.setDisable(true);
        btnXoaNhanVien.setDisable(true);
        btnThemNhanVien.setDisable(false);

        setCellTable();
        loadDataFromDB();
    }

    @FXML
    public void XoaNhanVien(Event event) throws SQLException, ClassNotFoundException {
        UserDAO.deleteNhanVien(tfMaNV.getText().trim());
        clearText();
        setDisable(true);
        CanhBao.showAlertInfo("Xóa nhân viên thành công", null, null);

        setDisable(true);
        btnChinhSuaTT.setDisable(true);
        btnHuyBo.setDisable(true);
        btnXoaNhanVien.setDisable(true);
        btnThemNhanVien.setDisable(false);

        setCellTable();
        loadDataFromDB();
    }

    @FXML
    public void Luu (Event event) throws SQLException, ClassNotFoundException {
        if(checkEmpty() == true) {
            UserDAO.insertNhanVien(tfMaNV.getText().trim(), tfTenNV.getText().trim(), tfSoDT.getText().trim(),
                    tfNgayVaoLam.getText().trim(), tfLuong.getText().trim(), tfMatKhau.getText().trim(),
                    cbVaiTro.getValue().trim());
            clearText();
            setDisable(true);
            btnChinhSuaTT.setDisable(true);
            btnXoaNhanVien.setDisable(true);

            CanhBao.showAlertInfo("Thêm mới nhân viên thành công", null, null);

            setCellTable();
            loadDataFromDB();
        } else {
            CanhBao.showAlertWarning("Nhập đầy đủ thông tin", "Thêm mới thất bại", null);
        }
    }

    @FXML
    public void HuyBo (Event event) {
        setDisable(true);
        btnChinhSuaTT.setDisable(true);
        btnXoaNhanVien.setDisable(true);
        btnHuyBo.setDisable(true);
        btnLuu.setDisable(true);
        clearText();
    }
}
