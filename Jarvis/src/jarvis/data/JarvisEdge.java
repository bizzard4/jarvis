package jarvis.data;

import java.util.Date;

/**
 * Represent a edge of the graph.
 * @see JarvisGraph
 * @author Francois
 *
 */
public class JarvisEdge {
	
	private int m_edgeId;
	private int m_n1;
	private int m_n2;
	private Date m_creation;
	private Date m_modified;
	
	/**
	 * Constrcutor.
	 * @param id Edge id.
	 * @param n1 Edge node1 id.
	 * @param n2 Edge node2 id.
	 */
	public JarvisEdge(int id, int n1, int n2, Date creation, Date modified) {
		m_edgeId = id;
		m_n1 = n1;
		m_n2 = n2;
		m_creation = creation;
		m_modified = modified;
	}
	
	public int getEdgeId() {
		return m_edgeId;
	}
	
	public int getNode1() {
		return m_n1;
	}
	
	public int getNode2() {
		return m_n2;
	}
	
	public Date getCreation() {
		return m_creation;
	}
	
	public Date getModified() {
		return m_modified;
	}
}

