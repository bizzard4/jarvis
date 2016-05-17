package jarvis.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

import jarvis.Jarvis;
import jarvis.JarvisCommandInterpreter;

/**
 * Main jarvis UI command receiver.
 * @author Francois
 *
 */
public class JarvisUI extends JFrame implements KeyListener {
	
	/**
	 * Serial ID generated.
	 */
	private static final long serialVersionUID = 4692124173177754922L;

	private JarvisCommandInterpreter m_interpreter;
	
	private DefaultListModel<String> m_logs = null;
	
	public JarvisUI(Jarvis jarvis) {
		m_interpreter = new JarvisCommandInterpreter(jarvis);
		initUI();
	}
	
	private void initUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 300));
		
		// Prepare layout
		this.getContentPane().setLayout(new BorderLayout());
		
		JList<String> list = new JList<>();
		m_logs = new DefaultListModel<String>();
		list.setModel(m_logs);
		m_logs.addElement("-Logs Starting-");
		this.getContentPane().add(list, BorderLayout.CENTER);
		
		JTextField cmdField = new JTextField();
		cmdField.addKeyListener(this);
		this.getContentPane().add(cmdField, BorderLayout.PAGE_END);
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				cmdField.requestFocus();
			}
	
			@Override
			public void windowIconified(WindowEvent e) {	
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {	
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {	
			}
			
			@Override
			public void windowClosing(WindowEvent e) {	
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {	
			}
		});
	}
	
	private void executeCommand(String cmd) {
		m_logs.addElement(m_interpreter.processCommand(cmd));
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextField tf = (JTextField)arg0.getSource();
			String text = tf.getText();
			if (!text.isEmpty()) {
				executeCommand(tf.getText());
				tf.setText("");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}


}
