package editor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class MyComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MyComponent(){
	}
	
	public MyComponent(JFrame frame) {
		this();
		Action disposeAction = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        frame.dispose();
		    }
		};
		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
                "dispose");
		this.getActionMap().put("dispose",
				disposeAction);
	}
	
	void drawString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.RED);
	    g.drawLine(0, 0, 0, 300);
	    g.drawLine(0, 0, 300, 0);
	    drawString(g, "Bla bla \nBla bla \n", 0, 0);
	}

}
