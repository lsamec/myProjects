package editor;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SwingMain {

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<String> text = new ArrayList<String>();
		TextEditor te = new TextEditor(text,frame);
		TextEditorMenuBar teMenuBar = new TextEditorMenuBar(te, frame);
		TextEditorToolbar teToolbar = new TextEditorToolbar(te, frame);
		TextEditorLabel teLabel = new TextEditorLabel(te);
		te.addPlugins(teMenuBar);
		
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(te,BorderLayout.CENTER);	
		frame.getContentPane().add(teLabel,BorderLayout.PAGE_END);
		frame.setJMenuBar(teMenuBar);	
		frame.getContentPane().add(teToolbar, BorderLayout.PAGE_START);
		frame.setBounds(100, 100, 500, 400);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
