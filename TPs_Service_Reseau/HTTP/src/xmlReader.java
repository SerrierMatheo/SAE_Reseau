import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class xmlReader {

    final static String  root = "Tps_Service_Reseau/HTTP/src/myweb.conf";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(root);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);

        document.getDocumentElement().normalize();
        System.out.println("Root Element :" + document.getDocumentElement().getNodeName() + "\n");
        NodeList nList = document.getElementsByTagName("webconf");
        System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element : " + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Port: " + eElement.getElementsByTagName("port").item(0).getTextContent());
                System.out.println("Root: " + eElement.getElementsByTagName("root").item(0).getTextContent());
                System.out.println("Index: " + eElement.getElementsByTagName("index").item(0).getTextContent());
                System.out.println("Accept: " + eElement.getElementsByTagName("accept").item(0).getTextContent());
            }
        }
    }





    public xmlReader() throws ParserConfigurationException, IOException, SAXException {
    }

}

