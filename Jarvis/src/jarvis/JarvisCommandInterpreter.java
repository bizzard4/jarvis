package jarvis;

/**
 * Commands interpreter, this is the "brain" is the command.
 * TODO : Add voice feedback
 * @author Francois
 *
 */
public class JarvisCommandInterpreter {
	
	/**
	 * Reference to jarvis system.
	 */
	private Jarvis m_jarvis;
	
	/**
	 * Constructor.
	 * @param jarvis
	 */
	public JarvisCommandInterpreter(Jarvis jarvis) {
		m_jarvis = jarvis;
	}
	
	/**
	 * Process a command.
	 * @param command Command to process
	 * @return Return textual response.
	 */
	public String processCommand(String command) {
		String response = processReservedKeywords(command);
		if (response == null) {
			Boolean executed = m_jarvis.getActionKeyword().executeKeyword(command);
			if (executed == null) {
				response = "Error executing " + command;
			} else if (executed) {
				response = "Executing " + command;
			} else {
				response = "Command not found";
			}
		}
		return response;
	}
	
	/**
	 * Process reserved keyword.
	 * @param command Command.
	 * @return Response or null if no reserved keyword process.
	 */
	private String processReservedKeywords(String command) {
		if (command.startsWith("jadd")) { // Add keyword/command
			String[] splits = command.split("->");
			String key = splits[0].substring("jadd ".length()).trim();
			String cmd = splits[1].substring(1);
			Boolean res = m_jarvis.getActionKeyword().addKeywordCommand(key, cmd);
			if (res == null) {
				return "Error adding command";
			} else if (res) {
				return "Keyword added";
			} else {
				return "Keyword already present";
			}
		} else if (command.startsWith("jreset")) { // Reset all
			Boolean res = m_jarvis.getActionKeyword().reset();
			if (res == null) {
				return "Error resetting";
			} else if (res) {
				return "Reset complete";
			}
		}
		return null;
	}
}
