package forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    private static ObservableList<Discussion> discussions = FXCollections.observableArrayList();

    public static ObservableList<Discussion> getDiscussions() {
        return discussions;
    }

    @FXML
    private void handleCreateDiscussion() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateView.fxml"));
        Parent root = loader.load();

        CreateDiscussionController controller = loader.getController();
        Stage stage = new Stage();
        controller.setStage(stage);

        stage.setTitle("Buat Diskusi Baru");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleViewDiscussions() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Lihat Diskusi");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
