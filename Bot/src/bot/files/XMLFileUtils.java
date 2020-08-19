/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.files;

/**
 *
 * @author HumanoideFilms
 */
import bot.logic.Rule;
import bot.logic.RulesList;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * clase para guardar archivos y leerlos en formato xml
 * los archivos xml estan mejor organizados que los txts
 * @author HumanoideFilms
 */
public class XMLFileUtils {
    
    public static final String xmlFilePath = "rulesFile.xml";
    
    /**
     * guarda la lista de reglas en un xml
     */
    public static void saveRules() {
        
        try {
            
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("rules");
            document.appendChild(root);
            for (Rule r : RulesList.ruleList) {
                Element rule = document.createElement("rule");
                root.appendChild(rule);
                Element strings = document.createElement("w");
                /*******************id***************************/
                Element id = document.createElement("id");
                rule.appendChild(id);
                id.appendChild(document.createTextNode(""+r.id));
                /*******************prev rules***************************/
                Element erule = document.createElement("erule");
                rule.appendChild(erule);
                erule.appendChild(document.createTextNode(""+r.expRules));
                
                rule.appendChild(strings);
                if(r.er!=null){
                    Element er = document.createElement("er");
                    rule.appendChild(er);
                    er.appendChild(document.createTextNode(r.er));
                }
                else{
                    Element er = document.createElement("er");
                    rule.appendChild(er);
                    er.appendChild(document.createTextNode("µ"));
                }
                strings.appendChild(document.createTextNode(r.printReact()));
                for (String response : r.responses) {
                    Element resp = document.createElement("r");
                    rule.appendChild(resp);
                    resp.appendChild(document.createTextNode(response));
                }
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);
            
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        
    }
    
    /**
     * le el archivo de reglas y las carga en las clases correspondientes a las reglas
     */
    public static void readRules() {
        try {
            RulesList.initList();
            File inputFile = new File("rulesFile.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("rule");
            //System.out.println("size :" + nList.);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Rule r = new Rule();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ew = (Element) nNode;
                    Element er = (Element) nNode;
                    r.setid(Integer.parseInt(ew.getElementsByTagName("id")
                            .item(0)
                            .getTextContent()));
                    r.addReact(ew.getElementsByTagName("w")
                            .item(0)
                            .getTextContent());
                    String exr=ew.getElementsByTagName("er")
                            .item(0)
                            .getTextContent();
                    if(exr.length()>0){
                        r.setER(exr);
                    }
                    
 
                    for (int i = 0; i < er.getElementsByTagName("r").getLength(); i++) {
                        r.addResponse(ew.getElementsByTagName("r")
                                .item(i)
                                .getTextContent());
                    }
                }
                RulesList.addRule(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
