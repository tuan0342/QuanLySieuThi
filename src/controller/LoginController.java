package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

public class LoginController implements Initializable {
    @FXML
    public TextField tfUserName;
    @FXML
    public PasswordField pwfPassWord;
    @FXML
    public Button btnLogin;

    private Scene nextScene;
    private Scene curScene;
    public void setNextSceneQL() {
        FXMLLoader fxmlLoader = new FXMLLoader(ChonChucNangQLController.class.getResource("/view/fxml/ChonChucNangQL.fxml"));
        try {
            Parent root = fxmlLoader.load();
            this.nextScene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setNextSceneNV() {
        FXMLLoader fxmlLoader = new FXMLLoader(ChonChucNangNVController.class.getResource("/view/fxml/ChonChucNangNV.fxml"));
        try {
            Parent root = fxmlLoader.load();
            this.nextScene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCurScene(Scene scene) {
        this.curScene = scene;
    }

    public Scene getCurScene() {
        return curScene;
    }

    Vector<User> vectorUsers = new Vector<User>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            vectorUsers = UserDAO.getUserFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Sự kiện liên quan đến phim 'Enter'
        tfUserName.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                pwfPassWord.requestFocus();
            }
        });
        pwfPassWord.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnLogin.requestFocus();
            }
        });

        // Sự kiện khi bấm login
        btnLogin.setOnAction(event -> {
            String userName = tfUserName.getText();
            String password = pwfPassWord.getText();
            System.out.println("username đã nhập trên màn hình: " + userName + ", pass đã nhập trên màn hình: " + password);

            if (usersCompare(userName.toLowerCase(), password, vectorUsers)) {
                if(UserDAO.getCheck() == 1) {
                    setNextSceneQL();
                    ChonChucNangQLController.setCurScene(this.nextScene);
                    ChonChucNangQLController.setPreScene(getCurScene());
                } else {
                    setNextSceneNV();
                    ChonChucNangNVController.setCurScene(this.nextScene);
                    ChonChucNangNVController.setPreScene(getCurScene());
                }
                DBUtils.changeScene(nextScene, event);
            } else {
                System.out.println("Sai thông tin đăng nhập!");
                CanhBao.showAlertError("Sai thông tin đăng nhập", "Đăng nhập thất bại", "");
            }
        });
    }

    // Hàm so sánh giữa thông tin đã nhập trên màn hình và thông tin trong database
    private boolean usersCompare(String usernameTF, String passPWF, Vector<User> vectorUsers) {
        int lenVector = vectorUsers.size();
        for (int i = 0; i < lenVector; i++) {
            if (stringCompare(usernameTF, vectorUsers.get(i).getMaNv()) ==  true &&
                    stringCompare(passPWF, vectorUsers.get(i).getMatKhau()) == true) {

                if (vectorUsers.get(i).getVaiTro().trim().equals("Thu Ngân")) {
                    UserDAO.setCheck(0);
                    ChonChucNangNVController.hoVaTen = vectorUsers.get(i).getTenNv();
                    ChonChucNangNVController.maNV = vectorUsers.get(i).getMaNv();
                    ChonChucNangNVController.soDt = vectorUsers.get(i).getSoDt();
                    System.out.println("Nhân viên: " + ChonChucNangNVController.hoVaTen);
                } else {
                    UserDAO.setCheck(1);
                    ChonChucNangQLController.maQL = vectorUsers.get(i).getMaNv();
                    ChonChucNangQLController.tenQL = vectorUsers.get(i).getTenNv();
                    System.out.println("Quản lý: " + ChonChucNangQLController.tenQL);
                }
                System.out.println("Đăng nhập với tư cách là: " + vectorUsers.get(i).getVaiTro());
                return true;
            }
        }
        return false;
    }

    // Hàm so sánh 2 chuỗi
    private boolean stringCompare(String str1, String str2) {
        int l1 = str1.trim().length();
        int l2 = str2.trim().length();
        if (l1 != l2) {
            return false;
        }
        for (int i = 0; i < l1; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch - str2_ch != 0) {
                return false;
            }
        }
        return true;
    }
}
