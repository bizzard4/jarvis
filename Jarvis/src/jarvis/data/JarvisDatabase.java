package jarvis.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import jarvis.ConfigurationFile;

/**
 * Class responsible to provide database connection and pooling.
 * TODO : Pooling
 * @author Francois
 *
 */
public class JarvisDatabase {
	
	/**
	 * Database connection.
	 */
	private Connection m_connection;
	
	/**
	 * Configuration file reference.
	 */
	private ConfigurationFile m_configurations = null;
	
	/**
	 * Configuration file name.
	 */
	public static final String DATABASE_CONFIGURATION_FILE = "db.conf";
	
	/**
	 * Constructor.
	 */
	public JarvisDatabase() {
		m_connection = null;
		m_configurations = ConfigurationFile.getConfigurationFile(DATABASE_CONFIGURATION_FILE);
	}
	
	/**
	 * Execute a raw database query without any escape.
	 * @param query Query to execute.
	 * @return Query result or null if error.
	 */
	public ResultSet executeRawQuery(String query) {
		ResultSet rs = null;
		try {
			Statement statement = m_connection.createStatement();
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * Open connection if it is not already open for this instance.
	 * @return True if connection was successfully open.
	 */
	public boolean connect() {
		try {
			if ((m_connection == null) || (m_connection.isClosed())) {
				Properties props = new Properties();
				props.setProperty("user", m_configurations.getValue("user"));
				props.setProperty("password", m_configurations.getValue("password"));
				m_connection = DriverManager.getConnection("jdbc:postgresql://" + m_configurations.getValue("host") + 
						":" + m_configurations.getValue("port") + 
						"/" + m_configurations.getValue("database"), props);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Return connection object to create query.
	 * @return SQL connection object.
	 */
	public Connection getConnection() {
		return m_connection;
	}
}
