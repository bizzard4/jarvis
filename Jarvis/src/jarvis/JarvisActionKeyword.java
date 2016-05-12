package jarvis;


import java.io.IOException;
import java.util.List;

import jarvis.data.JarvisEdge;
import jarvis.data.JarvisGraph;
import jarvis.data.JarvisNode;

/**
 * Class responsible for the action keyword feature.
 * @author Francois
 *
 */
public class JarvisActionKeyword {
	
	private JarvisGraph m_graph = null;
	
	public JarvisActionKeyword() {
		m_graph = new JarvisGraph();
	}
	
	/**
	 * Add a keyword associated with a command. If keyword/command pair already exist, then we do not add it.
	 * @param keyword Keyword to add/overwrite.
	 * @param command Command to execute.
	 * @return Return true if success, return null if error return false if operation was not done.
	 */
	public Boolean addKeywordCommand(String keyword, String command) {
		JarvisNode keywordNode = m_graph.getNode(keyword);
		if (keywordNode == null) {
			keywordNode = m_graph.addNode("KEY", keyword);
		}
		JarvisNode cmdNode = m_graph.getNode(command);
		if (cmdNode == null) {
			cmdNode = m_graph.addNode("CMD", command);
		}
		
		// In case of sql error
		if ((keywordNode == null) || (cmdNode == null)) {
			return null;
		}
		
		if (m_graph.isAdjacent(keywordNode, cmdNode)) {
			return false; // Already linked.
		} else {
			// Add edge
			JarvisEdge e = m_graph.addEdge(keywordNode, cmdNode);
			if (e != null) {
				return true;
			}
		}
		
		return null;
	}
	
	/**
	 * Execute a keyword and return result.
	 * TODO : Asych execution.
	 * @param keyword Keyword to execute.
	 * @return Boolean true if executed, false if not and null if error.
	 */
	public Boolean executeKeyword(String keyword) {
		JarvisNode n = m_graph.getNode(keyword);
		if (n != null) {
			List<JarvisNode> cmds = m_graph.getNeighbors(n);
			if (cmds.isEmpty()) {
				return false;
			}
			for (JarvisNode cmd : cmds) {
				try {
					Process p = Runtime.getRuntime().exec(cmd.getData());
				} catch (IOException e) {
					System.err.println(e.getMessage());
					return null;
				}
			}
			return true;
		}
		return false;
	}
	
}
