package pengaduanMasalah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class MemberiLaporanController {

    @FXML
    private ChoiceBox<String> CBPencemaran;

    @FXML
    private TextField TFLokasi;

    @FXML
    private TextArea deskripsi;

    @FXML
    private Button buttonSent;

    @FXML
    private Button buttonReset;

    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private File xmlFile;

    @FXML
    public void initialize() {
        // Initialize ChoiceBox with options
        CBPencemaran.getItems().addAll("Pencemaran Air", "Limbah Sungai", "Tumpukan Sampah");

        // Initialize XML document setup
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            xmlFile = new File("laporan.xml");

            if (!xmlFile.exists()) {
                // Create a new XML file if it doesn't exist
                doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("laporans");
                doc.appendChild(rootElement);
                saveXMLFile();
            } else {
                // Load existing XML file
                doc = docBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleButtonSent(ActionEvent event) {
        String jenisPencemaran = CBPencemaran.getValue();
        String lokasi = TFLokasi.getText();
        String isiDeskripsi = deskripsi.getText();
        String status = "Pending"; // Default status

        addLaporanToXML(jenisPencemaran, lokasi, isiDeskripsi, status);
    }

    @FXML
    void handleButtonReset(ActionEvent event) {
        CBPencemaran.getSelectionModel().clearSelection();
        TFLokasi.clear();
        deskripsi.clear();
    }

    private void addLaporanToXML(String jenisPencemaran, String lokasi, String isiDeskripsi, String status) {
        try {
            Element rootElement = doc.getDocumentElement();

            // Create laporan element
            Element laporan = doc.createElement("laporan");
            rootElement.appendChild(laporan);

            // Create jenisPencemaran element
            Element jenisPencemaranElement = doc.createElement("jenisPencemaran");
            jenisPencemaranElement.appendChild(doc.createTextNode(jenisPencemaran));
            laporan.appendChild(jenisPencemaranElement);

            // Create lokasi element
            Element lokasiElement = doc.createElement("lokasi");
            lokasiElement.appendChild(doc.createTextNode(lokasi));
            laporan.appendChild(lokasiElement);

            // Create deskripsi element
            Element deskripsiElement = doc.createElement("deskripsi");
            deskripsiElement.appendChild(doc.createTextNode(isiDeskripsi));
            laporan.appendChild(deskripsiElement);

            // Create status element
            Element statusElement = doc.createElement("status");
            statusElement.appendChild(doc.createTextNode(status));
            laporan.appendChild(statusElement);

            saveXMLFile(); // Save the updated XML file

            System.out.println("Laporan berhasil disimpan ke dalam file XML.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveXMLFile() {
        try {
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
