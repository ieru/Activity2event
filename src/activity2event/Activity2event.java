/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package activity2event;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author flag Main Class. Input.
 */
public class Activity2event {

    /**
     * @param args the command line arguments First param= input Path Second=
     * output Path. This is optative
     */
    public static void main(String[] args) {
        try {

            String dirPath = "";
            String dirOut = null;
            if (args.length != 0) {

                if (!"--?".equals(args[0])) {
                    dirPath = args[0];
                    if (args.length == 2) {
                        dirOut = args[1];
                    }


                    String name = "";

                    File dir = new File(dirPath);

                    DocWriteDOM dwrite = new DocWriteDOM();
                    FileExtensionFilter filter = new FileExtensionFilter(".xml");
                    File[] xml = dir.listFiles(filter);

                    // String[] ficheros = dir.list(filter);
                    if (xml == null) {
                       
                       help("There is not valid xml in the selected folder");
                    } else {
                        for (File file : xml) {
                            name = file.getName();
                            name = name.substring(0, name.length() - 4);
                            File fXmlFile = new File(file.getAbsolutePath());
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            dbFactory.setNamespaceAware(true);
                            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                            Document doc = dBuilder.parse(fXmlFile);
                            doc.getDocumentElement().normalize();
                            xmlWriter(doc, dwrite, name, dirPath, dirOut, "tag");
                            xmlWriter(doc, dwrite, name, dirPath, dirOut, "rating");
                            xmlWriter(doc, dwrite, name, dirPath, dirOut, "comment");

                        }
                        if (args.length == 1) {
                            System.out.println("");
                            System.out.println("The result files are on: " + dirPath);
                             System.out.println("");
                        } else if (args.length == 2) {
                             System.out.println("");
                            System.out.println("The result files are on: " + dirOut);
                             System.out.println("");
                        }
                    }
                } else {
                     help("");

                }
            } else {
                
                 help("XML directory required, please type java -jar activity2event.jar 'xmls directory path'");

            }
        } catch (Exception e) {
           // help("Error. See the message at the end");
            help("Error: "+e.getMessage());
        }
    }

    /**
     * This function obtain the event from activity XML and call to even
     * generator
     *
     * @param doc The Xml event
     * @param dwrite The writer of the file
     * @param name The name of the file
     * @param dirPath The input path
     * @param dirOut The output Path
     * @param tag The type of event tag rating comments
     */
    static void xmlWriter(Document doc, DocWriteDOM dwrite, String name, String dirPath, String dirOut, String tag) {
        NodeList nList = doc.getElementsByTagName("ods:" + tag);
        String id="";

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                id=eElement.getElementsByTagName("identifier").item(0).getTextContent();
                dwrite.createEventXml(eElement, name + "_" + tag, id, dirPath, dirOut);
            }
        }
    }
/**
 * Print the help message
 * @param message 
 */
    static void help(String message) {
        System.out.println("");
        System.out.println("-------------------------------------------");
        System.out.println(message);
        System.out.println("");
        System.out.println("Activity2event");
        System.out.println("-------------------");
        System.out.println("");
        System.out.println("java -jar activity2event.jar <<XML_INPUT_PATH>> [<<XML OUTPUT PATH>>]");
        System.out.println("");
        System.out.println("PARAMS");
        System.out.println("XML INPUT PATH = the activity based xml folder");
        System.out.println("XML OUTPUT PATH = the event based xml folder. By default (if null) the same that INPUT FOLDER");
         System.out.println("-------------------------------------------");
         System.out.println("");
    }
}
