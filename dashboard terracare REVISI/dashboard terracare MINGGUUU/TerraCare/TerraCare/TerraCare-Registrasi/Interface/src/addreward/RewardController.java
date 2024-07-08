package addreward;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class RewardController {

    @FXML
    private TableView<Reward> tabeldata;
    @FXML
    private TableColumn<Reward, String> namaColumn;
    @FXML
    private TableColumn<Reward, String> statusColumn;
    @FXML
    private TableColumn<Reward, String> rewardLevelColumn;
    @FXML
    private TableColumn<Reward, Void> actionColumn;
    @FXML
    private TableColumn<Reward, Void> verifyColumn;

    private ObservableList<Reward> rewardData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        rewardLevelColumn.setCellValueFactory(new PropertyValueFactory<>("rewardLevel"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusReward"));

        // Load data and set table items
        loadRewardData();
        tabeldata.setItems(rewardData);

        // Set action column cell factory
        if (actionColumn != null) {
            actionColumn.setCellFactory(createButtonCellFactory());
        }

        // Set verify column cell factory
        if (verifyColumn != null) {
            verifyColumn.setCellFactory(createVerifyButtonCellFactory());
        }
    }

    private void loadRewardData() {
        try {
            File file = new File("rewards.xml");
            if (file.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("Reward");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        String username = element.getElementsByTagName("Username").item(0).getTextContent();
                        String statusReward = element.getElementsByTagName("StatusReward").item(0).getTextContent();
                        String rewardLevel = "none";

                        // Check if RewardLevel element exists
                        if (element.getElementsByTagName("RewardLevel").getLength() > 0) {
                            rewardLevel = element.getElementsByTagName("RewardLevel").item(0).getTextContent();
                        }

                        rewardData.add(new Reward(username, statusReward, rewardLevel));
                    }
                }

                tabeldata.setItems(rewardData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Callback<TableColumn<Reward, Void>, TableCell<Reward, Void>> createButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Reward, Void> call(final TableColumn<Reward, Void> param) {
                final TableCell<Reward, Void> cell = new TableCell<>() {
                    private final Button bronzeButton = new Button("Bronze");
                    private final Button silverButton = new Button("Silver");
                    private final Button goldButton = new Button("Gold");
                    private final HBox buttons = new HBox(bronzeButton, silverButton, goldButton);

                    {
                        bronzeButton.setOnAction(event -> {
                            Reward reward = getTableView().getItems().get(getIndex());
                            updateSelectedRewardLevel(reward, "Bronze");
                        });
                        silverButton.setOnAction(event -> {
                            Reward reward = getTableView().getItems().get(getIndex());
                            updateSelectedRewardLevel(reward, "Silver");
                        });
                        goldButton.setOnAction(event -> {
                            Reward reward = getTableView().getItems().get(getIndex());
                            updateSelectedRewardLevel(reward, "Gold");
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private Callback<TableColumn<Reward, Void>, TableCell<Reward, Void>> createVerifyButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Reward, Void> call(final TableColumn<Reward, Void> param) {
                final TableCell<Reward, Void> cell = new TableCell<>() {
                    private final Button verifyButton = new Button("Verifikasi");
                    private final Button unverifyButton = new Button("Tidak Verifikasi");
                    private final HBox verifyButtons = new HBox(verifyButton, unverifyButton);

                    {
                        verifyButton.setOnAction(event -> {
                            Reward reward = getTableView().getItems().get(getIndex());
                            updateSelectedStatus(reward, "Terverifikasi");
                        });
                        unverifyButton.setOnAction(event -> {
                            Reward reward = getTableView().getItems().get(getIndex());
                            updateSelectedStatus(reward, "Tidak Terverifikasi");
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(verifyButtons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    private void updateSelectedRewardLevel(Reward reward, String level) {
        reward.setRewardLevel(level);
        updateRewardInXML(reward);
        tabeldata.refresh();
    }

    private void updateSelectedStatus(Reward reward, String status) {
        reward.setStatusReward(status);
        updateRewardInXML(reward);
        tabeldata.refresh();
    }

    private void updateRewardInXML(Reward reward) {
        try {
            File file = new File("rewards.xml");
            if (file.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("Reward");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        String username = element.getElementsByTagName("Username").item(0).getTextContent();
                        if (username.equals(reward.getUsername())) {
                            if (element.getElementsByTagName("RewardLevel").getLength() > 0) {
                                element.getElementsByTagName("RewardLevel").item(0).setTextContent(reward.getRewardLevel());
                            } else {
                                Element rewardLevelElement = doc.createElement("RewardLevel");
                                rewardLevelElement.setTextContent(reward.getRewardLevel());
                                element.appendChild(rewardLevelElement);
                            }

                            if (element.getElementsByTagName("StatusReward").getLength() > 0) {
                                element.getElementsByTagName("StatusReward").item(0).setTextContent(reward.getStatusReward());
                            } else {
                                Element statusRewardElement = doc.createElement("StatusReward");
                                statusRewardElement.setTextContent(reward.getStatusReward());
                                element.appendChild(statusRewardElement);
                            }

                            break;
                        }
                    }
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void launch(String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'launch'");
    }

}
