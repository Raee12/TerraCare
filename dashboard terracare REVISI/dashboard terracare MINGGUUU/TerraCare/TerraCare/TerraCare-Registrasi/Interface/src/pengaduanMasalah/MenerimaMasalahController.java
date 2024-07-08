package pengaduanMasalah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MenerimaMasalahController implements Initializable {

    @FXML
    private TableView<Laporan> tableView;

    @FXML
    private TableColumn<Laporan, String> tcJenisPencemaran;

    @FXML
    private TableColumn<Laporan, String> tcLokasi;

    @FXML
    private TableColumn<Laporan, String> tcDeskripsi;

    @FXML
    private TableColumn<Laporan, String> tcStatus;

    @FXML
    private TableColumn<Laporan, Void> tcActions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcJenisPencemaran.setCellValueFactory(new PropertyValueFactory<>("jenisPencemaran"));
        tcLokasi.setCellValueFactory(new PropertyValueFactory<>("lokasi"));
        tcDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadPengaduanDataFromXML();
        addActionButtonsToTable();
    }

    private void loadPengaduanDataFromXML() {
        try {
            File xmlFile = new File("laporan.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                // Clear existing items in the tableView
                tableView.getItems().clear();

                NodeList nodeList = doc.getElementsByTagName("laporan");

                for (int temp = 0; temp < nodeList.getLength(); temp++) {
                    Node nNode = nodeList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) nNode;
                        String jenisPencemaran = element.getElementsByTagName("jenisPencemaran").item(0).getTextContent();
                        String lokasi = element.getElementsByTagName("lokasi").item(0).getTextContent();
                        String deskripsi = element.getElementsByTagName("deskripsi").item(0).getTextContent();
                        String status = element.getElementsByTagName("status").item(0).getTextContent();

                        Laporan laporan = new Laporan(jenisPencemaran, lokasi, deskripsi, status);
                        tableView.getItems().add(laporan);
                    }
                }
            } else {
                System.out.println("File laporan.xml not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Laporan, Void>, TableCell<Laporan, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Laporan, Void> call(final TableColumn<Laporan, Void> param) {
                final TableCell<Laporan, Void> cell = new TableCell<>() {

                    private final Button btnAccepted = new Button("Accepted");
                    private final Button btnRejected = new Button("Rejected");

                    {
                        btnAccepted.setOnAction((ActionEvent event) -> {
                            Laporan laporan = getTableView().getItems().get(getIndex());
                            updateStatus(laporan, "Accepted");
                        });
                        btnRejected.setOnAction((ActionEvent event) -> {
                            Laporan laporan = getTableView().getItems().get(getIndex());
                            updateStatus(laporan, "Rejected");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(btnAccepted, btnRejected);
                            hbox.setSpacing(10);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };

        tcActions.setCellFactory(cellFactory);
    }

    private void updateStatus(Laporan laporan, String newStatus) {
        laporan.setStatus(newStatus);
        saveUpdatedStatusToXML(laporan);
        tableView.refresh();
    }

    private void saveUpdatedStatusToXML(Laporan laporan) {
        try {
            File xmlFile = new File("laporan.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("laporan");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String jenisPencemaran = element.getElementsByTagName("jenisPencemaran").item(0).getTextContent();
                    String lokasi = element.getElementsByTagName("lokasi").item(0).getTextContent();
                    String deskripsi = element.getElementsByTagName("deskripsi").item(0).getTextContent();

                    if (jenisPencemaran.equals(laporan.getJenisPencemaran()) && lokasi.equals(laporan.getLokasi())
                            && deskripsi.equals(laporan.getDeskripsi())) {
                        element.getElementsByTagName("status").item(0).setTextContent(laporan.getStatus());
                        break;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
