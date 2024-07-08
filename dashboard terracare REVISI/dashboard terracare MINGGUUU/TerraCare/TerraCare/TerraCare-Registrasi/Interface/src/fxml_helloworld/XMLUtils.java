package fxml_helloworld;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLUtils {

    private static final String XML_FILE_PATH = "users.xml";

    public static boolean validateLogin(String username, String password) {
        try {
            File xmlFile = new File(XML_FILE_PATH);

            if (!xmlFile.exists()) {
                System.out.println("XML file not found: " + XML_FILE_PATH);
                return false;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            NodeList userList = root.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++) {
                Element user = (Element) userList.item(i);
                String xmlUsername = user.getElementsByTagName("username").item(0).getTextContent();
                String xmlPassword = user.getElementsByTagName("password").item(0).getTextContent();

                if (xmlUsername.equals(username) && xmlPassword.equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addUserToXML(String username, String password, String role) {
        try {
            File xmlFile = new File(XML_FILE_PATH);

            if (!xmlFile.exists()) {
                createEmptyXMLFile(xmlFile);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            Element newUser = doc.createElement("user");

            Element newUsername = doc.createElement("username");
            newUsername.appendChild(doc.createTextNode(username));
            newUser.appendChild(newUsername);

            Element newPassword = doc.createElement("password");
            newPassword.appendChild(doc.createTextNode(password));
            newUser.appendChild(newPassword);

            Element newRole = doc.createElement("role");
            newRole.appendChild(doc.createTextNode(role));
            newUser.appendChild(newRole);

            root.appendChild(newUser);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); // Optional formatting
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createEmptyXMLFile(File xmlFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("users");
            doc.appendChild(rootElement);

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
