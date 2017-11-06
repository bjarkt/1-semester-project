package worldofzuul21;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class XMLUtilities {
    private String filename;

    public XMLUtilities(String filename) {
        this.filename = filename;
    }

    public boolean doesFileExist() {
        File file = new File(filename);
        return file.exists();
    }

    public Map<String, String> load() {
        Map<String, String> map = new LinkedHashMap<>();
        StringBuilder xml = new StringBuilder();
        try {
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                xml.append(sc.nextLine());
            }
            sc.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.toString().getBytes()));
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                map.put(nodeList.item(i).getNodeName(), nodeList.item(i).getChildNodes().item(0).getNodeValue());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void save(Map<String, String> map) {
        StringBuilder buffer = new StringBuilder();

        buffer.append("<gameData>\n");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            buffer.append("<").append(entry.getKey()).append(">");
            buffer.append(entry.getValue());
            buffer.append("</").append(entry.getKey()).append(">\n");
        }
        buffer.append("</gameData>\n");

        File file = new File(filename);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(buffer.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
