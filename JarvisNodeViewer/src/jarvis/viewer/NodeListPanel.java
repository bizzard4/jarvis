package jarvis.viewer;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import jarvis.data.JarvisNode;

public class NodeListPanel extends JPanel {
	
	public NodeListPanel() {
		initUI();
	}
	
	private void initUI() {
		JList<JarvisNode> list = new JList<>();
		add(list);
		
		JButton btn_add = new JButton("Add");
		add(btn_add);
		
		JButton btn_rem = new JButton("Remove");
		add(btn_rem);
		
		JButton btn_cln = new JButton("Clone");
		add(btn_cln);
		
		JButton btn_ref = new JButton("Refresh");
		add(btn_ref);
	}
	
}
