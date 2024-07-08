package addreward;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class FormRewardController {

    @FXML
    private TextField usernameField;

    @FXML
    private void handleClaimReward(ActionEvent event) {
        String username = usernameField.getText();
        if (username == null || username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Username cannot be empty!");
            alert.showAndWait();
            return;
        }

        try {
            File file = new File("rewards.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (file.exists()) {
                doc = dBuilder.parse(file);
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("Rewards");
                doc.appendChild(rootElement);
            }

            Element rootElement = doc.getDocumentElement();
            Element rewardElement = doc.createElement("Reward");

            Element usernameElement = doc.createElement("Username");
            usernameElement.appendChild(doc.createTextNode(username));
            rewardElement.appendChild(usernameElement);

            Element statusElement = doc.createElement("StatusReward");
            statusElement.appendChild(doc.createTextNode("none"));
            rewardElement.appendChild(statusElement);

            rootElement.appendChild(rewardElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Username has been added to the reward list!");
            alert.showAndWait();

            usernameField.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
