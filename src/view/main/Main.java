package view.main;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //--module-path "C:\Program Files\Java\javafx-sdk-18.0.1\lib" --add-modules javafx.controls,javafx.fxml

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/view/fxml/Login.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Quản lý siêu thị!"); // set title
        Scene scene = new Scene(root, 1000, 600);

//        LoginController controller = fxmlLoader.getController();
//        controller.setCurScene(scene);
        LoginController controller = fxmlLoader.getController();
        controller.setCurScene(scene); 

        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // cố định màn hình
        primaryStage.show();
    }
}
