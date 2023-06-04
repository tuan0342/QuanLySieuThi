package controller;

import dao.HoaDonDAO;
import dao.ItemDonHangDAO;
import dao.SPBayBanDAO;
import dao.ThongTinSPDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.ItemDonHang;
import model.SPBayBan;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.NumberFormat;

/*
*
*  1. Nhân viên insert từng item vào bảng, mỗi item khi được insert vào bảng sẽ được cập nhật vào database (bảng item)
*  2. Sau khi thanh toán xong -> nhấn "Hoàn tất thanh toán"
*  3. Dữ liệu sẽ đc thêm vào bảng 'HoaDon' trong DB
*  4. Dữ liệu này là 1 danh sách các item vừa nhập
*
* */

/*
*
*  - Mã item có dạng: xxxxxxxx-yyyy với 4 chữ đầu là số từ 1000 0000 -> 9999 9999, yyyy là các chữ cái ngẫu nhiên
*       VD: 00001111-acbd
*  - Mã hóa đơn được tạo theo dạng: xxxxxxxx-"yyyy"-"MM"-"dd"
*       VD: 00001111-2023-05-08
*
* */

public class ThanhToanHDController implements Initializable {

    Locale locale = new Locale("en", "EN");
    NumberFormat en = NumberFormat.getInstance(locale);

