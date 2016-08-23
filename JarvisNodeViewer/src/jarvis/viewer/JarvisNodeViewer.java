package jarvis.viewer;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JarvisNodeViewer extends JFrame {
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -3425344646411497429L;

	public JarvisNodeViewer() {
		initUI();
	}
	
	private void initUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new NodeListPanel(), BorderLayout.LINE_START);
	}

	public static void main(String[] args) {
		JarvisNodeViewer viewer = new JarvisNodeViewer();
		viewer.pack();
		viewer.setVisible(true);
	}

}
