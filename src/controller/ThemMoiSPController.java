package controller;

import dao.DaiLyDAO;
import dao.ThongTinSPDAO;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.DaiLy;
import model.ThongTinSP;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ThemMoiSPController implements Initializable {

    @FXML
    public TextField tfMaSP, tfTenSP, tfPhanLoai, tfGiaNhap, tfGiaBan;

    @FXML
    public ChoiceBox<String> choiceBoxDaiLy = new ChoiceBox<>();
    ObservableList<DaiLy> listDaiLy = null;

    private static Scene preScene;
    private static Scene curScene;
    public static Scene getPreScene() {
        return preScene;
    }
    public static void setPreScene(Scene preScene) {
        ThemMoiSPController.preScene = preScene;
    }
    public static Scene getCurScene() {
        return curScene;
    }
    public static void setCurScene(Scene curScene) {
        ThemMoiSPController.curScene = curScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDanhSachDaiLy();
    }

    public void getDanhSachDaiLy() {
        listDaiLy = DaiLyDAO.getAlLRecords();
        for(DaiLy i : listDaiLy) {
            choiceBoxDaiLy.getItems().add(i.getTenDL());
        }
    }

    public void clearTextField() {
        tfGiaBan.clear();
        tfGiaNhap.clear();
        tfPhanLoai.clear();
        tfTenSP.clear();
        tfMaSP.clear();
        choiceBoxDaiLy.setValue("");
    }

    private boolean checkEmptyTextField() {
        if (tfMaSP.getText().trim() == "" ||  tfTenSP.getText().trim() == "" ||  tfPhanLoai.getText().trim() == "" ||
                tfGiaNhap.getText().trim() == "" || tfGiaBan.getText().trim() == "" || choiceBoxDaiLy.getValue().trim() == "") {
            return false;
        }
        return true;
    }


    @FXML
    public void insertSanPham (Event event) throws SQLException, ClassNotFoundException {
        if (checkEmptyTextField() == true) {
            String maDL = "";
            for (DaiLy i: listDaiLy) {
                if(i.getTenDL().trim().equals(choiceBoxDaiLy.getValue().trim())) {
                    maDL = i.getMaDL();
                    break;
                }
            }
            ThongTinSPDAO.insertSanPham(tfMaSP.getText(), tfTenSP.getText(), tfPhanLoai.getText(), tfGiaNhap.getText(),
                    tfGiaBan.getText(), maDL);
            clearTextField();
            CanhBao.showAlertInfo("Thêm sản phẩm thành công!", null, null);
        } else {
            CanhBao.showAlertWarning("Hãy nhập đủ thông tin", "Thất bại", null);
        }
    }

    @FXML
    public void TaoMoiDaiLy (Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ThemMoiDaiLyController.class.getResource("/view/fxml/ThemMoiDaiLy.fxml"));
        AnchorPane Them = fxmlLoader.load();
        controller.ThemMoiDaiLyController.setPreScene(this.curScene);

        Scene scene = new Scene(Them, 1000, 600);
        scene.setFill(Color.GRAY);
        controller.ThemMoiDaiLyController.setCurScene(scene);
        DBUtils.changeScene(scene, event);
    }

    @FXML
    public void Load (Event event) {
        System.out.println("1. chiều dài chuỗi trước khi xóa là: " + listDaiLy.size());
        if(listDaiLy.size() > 0) {
            listDaiLy.clear();
            choiceBoxDaiLy.getItems().removeAll(choiceBoxDaiLy.getItems());
            System.out.println("2. chiều dài chuỗi sau khi xóa là: " + listDaiLy.size());
        }
        getDanhSachDaiLy();
    }

    @FXML
    public void Back (Event event) {
        DBUtils.changeScene(preScene, event);
    }
}