    @FXML
    public Label lbHoTen;
    @FXML
    public Button Back;
    @FXML
    public Button Gui, TinhTien, Xoa;
    @FXML
    public TextField tfMaHang, tfSoLuong, tfGiaBan, tfTienKhachHangDua;
    @FXML
    public Label lbTongTien, lbHoanTien, lbThoiGianThanhToan;

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        ThanhToanHDController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        ThanhToanHDController.curScene = curScene;
    }

    // Các thành phần trong bảng sản phẩm đang bày bán
    @FXML
    TableView<SPBayBan> tableSPBayBan;
    @FXML
    public TableColumn<SPBayBan, String> colTenSPBayBan;
    @FXML
    public TableColumn<SPBayBan, String> colMaHangBayBan;
    @FXML
    public TableColumn<SPBayBan, String> colGiaBanBayBan;
    ObservableList<SPBayBan> listSPBayBan = null;

    // Các thành phần trong bảng Item
    @FXML
    TableView<ItemDonHang> tableItemDonHang;
    @FXML
    public TableColumn<ItemDonHang, String> colIdItemDonHang;
    @FXML
    public TableColumn<ItemDonHang, String> colIdSPItemDonHang;
    @FXML
    public TableColumn<ItemDonHang, String> colTenSPItemDonHang;
    @FXML
    public TableColumn<ItemDonHang, String> colSLItemDonHang;
    @FXML
    public TableColumn<ItemDonHang, String> colDonGiaItemDonHang;
    ObservableList<ItemDonHang> listItemDonHang = null;

    private String numberCodeItemDonHang;
    private List<String> listItemOfHoaDon = new ArrayList<String>();;
    private String maItemDelete = "", maHangDelete = "";
    private int soLuongDelete = 0, donGiaDelete = 0;
    private int tongTienHoaDon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tongTienHoaDon = 0;
        numberCodeItemDonHang = GenerateCode.generateNumber();

        lbThoiGianThanhToan.setText(GenerateCode.generateCurrentDate());

        // Load dữ liệu vào bảng sản phẩm đang trưng bày
        setCellTable();
        loadDataFromDB();

        // sự kiện khi click vào 1 hàng trong bảng chi tiết khoản thu
        tableSPBayBan.setRowFactory(tv -> {
            TableRow<SPBayBan> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    SPBayBan clickedRow = row.getItem();
                    tfMaHang.setText(clickedRow.getMaHang());
                    tfGiaBan.setText(clickedRow.getGiaBan());
                }
            });
            return row ;
        });

        // sự kiện khi click vào 1 hàng trong bảng Item
        tableItemDonHang.setRowFactory(tv -> {
            TableRow<ItemDonHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    ItemDonHang clickedRow = row.getItem();
                    maItemDelete = clickedRow.getMaItem().trim();
                    maHangDelete = clickedRow.getMaSP().trim();
                    donGiaDelete = Integer.valueOf(clickedRow.getDonGia().trim());
                    soLuongDelete = Integer.valueOf(clickedRow.getSoLuong().trim());
                    Xoa.setDisable(false);
                }
            });
            return row ;
        });

        lbHoTen.setText(ChonChucNangNVController.hoVaTen);
    }

    // load dữ liệu vào bảng
    private void setCellTable() {
        colTenSPBayBan.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colMaHangBayBan.setCellValueFactory(new PropertyValueFactory<>("maHang"));
        colGiaBanBayBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
    }
    // set cột cho bảng
    private void loadDataFromDB() {
        listSPBayBan = SPBayBanDAO.getAlLRecords();
        tableSPBayBan.setItems(listSPBayBan);
    }

    // set cột Item đơn hàng
    private void setCellItemDonHang() {
        colIdItemDonHang.setCellValueFactory(new PropertyValueFactory<>("maItem"));
        colIdSPItemDonHang.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSPItemDonHang.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colDonGiaItemDonHang.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        colSLItemDonHang.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
    }
    // load dữ liệu vào bảng Item đơn hàng
    private void loadDataFromItemDonHang() {
        listItemDonHang = ItemDonHangDAO.getPartRecords(numberCodeItemDonHang);
        tableItemDonHang.setItems(listItemDonHang);
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }

    @FXML
    public void Gui (Event event) throws SQLException, ClassNotFoundException {
        String maItem = GenerateCode.generateMaItem(numberCodeItemDonHang);
        if (checkIsEmpty() == 0) {
            String text = "Nhập đầy đủ thông tin";
            String title = "Lỗi";
            String header = null;
            CanhBao.showAlertError(text, title, header);
        } else {
            listItemOfHoaDon.add(maItem);
            int tongSoLuongSPDaBan = ThongTinSPDAO.getSLBanTheoMaSP(tfMaHang.getText());
            ThongTinSPDAO.updateSLBanSanPham(tfMaHang.getText(), tongSoLuongSPDaBan + Integer.valueOf(tfSoLuong.getText()));  // update vào bảng SanPham
            ItemDonHangDAO.insertItemDonHang(maItem, tfMaHang.getText(), tfSoLuong.getText());  // insert vào bảng Item
            SPBayBanDAO.updateSPBayBanKhiThemItem(maItem, tfMaHang.getText()); // update vào bảng TrungBay
            ThongTinSPDAO.updateSanPhamKhiThemItem(maItem, tfMaHang.getText()); // update vào bảng SanPham
            setCellItemDonHang();
            loadDataFromItemDonHang();
            tinhTongTien(Integer.valueOf(tfGiaBan.getText()), Integer.valueOf(tfSoLuong.getText()));
            lbTongTien.setText(chuanHoaTien(tongTienHoaDon));
            clearTextAdd();
        }
    }

    @FXML
    public void HuyBo (Event event) {

    }

    @FXML
    public void XoaItem (Event event) throws SQLException, ClassNotFoundException {
        if(maItemDelete.isEmpty() || maItemDelete.isEmpty()) {
            CanhBao.showAlertWarning("Chọn sản phẩm trước khi xóa!", "Chưa chọn sản phẩm", null);
        } else {
            int tongSoLuongSPDaBan = ThongTinSPDAO.getSLBanTheoMaSP(maItemDelete);
            ThongTinSPDAO.updateSLBanSanPham(maItemDelete, tongSoLuongSPDaBan - soLuongDelete);  // update vào bảng SanPham
            SPBayBanDAO.updateSPBayBanKhiXoaItem(maItemDelete, maHangDelete);
            ThongTinSPDAO.updateSanPhamKhiXoaItem(maItemDelete, maHangDelete);
            ItemDonHangDAO.deleteItemDonHang(maItemDelete);
            setCellItemDonHang();
            loadDataFromItemDonHang();
            tinhTongTien(-1 * donGiaDelete, soLuongDelete);
            lbTongTien.setText(chuanHoaTien(tongTienHoaDon));
            maItemDelete = "";
            maHangDelete = "";
            donGiaDelete = 0;
            soLuongDelete = 0;
        }

    }

    @FXML
    public void TinhTienThua (Event event) {
        lbHoanTien.setText(chuanHoaTien(- tongTienHoaDon + Integer.valueOf(tfTienKhachHangDua.getText())));
    }

    @FXML
    public void HoanTatThanhToan (Event event) throws SQLException, ClassNotFoundException {
        String listItem = taoItemChoHoaDon();
        String maHoaDon = GenerateCode.generateMaHoaDon(numberCodeItemDonHang);
        numberCodeItemDonHang = GenerateCode.generateNumber();

        listItemOfHoaDon.clear();
        listItemDonHang.clear();
        setCellItemDonHang();
        loadDataFromItemDonHang();
        Xoa.setDisable(true);
        lbTongTien.setText("0");
        lbHoanTien.setText("0");
        tfTienKhachHangDua.clear();

        HoaDonDAO.insertHoaDon(maHoaDon, listItem, GenerateCode.generateCurrentDate(), ChonChucNangNVController.maNV.toUpperCase());
    }

    // clear text trên textfield
    private void clearTextAdd() {
        tfSoLuong.clear();
        tfMaHang.clear();
        tfGiaBan.clear();
    }

    // kiểm tra các dữ liệu được nhập đủ chưa
    private int checkIsEmpty() {
        if(tfMaHang.getText().isEmpty() || tfSoLuong.getText().isEmpty()) {
            return 0;
        }
        return 1;
    }

    // tính tiền
    private int tinhTongTien(int donGia, int soLuong) {
        tongTienHoaDon += donGia * soLuong;
        return tongTienHoaDon;
    }

    // chuẩn hóa form tiền
    private String chuanHoaTien(int tongTien) {
        String str1 = en.format(tongTien);
        return str1;
    }

    // tạo danh sách item cho hóa đơn
    private String taoItemChoHoaDon() {
        String rs = "";
        int sizeList = listItemOfHoaDon.size();
        for (int i=0; i<sizeList-1; i++) {
            rs += listItemOfHoaDon.get(i) + ",";
        }
        rs += listItemOfHoaDon.get(sizeList-1);
        return rs;
    }
}
