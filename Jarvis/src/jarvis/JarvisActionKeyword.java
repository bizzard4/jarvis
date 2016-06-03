package jarvis;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import jarvis.data.JarvisDatabase;
import jarvis.data.JarvisEdge;
import jarvis.data.JarvisGraph;
import jarvis.data.JarvisNode;

/**
 * Class responsible for the action keyword feature.
 * @author Francois
 *
 */
public class JarvisActionKeyword {
	
	private JarvisGraph m_graph = null;
	
	public JarvisActionKeyword() {
		m_graph = new JarvisGraph();
	}
	
	/**
	 * Add a keyword associated with a command. If keyword/command pair already exist, then we do not add it.
	 * @param keyword Keyword to add/overwrite.
	 * @param command Command to execute.
	 * @return Return true if success, return null if error return false if operation was not done.
	 */
	public Boolean addKeywordCommand(String keyword, String command) {
		JarvisNode keywordNode = m_graph.getNode(getXMLForKeyword(keyword));
		if (keywordNode == null) {
			keywordNode = m_graph.addNode("KEY", getXMLForKeyword(keyword));
		}
		JarvisNode cmdNode = m_graph.getNode(getXMLForCmd(command));
		if (cmdNode == null) {
			cmdNode = m_graph.addNode("CMD", getXMLForCmd(command));
		}
		
		// In case of sql error
		if ((keywordNode == null) || (cmdNode == null)) {
			return null;
		}
		
		if (m_graph.isAdjacent(keywordNode, cmdNode)) {
			return false; // Already linked.
		} else {
			// Add edge
			JarvisEdge e = m_graph.addEdge(keywordNode, cmdNode);
			if (e != null) {
				return true;
			}
		}
		
		return null;
	}
	
	/**
	 * Execute a keyword and return result.
	 * TODO : Asych execution.
	 * @param keyword Keyword to execute.
	 * @return Boolean true if executed, false if not and null if error.
	 */
	public Boolean executeKeyword(String keyword) {
		JarvisNode n = m_graph.getNode(getXMLForKeyword(keyword));
		if (n != null) {
			List<JarvisNode> cmds = m_graph.getNeighbors(n);
			if (cmds.isEmpty()) {
				return false;
			}
			for (JarvisNode cmd : cmds) {
				try {
					Runtime.getRuntime().exec(getCmdFromXML(cmd.getData()));
				} catch (Exception e) {
					System.err.println(e.getMessage());
					return null;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Reset the system to it initial state.
	 * @return True if executed, false if not and null if error.
	 */
	public Boolean reset() {
		JarvisDatabase database = new JarvisDatabase();
		if (database.connect()) {
			try {
				String query = "DELETE FROM edges";
				PreparedStatement statement = database.getConnection().prepareStatement(query);
				statement.executeUpdate();
				query = "DELETE FROM nodes";
				statement = database.getConnection().prepareStatement(query);
				statement.executeUpdate();
				return true;
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
		return null;
	}
	
	// TODO : XML Wrapper for node data
	private String getXMLForCmd(String command) {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			Element root = doc.createElement("data");
			doc.appendChild(root);
			
			Element cmd = doc.createElement("command");
			cmd.appendChild(doc.createTextNode(command));
			root.appendChild(cmd);
					
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			writer.flush();
			return writer.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return "";
	}
	
	// TODO : XML Wrapper for node data
	private String getXMLForKeyword(String keyword) {
		try {
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element root = doc.createElement("data");
			doc.appendChild(root);
			
			Element cmd = doc.createElement("keyword");
			cmd.appendChild(doc.createTextNode(keyword));
			root.appendChild(cmd);
			
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			writer.flush();
			return writer.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return "";
	}
	
	// TODO : XML Wrapper for node data
	private String getCmdFromXML(String cmdXML) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(cmdXML)));
		return doc.getElementsByTagName("command").item(0).getTextContent();
	}
	
}
