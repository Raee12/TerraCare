package addreward;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormRewardApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Memuat file FXML FormRewardUser.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/addreward/FormRewardUser.fxml"));
        primaryStage.setTitle("Reward Claim");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
