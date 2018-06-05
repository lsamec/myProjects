import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


public class Canvas extends JComponent {
	
	private GUI gui;
	private Boolean mouseDragged = false;
	
	public Canvas(GUI gui){
		this.setFocusable(true);
		this.gui = gui;
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Point mousePoint = new Point(e.getX(),e.getY());
				gui.getCurrentState().mouseDragged(mousePoint);
				gui.getCurrentState().mouseUp(mousePoint, e.isShiftDown(), e.isControlDown());
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Point mousePoint = new Point(e.getX(),e.getY());
				gui.getCurrentState().mouseDown(mousePoint, e.isShiftDown(), e.isControlDown());
				gui.getCurrentState().mouseDragged(mousePoint);
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {			
			}
		});
		Action toIdle = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().onLeaving();
				gui.setCurrentState(new IdleState());
				repaint();					
			}
		};
		Action up = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_UP);;				
			}
		};
		Action down = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_DOWN);;				
			}
		};
		Action left = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_LEFT);;				
			}
		};
		Action right = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_RIGHT);;				
			}
		};
		Action plus = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_P);;				
			}
		};
		Action minus = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_M);;				
			}
		};
		Action g = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_G);;				
			}
		};
		Action u = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				gui.getCurrentState().keyPressed(KeyEvent.VK_U);;				
			}
		};
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "toIdle");
		this.getActionMap().put("toIdle", toIdle);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		this.getActionMap().put("up", up);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		this.getActionMap().put("down", down);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		this.getActionMap().put("left", left);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		this.getActionMap().put("right", right);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "plus");
		this.getActionMap().put("plus", plus);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "minus");
		this.getActionMap().put("minus", minus);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "g");
		this.getActionMap().put("g", g);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "u");
		this.getActionMap().put("u", u);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Renderer r = new G2DRendererImpl(g2d);
		for(GraphicalObject obj : this.gui.getModel().list()){
		  obj.render(r);
		  this.gui.getCurrentState().afterDraw(r, obj);
		}
		 this.gui.getCurrentState().afterDraw(r);
	}
}
