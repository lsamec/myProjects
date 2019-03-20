import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class SwingMain {

	private static void createAndShowGUI() {
		List<GraphicalObject> objects = new ArrayList<GraphicalObject>();

		objects.add(new LineSegment());
		objects.add(new Oval());
		
		GUI gui = new GUI(objects);
		gui.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
