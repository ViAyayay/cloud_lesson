import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("sample.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Cloud Storage");
        primaryStage.setScene(new Scene((root)));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Application.launch(args);
    }
}
