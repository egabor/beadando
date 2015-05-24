package Utilities;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import Models.Marker;
import Views.GameScene;

/**
 * Az XML osztály segítségével tudjuk menteni a játékállást egy xml fájlba, illetve az elmenett játékot innen tudjuk visszatölteni.
 * @author gaboreszenyi
 *
 */
public class XML {
	
	
	/**
     * A {@code logger} változó segítségével fogunk tudni naplózni.
     */
    private static Logger logger = LoggerFactory.getLogger(XML.class);
	
	/**
	 * A {@code loadGame()} metódus végzi el elmentett játékállás betöltését az xml fájlból.
	 * @return Az elmetett játék állapota.
	 */
	public static ArrayList<Marker> loadGame() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//dbf.setValidating(true);
			dbf.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace",false);
			DocumentBuilder parser = dbf.newDocumentBuilder();
			ArrayList<Marker> markers = new ArrayList<Marker>();
			
			String filePath = "save.xml";

			File f = new File(filePath);
			if(!f.exists()) { 
				logger.warn("Nem létezik az xml fájl, ahonnan egy előző mentést be lehetne tölteni.");
				return markers; 
			} 

			
			Document doc = parser.parse(filePath);
			
			NodeList elements = doc.getElementsByTagName("marker");
			for (int i = 0; i < elements.getLength(); i++) {
				Element element = (Element) elements.item(i);
				String row = element.getAttribute("row");
				String column = element.getAttribute("column");
				String markerState = element.getAttribute("markerState");
				Marker marker = new Marker(Integer.parseInt(row), Integer.parseInt(column), Integer.parseInt(markerState));
				markers.add(marker);
			}
			return markers;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return new ArrayList<Marker>();
	}
	
	/**
	 * A {@code saveGame()} metódus végzi el az xml fájlba való mentést.
	 * @param markers A játék jelenlegi állapotát reprezentáló markerek.
	 */
	public static void saveGame(ArrayList<Marker> markers) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//dbf.setValidating(true);
			dbf.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);
			DocumentBuilder parser = dbf.newDocumentBuilder();
			
			
			Document docToSave = parser.newDocument();
			Element markersElement = docToSave.createElement("markers");
			
			
			for (int i = 0; i < markers.size(); i++) {
				Marker marker = markers.get(i);
				Element markerElement = docToSave.createElement("marker");
				markerElement.setAttribute("id", new Integer(i).toString());
				markerElement.setAttribute("row", new Integer(marker.row).toString());
				markerElement.setAttribute("column", new Integer(marker.column).toString());
				markerElement.setAttribute("markerState", new Integer(marker.markerState).toString());

				markersElement.appendChild(markerElement);
			}	
			
			docToSave.appendChild(markersElement);

			DOMImplementationLS dils = (DOMImplementationLS) parser.getDOMImplementation();
			LSSerializer lsser = dils.createLSSerializer();
			LSOutput lsout = dils.createLSOutput();
			lsout.setCharacterStream(new FileWriter("save.xml"));
			lsout.setEncoding("ISO-8859-2");
			lsser.write(docToSave, lsout);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
