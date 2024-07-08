package forum;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class CreateDiscussionController {

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private TextArea contentArea;

    private Stage stage;

    private List<String> categories;

    public void initialize() {
        categories = new ArrayList<>();
        categories.add("General");
        categories.add("Pencemaran limbah");
        categories.add("Pencemaran Lingkungan");
        categories.add("Others");

        categoryBox.setItems(FXCollections.observableArrayList(categories));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleSubmit() {
        String title = titleField.getText();
        String category = categoryBox.getValue();
        String content = contentArea.getText();

        Discussion discussion = new Discussion(title, category, content);

        // Gunakan XMLUtil untuk menyimpan diskusi
        List<Discussion> discussions = XMLUtil.loadDiscussions();
        discussions.add(discussion);
        XMLUtil.saveDiscussions(discussions);
        MainController.getDiscussions().add(discussion);

        stage.close();
    }
}
