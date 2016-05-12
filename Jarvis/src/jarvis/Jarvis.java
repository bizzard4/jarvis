package jarvis;

import jarvis.ui.*;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

/**
 * Jarvis system main class.
 * @author Francois
 *
 */
public class Jarvis {
	
	/**
	 * UI system.
	 */
	private JarvisUI m_ui = null;
	
	/**
	 * Keyword action system.
	 */
	private JarvisActionKeyword m_actionKeyword = null;
	
	/**
	 * Constructor.
	 */
	public Jarvis() {
		
	}
	
	/**
	 * Build action keyword system.
	 */
	public void buildActionKeyword() {
		m_actionKeyword = new JarvisActionKeyword();
	}
	
	/**
	 * Build jarvis UI system. Must be done at the end.
	 */
	public void buildUI() {
		m_ui = new JarvisUI(this);
	}
	
	/**
	 * Start jarvis system with initiated systems.
	 */
	public void start() {
		m_ui.pack();
		m_ui.setVisible(true);
	}

	/**
	 * Return action keyword system.
	 * @return Action keyword system.
	 */
	public JarvisActionKeyword getActionKeyword() {
		return m_actionKeyword;
	}
	
	/**
	 * Main.
	 * @param args
	 */
	public static void main(String[] args) {
		Jarvis jarvis = new Jarvis();
		jarvis.buildActionKeyword();
		if (args.length > 0) { // Process command
			JarvisCommandInterpreter intr = new JarvisCommandInterpreter(jarvis);
			JOptionPane.showMessageDialog(null, intr.processCommand(args[0]));
		} else {
			jarvis.buildUI();
			jarvis.start();
		}
	}
}
