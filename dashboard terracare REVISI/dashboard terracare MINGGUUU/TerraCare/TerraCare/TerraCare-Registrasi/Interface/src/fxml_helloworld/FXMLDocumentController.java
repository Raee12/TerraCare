package fxml_helloworld;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane LoginKANAN;

    @FXML
    private AnchorPane LoginKIRI;

    @FXML
    private PasswordField PW;

    @FXML
    private Button SignIN;

    @FXML
    private Button SignUP;

    @FXML
    private TextField UserName;

    @FXML
    private PasswordField confirmPW;

    @FXML
    private Label label;

    @FXML
    private TextField registEMAIL;

    @FXML
    private TextField registNAMA;

    @FXML
    private PasswordField registPW;

    @FXML
    private Button registSIGNIN;

    @FXML
    private Button registSIGNUP;

    @FXML
    private RadioButton userRadio;

    @FXML
    private RadioButton adminRadio;

    @FXML
    private RadioButton userRadioRegist;  // Added for registration
    
    @FXML
    private RadioButton adminRadioRegist; // Added for registration

    @FXML
    private ToggleGroup roleGroup;

    @FXML
    private ToggleGroup registgroup; // Added for registration

    @FXML
    private ToggleButton buttonartikel1;

    @FXML
    private ToggleButton buttonartikel2;

    @FXML
    private ToggleButton buttonartikel3;

    @FXML
    private ToggleButton buttonartikel4;

    @FXML
    void handlebuttonartikel1(ActionEvent event) {
        try {
            Parent rewardPage = FXMLLoader.load(getClass().getResource("artikel1.fxml"));
            Scene rewardScene = new Scene(rewardPage);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(rewardScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlebuttonartikel2(ActionEvent event) {
        try {
            Parent rewardPage = FXMLLoader.load(getClass().getResource("artikel2.fxml"));
            Scene rewardScene = new Scene(rewardPage);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(rewardScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlebuttonartikel3(ActionEvent event) {
        try {
            Parent rewardPage = FXMLLoader.load(getClass().getResource("artikel3.fxml"));
            Scene rewardScene = new Scene(rewardPage);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(rewardScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlebuttonartikel4(ActionEvent event) {
        try {
            Parent rewardPage = FXMLLoader.load(getClass().getResource("artikel4.fxml"));
            Scene rewardScene = new Scene(rewardPage);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(rewardScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
private void handleButtonSignIN(ActionEvent event) {
    String username = UserName.getText();
    String password = PW.getText();
    RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();
    String role = selectedRole != null ? selectedRole.getText() : "";

    // Debug: Print the inputs
    System.out.println("Username: " + username);
    System.out.println("Password: " + password);
    System.out.println("Role: " + role);

    // Validasi input
    if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all details");
        return;
    }

    // Validasi login
    boolean loginSuccessful = XMLUtils.validateLogin(username, password);
    if (loginSuccessful) {
        showAlert(Alert.AlertType.INFORMATION, "Login Successful!", "Welcome " + username);

        if (role.equals("User Biasa")) {
            loadPage(event, "/fxml_helloworld/Home.fxml", "User Home Page");
        } else if (role.equals("Admin")) {
            loadPage(event, "/fxml_helloworld/adminHome.fxml", "Admin Home Page");
        }
    } else {
        showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
    }
}


    @FXML
    private void handleButtonSignUP(ActionEvent event) {
        loadPage(event, "buatAkunController.fxml", "Registration Form");
    }

    @FXML
    private void handleRegistIN(ActionEvent event) {
        loadPage(event, "FXMLDocument.fxml", "Login Form");
    }

    @FXML
    private void handleRegistUP(ActionEvent event) {
        String nama = registNAMA.getText();
        String passwordRegist = registPW.getText();
        String email = registEMAIL.getText();
        RadioButton selectedRole = (RadioButton) registgroup.getSelectedToggle();
        String role = selectedRole != null ? selectedRole.getText() : "";

        if (nama.isEmpty() || passwordRegist.isEmpty() || email.isEmpty() || role.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all details");
            return;
        }

        XMLUtils.addUserToXML(nama, passwordRegist, role);

        showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "Welcome " + nama);
        loadPage(event, "FXMLDocument.fxml", "Login Form");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /*
     * Halaman User
     */

    @FXML
    private void handleHomeButton(ActionEvent event) {
        loadPage(event, "/fxml_helloworld/Home.fxml", null);
    }

    @FXML
    private void handleMessagesButton(ActionEvent event) {
        loadPage(event, "/forum/MainView.fxml", null);
    }

    @FXML
    private void handleReportButton(ActionEvent event) {
        loadPage(event, "/pengaduanMasalah/memberilaporan.fxml", "melaporkan msalah");
    }

    @FXML
    private void handleRewardButton(ActionEvent event) {
        loadPage(event, "/addreward/FormRewardUser.fxml", "formReward");
    }

    @FXML
    private void handleSettingsButton(ActionEvent event) {
        loadPage(event, "SettingsPage.fxml", null);
    }

    @FXML
    private void HomeLogOutButton(ActionEvent event) {
        loadPage(event, "FXMLDocument.fxml", null);
    }


    /*
     * Halaman Admin
     */

    @FXML
    private void adminHomeButton(ActionEvent event) {
        loadPage(event, "/fxml_helloworld/Home.fxml", null);
    }

    @FXML
    private void adminRewardButton(ActionEvent event) {
        loadPage(event, "/addreward/RewardAdmin.fxml", "formReward");
    }

    @FXML
    void adminMmasalah(ActionEvent event) {
        loadPage(event, "/pengaduanMasalah/menerimamasalah.fxml", "formReward");
    }


    @FXML
   private void adminSettingsButton(ActionEvent event) {
        loadPage(event, null, null);
    }

    @FXML
    private void AdminLogOutButton(ActionEvent event) {
        loadPage(event, "FXMLDocument.fxml", null);
    }

    private void loadPage(ActionEvent event, String page, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing...");
    
        // Check if userRadio and adminRadio are not null for login
        if (userRadio == null || adminRadio == null) {
            System.out.println("userRadio or adminRadio is null. Please check your FXML file for login form.");
            return;
        }
    
        // Initialize login role group
        roleGroup = new ToggleGroup();
        userRadio.setToggleGroup(roleGroup);
        adminRadio.setToggleGroup(roleGroup);
        userRadio.setSelected(true);
    
        // Check if userRadioRegist and adminRadioRegist are not null for registration
        if (userRadioRegist == null || adminRadioRegist == null) {
            System.out.println("userRadioRegist or adminRadioRegist is null. Please check your FXML file for registration form.");
            return;
        }
    
        // Initialize registration role group
        registgroup = new ToggleGroup();
        userRadioRegist.setToggleGroup(registgroup);
        adminRadioRegist.setToggleGroup(registgroup);
        userRadioRegist.setSelected(true);
    }
}