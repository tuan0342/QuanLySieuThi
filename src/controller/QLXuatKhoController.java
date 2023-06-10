package controller;

import dao.NhapKhoDAO;
import dao.UserDAO;
import dao.XuatKhoDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.NhapKho;
import model.ThongTinSP;
import model.User;
import model.XuatKho;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class QLXuatKhoController implements Initializable {

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        QLXuatKhoController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        QLXuatKhoController.curScene = curScene;
    }

    @FXML
    public Button btnHoanTat, btnHuyBo;
    @FXML
    public TextField tfTenSP, tfSoLuong, tfMaLo, tfNgayXuatKho, tfMaXuatKho;
    @FXML
    public ChoiceBox<String> cbNguoiXuat, cbNguoiNhan;

    ObservableList<User> listNguoiXuat = null;
    ObservableList<User> listNguoiNhan = null;
    private String maSP = "", maNguoiNhan = "", maNguoiXuat = "";
    private int luaChon = 0;  // 0: không làm gì, 1: thêm mới, 2: chỉnh sửa

    // Các thành phần trong bảng Nhập kho
    @FXML
    TableView<NhapKho> tableNhapKho;
    @FXML
    public TableColumn<NhapKho, String> colMaLo;
    @FXML
    public TableColumn<NhapKho, String> colTenSP;
    @FXML
    public TableColumn<NhapKho, String> colMaSP;
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

    // Các thành phần trong bảng Nhập kho
    @FXML
    TableView<XuatKho> tableXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colMaXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colMaSPXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colTenSPXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colSoLuongXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colMaNguoiXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colTenNguoiXuatKho;
    @FXML
    public TableColumn<XuatKho, String> colMaNguoiNhan;
    @FXML
    public TableColumn<XuatKho, String> colTenNguoiNhan;
    @FXML
    public TableColumn<XuatKho, String> colNgayXuat;
    @FXML
    public TableColumn<XuatKho, String> colMaLoXuatKho;
    ObservableList<XuatKho> listXuatKho = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bảng nhập kho
        setCellTableNhapKho();
        loadDataFromDBNhapKho();

        // Bảng xuất kho
        setCellTableXuatKho();
        loadDataFromDBXuatKho();

        // Choice Box
        getDanhSachNguoiNhan();
        getDanhSachNguoiXuat();

        // sự kiện khi click vào 1 hàng trong bảng Nhập kho
        tableNhapKho.setRowFactory(tv -> {
            TableRow<NhapKho> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    clearText();
                    luaChon = 1;
                    tfMaXuatKho.setEditable(true);
                    NhapKho clickedRow = row.getItem();

                    maSP = clickedRow.getMaSP().trim();

                    tfTenSP.setText(clickedRow.getTenSP().trim());
                    tfMaLo.setText(clickedRow.getMaLo().trim());

                    setDisableInput(false);
                    setDisableButton(false);
                }
            });
            return row ;
        });

        // sự kiện khi click vào 1 hàng trong bảng Xuất kho
        tableXuatKho.setRowFactory(tv -> {
            TableRow<XuatKho> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    clearText();
                    luaChon = 2;
                    XuatKho clickedRow = row.getItem();

                    maSP = clickedRow.getMaSP().trim();
                    maNguoiNhan = clickedRow.getMaNguoiNhan().trim();
                    maNguoiXuat = clickedRow.getMaNguoiXuat().trim();

                    tfMaXuatKho.setText(clickedRow.getMaXuatKho().trim());
                    tfTenSP.setText(clickedRow.getTenSP().trim());
                    tfMaLo.setText(clickedRow.getMaLo().trim());
                    tfSoLuong.setText(clickedRow.getSoLuong().trim());
                    tfNgayXuatKho.setText(clickedRow.getNgayXuat().trim());
                    cbNguoiNhan.setValue(clickedRow.getNguoiNhan().trim());
                    cbNguoiXuat.setValue(clickedRow.getNguoiXuat().trim());

                    setDisableInput(false);
                    setDisableButton(false);
                }
            });
            return row ;
        });
    }

    public void getDanhSachNguoiXuat() {
        listNguoiXuat = UserDAO.getQuanLy();
        for(User i : listNguoiXuat) {
            cbNguoiXuat.getItems().add(i.getTenNv());
        }
    }
    public void getDanhSachNguoiNhan() {
        listNguoiNhan = UserDAO.getNhanVienBanHang();
        for(User i : listNguoiNhan) {
            cbNguoiNhan.getItems().add(i.getTenNv());
        }
    }
    private void setDisableInput(boolean value) {
        tfMaXuatKho.setDisable(value);
        tfMaLo.setDisable(value);
        tfTenSP.setDisable(value);
        tfSoLuong.setDisable(value);
        tfNgayXuatKho.setDisable(value);
        cbNguoiXuat.setDisable(value);
        cbNguoiNhan.setDisable(value);
    }
    private void setDisableButton(boolean value) {
        btnHoanTat.setDisable(value);
        btnHuyBo.setDisable(value);
    }
    private void clearText() {
        tfSoLuong.clear();
        tfMaLo.clear();
        tfNgayXuatKho.clear();
        tfTenSP.clear();
        cbNguoiXuat.setValue("");
        cbNguoiNhan.setValue("");
    }
    private boolean checkEmpty() {
        if(tfMaLo.getText().trim() == "" || tfTenSP.getText().trim() == "" || tfNgayXuatKho.getText().trim() == "" ||
        tfSoLuong.getText().trim() == "" || cbNguoiNhan.getValue().trim() == "" || cbNguoiXuat.getValue().trim() == ""){
            return false;
        }
        return true;
    }

    // load dữ liệu vào bảng Nhập kho
    private void setCellTableNhapKho() {
        colMaLo.setCellValueFactory(new PropertyValueFactory<>("maLo"));
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colTenDL.setCellValueFactory(new PropertyValueFactory<>("tenDaiLy"));
        colTenNguoiNhap.setCellValueFactory(new PropertyValueFactory<>("tenNguoiNhap"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colGiaNhap.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        colHanSuDung.setCellValueFactory(new PropertyValueFactory<>("hanSuDung"));
    }
    // set cột cho bảng Nhập kho
    private void loadDataFromDBNhapKho() {
        listNhapKho = NhapKhoDAO.getAlLRecordsIncludeMaSP();
        tableNhapKho.setItems(listNhapKho);
    }

    // load dữ liệu vào bảng
    private void setCellTableXuatKho() {
        colMaXuatKho.setCellValueFactory(new PropertyValueFactory<>("maXuatKho"));
        colMaSPXuatKho.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSPXuatKho.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colSoLuongXuatKho.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colMaNguoiXuatKho.setCellValueFactory(new PropertyValueFactory<>("maNguoiXuat"));
        colTenNguoiXuatKho.setCellValueFactory(new PropertyValueFactory<>("nguoiXuat"));
        colMaNguoiNhan.setCellValueFactory(new PropertyValueFactory<>("maNguoiNhan"));
        colTenNguoiNhan.setCellValueFactory(new PropertyValueFactory<>("nguoiNhan"));
        colNgayXuat.setCellValueFactory(new PropertyValueFactory<>("ngayXuat"));
        colMaLoXuatKho.setCellValueFactory(new PropertyValueFactory<>("maLo"));
    }
    // set cột cho bảng
    private void loadDataFromDBXuatKho() {
        listXuatKho = XuatKhoDAO.getAlLRecords();
        tableXuatKho.setItems(listXuatKho);
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }

    @FXML
    public void HuyBo (Event event) {
        tfMaXuatKho.setEditable(false);
        setDisableInput(true);
        setDisableButton(true);
        clearText();
        luaChon = 0;
        maNguoiXuat = "";
        maSP = "";
        maNguoiNhan = "";
    }

    @FXML
    public void HoanTat (Event event) throws SQLException, ClassNotFoundException {
        if(checkEmpty() == true) {
            // Tìm mã người xuất hàng
            for (User i: listNguoiXuat) {
                if(i.getTenNv().trim().equals(cbNguoiXuat.getValue().trim())) {
                    maNguoiXuat = i.getMaNv();
                    break;
                }
            }
            // Tìm mã người nhận hàng
            for (User i: listNguoiNhan) {
                if(i.getTenNv().trim().equals(cbNguoiNhan.getValue().trim())) {
                    maNguoiNhan = i.getMaNv();
                    break;
                }
            }

            if(luaChon == 1) {
                XuatKhoDAO.insertXuatKho(tfMaXuatKho.getText().trim(), maSP, tfSoLuong.getText().trim(),
                        maNguoiXuat.toUpperCase(), maNguoiNhan.toUpperCase(), tfNgayXuatKho.getText().trim(), tfMaLo.getText().trim());

            } else if(luaChon == 2){
                XuatKhoDAO.updateXuatKho(tfMaXuatKho.getText().trim(), maSP, tfSoLuong.getText().trim(),
                        maNguoiXuat.toUpperCase(), maNguoiNhan.toUpperCase(), tfNgayXuatKho.getText().trim(), tfMaLo.getText().trim());

            }

            setCellTableXuatKho();
            loadDataFromDBXuatKho();
        } else {
            CanhBao.showAlertWarning("Nhập thiếu thông tin", "Thất bại", null);
        }

        luaChon = 0;
        maNguoiXuat = "";
        maSP = "";
        maNguoiNhan = "";
        tfMaXuatKho.setEditable(false);
        setDisableInput(true);
        setDisableButton(true);
        clearText();
    }
}
