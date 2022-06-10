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

    private String port;
    private String racine;
    private String index;
    private String accept;

    final static String  root = "Tps_Service_Reseau/HTTP/src/myweb.conf";

    public xmlReader(){
        this.port="80";
        this.racine="./doc/";
        this.index="true";
        this.accept="localhost";
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(root);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);

        xmlReader xmlReader = new xmlReader();

        document.getDocumentElement().normalize();
        System.out.println("Root Element :" + document.getDocumentElement().getNodeName() + "\n");
        NodeList nList = document.getElementsByTagName("webconf");
        System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element : " + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                xmlReader.setPort(eElement.getElementsByTagName("port").item(0).getTextContent());
                System.out.println("Port: " + xmlReader.getPort());
                xmlReader.setRacine(eElement.getElementsByTagName("root").item(0).getTextContent());
                System.out.println("Root: " + xmlReader.getRacine());
                xmlReader.setIndex(eElement.getElementsByTagName("index").item(0).getTextContent());
                System.out.println("Index: " + xmlReader.getIndex());
                xmlReader.setAccept(eElement.getElementsByTagName("accept").item(0).getTextContent());
                System.out.println("Accept: " + xmlReader.getAccept());
            }
        }
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRacine() {
        return racine;
    }

    public void setRacine(String racine) {
        this.racine = racine;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }
}

