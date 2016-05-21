package jarvis.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

/**
 * Class responsible to work with database. All data is stored as graphs, this class is responsible
 * to create this abstraction to the code.
 * @author Francois
 *
 */
public class JarvisGraph {
	
	private JarvisDatabase m_database;
	
	/**
	 * Constructor.
	 */
	public JarvisGraph() {
		m_database = new JarvisDatabase();
	}
	
	/**
	 * Get node with given data. If not found, then null.
	 * TODO : Multiple nodes return.
	 * @param data Data to search.
	 * @return Node or null if not found.
	 */
	public JarvisNode getNode(String data) {
		if (m_database.connect()) {	
			try {
				String query = "SELECT * FROM nodes WHERE node_data=? LIMIT 1";
				PreparedStatement statement = m_database.getConnection().prepareStatement(query);
				statement.setString(1, data);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					JarvisNode entry = new JarvisNode(rs.getInt("node_id"), rs.getString("node_type"), rs.getString("node_data"), rs.getDate("node_creation"), rs.getDate("node_modified"));
					return entry;
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * Get the list of nodes connected to the given node. List could be empty.
	 * @param node Entry point node.
	 * @return List of connected nodes or null if error.
	 */
	public List<JarvisNode> getNeighbors(JarvisNode node) {
		if (m_database.connect()) {
			try {
				String query = "SELECT n2.node_id, n2.node_type, n2.node_data, n2.node_creation, n2.node_modified FROM nodes INNER JOIN edges ON nodes.node_id = edges.edge_n1 OR nodes.node_id = edges.edge_n2 INNER JOIN nodes AS n2 ON ((edges.edge_n1 = n2.node_id OR edges.edge_n2 = n2.node_id) AND n2.node_data <> ?) WHERE nodes.node_data = ?;";
				PreparedStatement statement = m_database.getConnection().prepareStatement(query);
				statement.setString(1, node.getData());
				statement.setString(2, node.getData());
				ResultSet rs = statement.executeQuery();
				ArrayList<JarvisNode> nodes = new ArrayList<>();
				while (rs.next()) {
					JarvisNode n = new JarvisNode(rs.getInt("node_id"), rs.getString("node_type"), rs.getString("node_data"), rs.getDate("node_creation"), rs.getDate("node_modified"));
					nodes.add(n);
				}
				return nodes;
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * Test is nodes are connected by a edge.
	 * @param n1 Node 1.
	 * @param n2 Node 2.
	 * @return Return if connected or null if error.
	 */
	public Boolean isAdjacent(JarvisNode n1, JarvisNode n2) {
		if (m_database.connect()) {
			try {
				String query = "SELECT * FROM edges WHERE ((edge_n1 = ? AND edge_n2 = ?) OR (edge_n1 = ? AND edge_n2 = ?))";
				PreparedStatement statement = m_database.getConnection().prepareStatement(query);
				statement.setInt(1, n1.getNodeId());
				statement.setInt(2, n2.getNodeId());
				statement.setInt(3, n2.getNodeId());
				statement.setInt(4, n1.getNodeId());
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					return true;
				}
				return false;
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * Add a node to the graph. This method doesn't validate is node already exist.
	 * @param type Node type.
	 * @param data Data in node.
	 * @return Return inserted node or null if operation failed.
	 */
	public JarvisNode addNode(String type, String data) {
		if (m_database.connect()) {
			try {
				String query = "INSERT INTO nodes(node_type, node_data, node_creation, node_modified) VALUES (?, ?, ?, ?)";
				PreparedStatement statement = m_database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
				statement.setString(1, type);
				statement.setString(2, data);
				java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				statement.setDate(3, currentDate);
				statement.setDate(4, currentDate);
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt("node_id");
					JarvisNode newNode = new JarvisNode(id, type, data, currentDate, currentDate);
					return newNode;
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * Add a edge to the graph. This method doesn't validate if edge already exist.
	 * @param n1 Node1.
	 * @param n2 Node2.
	 * @return New edge or null if error.
	 */
	public JarvisEdge addEdge(JarvisNode n1, JarvisNode n2) {
		if (m_database.connect()) {
			try {
				String query = "INSERT INTO edges(edge_n1, edge_n2, edge_creation, edge_modified) VALUES (?, ?, ?, ?)";
				PreparedStatement statement = m_database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
				statement.setInt(1, n1.getNodeId());
				statement.setInt(2, n2.getNodeId());
				java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				statement.setDate(3, currentDate);
				statement.setDate(4, currentDate);
				statement.executeUpdate();
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt("edge_id");
					JarvisEdge newEdge = new JarvisEdge(id, n1.getNodeId(), n2.getNodeId(), currentDate, currentDate);
					return newEdge;
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
	
}
