package jarvis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class responsible to store loaded configuration from a file. This class also act as a factory.
 * @author Francois
 *
 */
public class ConfigurationFile {
	
	/**
	 * Hashmap to store param/values.
	 */
	private HashMap<String, String> m_parameters;
	
	/**
	 * Constructor.
	 */
	public ConfigurationFile() {
		m_parameters = new HashMap<>();
	}
	
	/**
	 * Get a parameter value.
	 * @param parameter Parameter.
	 * @return Value as string.
	 */
	public String getValue(String parameter) {
		return m_parameters.get(parameter);
	}
	
	/**
	 * Set a parameter value.
	 * @param parameter Parameter.
	 * @param value Value to set.
	 */
	public void setValue(String parameter, String value) {
		m_parameters.put(parameter, value);
	}
	
	/**
	 * Create a configuration file instance using a configuration file path.
	 * @param configurationFilePath Configuration file path.
	 * @return Configuration file instance.
	 */
	public synchronized static ConfigurationFile getConfigurationFile(String configurationFilePath) {
		ConfigurationFile toReturn = new ConfigurationFile(); 
		try (BufferedReader br = new BufferedReader(new FileReader(configurationFilePath))) {
			for (String line; (line = br.readLine()) != null;) {
				if (!line.startsWith("#")) {
					String[] splits = line.split("=");
					toReturn.setValue(splits[0], splits[1]);
				}
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		} catch (Exception ex) {
			System.err.println("Error reading configuration : " + ex.getMessage());
		}
		return toReturn;
	}
}
