package forum;

import java.util.ArrayList;
import java.util.List;

public class Discussion {
    private String title;
    private String category;
    private String content;
    private List<Comment> comments = new ArrayList<>();

    public Discussion() {}

    public Discussion(String title, String category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
