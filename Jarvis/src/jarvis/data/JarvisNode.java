package jarvis.data;

import java.util.Date;

/**
 * Class representing a data node.
 * @see JarvisGraph
 * @author Francois
 *
 */
public class JarvisNode {
	
	private int m_nodeId;
	private String m_data;
	private String m_type;
	private Date m_creation;
	private Date m_modified;
	
	/**
	 * Constructor.
	 * @param id Node id.
	 * @param type Node type.
	 * @param data Node data.
	 */
	public JarvisNode(int id, String type, String data, Date creation, Date modified) {
		m_nodeId = id;
		m_data = data;
		m_type = type;
		m_creation = creation;
		m_modified = modified;
	}
	
	public int getNodeId() {
		return m_nodeId;
	}
	
	public String getData() {
		return m_data;
	}
	
	public String getType() {
		return m_type;
	}
	
	public Date getCreation() {
		return m_creation;
	}
	
	public Date getModified() {
		return m_modified;
	}
}
