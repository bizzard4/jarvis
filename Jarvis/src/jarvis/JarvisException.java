package jarvis;

/**
 * Exception wrapper class for jarvis related error.
 * @author Francois
 *
 */
public class JarvisException extends Exception {
	
	/**
	 * Constructor.
	 * @param message Error message.
	 */
	public JarvisException(String message) {
		super(message);
	}
}
