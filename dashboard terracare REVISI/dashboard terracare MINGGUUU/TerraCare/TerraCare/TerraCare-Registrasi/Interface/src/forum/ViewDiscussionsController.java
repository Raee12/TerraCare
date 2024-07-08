package forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ViewDiscussionsController {

    @FXML
    private TableView<Discussion> tableView;

    @FXML
    private TableColumn<Discussion, String> titleColumn;

    @FXML
    private TableColumn<Discussion, String> categoryColumn;

    @FXML
    private TableColumn<Discussion, String> contentColumn;

    private ObservableList<Discussion> discussions;

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        discussions = FXCollections.observableArrayList(MainController.getDiscussions());
        tableView.setItems(discussions);

        tableView.setOnMouseClicked(this::handleDiscussionClick);
    }

    private void handleDiscussionClick(MouseEvent event) {
        if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
            Discussion selectedDiscussion = tableView.getSelectionModel().getSelectedItem();
            openCommentWindow(selectedDiscussion);
        }
    }

    private void openCommentWindow(Discussion discussion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentView.fxml"));
            Parent root = loader.load();

            CommentController controller = loader.getController();
            controller.setDiscussion(discussion);

            Stage stage = new Stage();
            stage.setTitle("Komentar Diskusi");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToMenu() throws IOException {
        Stage stage = (Stage) tableView.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleDeleteDisk(ActionEvent event) {
        Discussion selectedDiscussion = tableView.getSelectionModel().getSelectedItem();

        if (selectedDiscussion != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Penghapusan");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda yakin ingin menghapus diskusi ini?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                discussions.remove(selectedDiscussion);
                MainController.getDiscussions().remove(selectedDiscussion);
                MainController.getDiscussions();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Pilih diskusi yang akan dihapus.");
            alert.showAndWait();
        }
    }
}
