package addreward;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

public class Reward {
    private String username;
    private String rewardLevel;
    private String statusReward;
    private SimpleObjectProperty<Button> changeLevelButton;
    private SimpleObjectProperty<Button> changeStatusButton;

    public Reward(String username, String rewardLevel, String statusReward) {
        this.username = username;
        this.rewardLevel = rewardLevel;
        this.statusReward = statusReward;
        this.changeLevelButton = new SimpleObjectProperty<>(new Button("Change Level"));
        this.changeStatusButton = new SimpleObjectProperty<>(new Button("Change Status"));
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(String rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    public String getStatusReward() {
        return statusReward;
    }

    public void setStatusReward(String statusReward) {
        this.statusReward = statusReward;
    }

    public Button getChangeLevelButton() {
        return changeLevelButton.get();
    }

    public SimpleObjectProperty<Button> changeLevelButtonProperty() {
        return changeLevelButton;
    }

    public void setChangeLevelButton(Button changeLevelButton) {
        this.changeLevelButton.set(changeLevelButton);
    }

    public Button getChangeStatusButton() {
        return changeStatusButton.get();
    }

    public SimpleObjectProperty<Button> changeStatusButtonProperty() {
        return changeStatusButton;
    }

    public void setChangeStatusButton(Button changeStatusButton) {
        this.changeStatusButton.set(changeStatusButton);
    }
}
