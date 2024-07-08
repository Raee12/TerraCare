package forum;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {

    private static final String FILE_PATH = "discussions.xml";

    public static void saveDiscussions(List<Discussion> discussions) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("discussions");
            doc.appendChild(rootElement);

            for (Discussion discussion : discussions) {
                Element discussionElement = doc.createElement("discussion");

                Element titleElement = doc.createElement("title");
                titleElement.appendChild(doc.createTextNode(discussion.getTitle()));
                discussionElement.appendChild(titleElement);

                Element categoryElement = doc.createElement("category");
                categoryElement.appendChild(doc.createTextNode(discussion.getCategory()));
                discussionElement.appendChild(categoryElement);

                Element contentElement = doc.createElement("content");
                contentElement.appendChild(doc.createTextNode(discussion.getContent()));
                discussionElement.appendChild(contentElement);

                Element commentsElement = doc.createElement("comments");
                for (Comment comment : discussion.getComments()) {
                    Element commentElement = doc.createElement("comment");
                    commentElement.appendChild(doc.createTextNode(comment.getText()));
                    commentsElement.appendChild(commentElement);
                }
                discussionElement.appendChild(commentsElement);

                rootElement.appendChild(discussionElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(FILE_PATH));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Discussion> loadDiscussions() {
        List<Discussion> discussions = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return discussions; // File not found, return empty list
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList discussionNodes = doc.getElementsByTagName("discussion");

            for (int i = 0; i < discussionNodes.getLength(); i++) {
                Node node = discussionNodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String category = element.getElementsByTagName("category").item(0).getTextContent();
                    String content = element.getElementsByTagName("content").item(0).getTextContent();

                    Discussion discussion = new Discussion(title, category, content);

                    NodeList commentNodes = element.getElementsByTagName("comment");
                    for (int j = 0; j < commentNodes.getLength(); j++) {
                        String commentText = commentNodes.item(j).getTextContent();
                        discussion.getComments().add(new Comment(commentText));
                    }

                    discussions.add(discussion);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discussions;
    }
}
