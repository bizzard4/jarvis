package jarvis;

/**
 * Exception wrapper class for jarvis related error.
 * @author Francois
 *
 */
public class JarvisException extends Exception {
	
	/**
	 * Serial ID generated.
	 */
	private static final long serialVersionUID = -2045619067009663198L;

	/**
	 * Constructor.
	 * @param message Error message.
	 */
	public JarvisException(String message) {
		super(message);
	}
}
