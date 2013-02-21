/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package activity2event;

/**
 *
 * @author flag
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.w3c.dom.Element;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



/**
 * Make up and write an XML document, using DOM UPDATED FOR JAXP.
 *
 * @author flag
 * 
 */
public class DocWriteDOM {

    public DocWriteDOM() {
                
    }

    protected String createEventXml(Element xml, String name, String sufix, String directory, String dirOut) {
        try {
            if(dirOut==null) {
                dirOut=directory;
            }
           
            // Load the XML event to file
            TransformerFactory transFact = TransformerFactory.newInstance();

            // add header and indentation

            transFact.setAttribute("indent-number", new Integer(3));
            Transformer trans = transFact.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            // Make the conversion
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            DOMSource domSource = new DOMSource(xml);
            trans.transform(domSource, sr);
           
            try {
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(
                        new FileOutputStream(dirOut+name + "_" + sufix + ".xml"), "UTF-8"));
                // create text file 
                // write the tree on file
                writer.println(sw.toString());

                // close file
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dirOut;
    }
}