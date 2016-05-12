package jarvis.data;

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
	
	/**
	 * Constructor.
	 * @param id Node id.
	 * @param type Node type.
	 * @param data Node data.
	 */
	public JarvisNode(int id, String type, String data) {
		m_nodeId = id;
		m_data = data;
		m_type = type;
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
}
