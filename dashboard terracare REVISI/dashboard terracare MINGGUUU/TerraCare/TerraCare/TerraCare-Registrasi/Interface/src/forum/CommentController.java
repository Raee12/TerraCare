package forum;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;

public class CommentController {

    @FXML
    private TextArea commentArea;

    @FXML
    private ListView<String> commentListView;

    @FXML
    private Button deleteButton;

    private Discussion discussion;

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
        updateCommentList();
    }

    private void updateCommentList() {
        commentListView.getItems().clear();
        for (Comment comment : discussion.getComments()) {
            commentListView.getItems().add(comment.getText());
        }
        deleteButton.setDisable(true);
    }

    @FXML
    private void handleAddComment() {
        String commentText = commentArea.getText();
        if (!commentText.trim().isEmpty()) {
            Comment comment = new Comment(commentText);
            discussion.getComments().add(comment);
            updateCommentList();
            commentArea.clear();
            saveComments();
        }
    }

    @FXML
    private void handleDeleteComment() {
        int selectedIndex = commentListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            discussion.getComments().remove(selectedIndex);
            updateCommentList();
            saveComments();
        }
    }

    private void saveComments() {
        List<Discussion> discussions = XMLUtil.loadDiscussions();
        for (Discussion d : discussions) {
            if (d.getTitle().equals(discussion.getTitle())) {
                d.setComments(discussion.getComments());
                break;
            }
        }
        XMLUtil.saveDiscussions(discussions);
    }

    @FXML
    private void initialize() {
        commentListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });
    }
}
