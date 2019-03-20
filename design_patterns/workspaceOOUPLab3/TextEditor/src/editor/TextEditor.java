package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class TextEditor extends JComponent implements CursorObserver,
		TextObserver {

	private TextEditorModel model;
	private JFrame frame;
	Action moveLeft = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.moveCursorLeft();
		}
	};
	Action moveRight = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.moveCursorRight();
		}
	};
	Action moveUp = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.moveCursorUp();
		}
	};
	Action moveDown = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.moveCursorDown();
		}
	};
	Action deleteBefore = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (model.getStarLocation() != null) {
				model.deleteRange(model.getSelectionRange());
			} else {
				model.deleteBefore();
			}
		}
	};
	Action deleteAfter = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			if (model.getStarLocation() != null) {
				model.deleteRange(model.getSelectionRange());
			} else {
				model.deleteAfter();
			}
		}
	};

	Action shiftPressed = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.setShiftHeld(true);
		}
	};
	Action shiftReleased = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.setShiftHeld(false);
		}
	};
	Action ctrlc = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.pushOnClipboard();
		}
	};
	Action ctrlv = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			try {
				model.insert(model.peekOnClipboard());
			} catch (EmptyStackException exce) {

			}
		}
	};
	Action ctrlshiftv = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			try {
				model.insert(model.popOnClipboard());
			} catch (EmptyStackException exce) {

			}
		}
	};
	Action ctrlx = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			model.pushOnClipboardAndDelete();
		}

	};
	Action ctrlz = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			try {
				UndoManager.instance().undo();
			} catch (EmptyStackException exce) {

			}
		}

	};
	Action ctrly = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			try {
				UndoManager.instance().redo();
			} catch (EmptyStackException exce) {

			}
		}

	};

	private TextEditor() {
		super();
	}

	public void addActions() {
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		this.getActionMap().put("moveLeft", moveLeft);

		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		this.getActionMap().put("moveRight", moveRight);
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
		this.getActionMap().put("moveUp", moveUp);
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
		this.getActionMap().put("moveDown", moveDown);
		this.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"),
				"deleteBefore");
		this.getActionMap().put("deleteBefore", deleteBefore);
		this.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "deleteAfter");
		this.getActionMap().put("deleteAfter", deleteAfter);
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0),
				"shiftPressed");
		this.getActionMap().put("shiftPressed", shiftPressed);
		this.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_END, 0, true),
				"shiftReleased");
		this.getActionMap().put("shiftReleased", shiftReleased);
		this.getInputMap().put(KeyStroke.getKeyStroke("ctrl V"), "ctrlv");
		this.getActionMap().put("ctrlv", ctrlv);
		this.getInputMap().put(KeyStroke.getKeyStroke("ctrl C"), "ctrlc");
		this.getActionMap().put("ctrlc", ctrlc);
		this.getInputMap().put(KeyStroke.getKeyStroke("ctrl X"), "ctrlx");
		this.getActionMap().put("ctrlx", ctrlx);
		this.getInputMap().put(KeyStroke.getKeyStroke("ctrl shift V"),
				"ctrlshiftv");
		this.getActionMap().put("ctrlshiftv", ctrlshiftv);
		this.getInputMap().put(KeyStroke.getKeyStroke("ctrl Z"), "ctrlz");
		this.getActionMap().put("ctrlz", ctrlz);
		this.getInputMap().put(KeyStroke.getKeyStroke("ctrl Y"), "ctrly");
		this.getActionMap().put("ctrly", ctrly);

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (Character.isLetterOrDigit(e.getKeyChar())
						|| Character.isWhitespace(e.getKeyChar())) {
					model.insert(e.getKeyChar());
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					model.breakLine();
				}
			}
		});

		this.model.attachCO(this);
		this.model.attachTO(this);
	}

	public void addPlugins(JMenuBar menuBar) {
		List<Class> classes = null;
		try {
			classes = ReflectionUtil.getClasses("plugins");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JMenu menuPlugin = new JMenu("Plugins");
		for(Class<Plugin> clazz : classes){
			Plugin plugin = null;
			try {
				plugin = (Plugin)clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			final Plugin fPlugin = plugin;
			JMenuItem pluginItem = new JMenuItem(plugin.getName());
			pluginItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					fPlugin.execute(model, UndoManager.instance(), frame);					
				}
			});
			menuPlugin.add(pluginItem);
		}
		menuBar.add(menuPlugin);
	}

	public TextEditor(List<String> text, JFrame frame) {
		this();
		this.model = new TextEditorModel(text);
		this.frame = frame;
		this.addActions();
	}

	public TextEditorModel getModel() {
		return model;
	}

	public void setModel(TextEditorModel model) {
		this.model = model;
	}

	void drawText(Graphics g, int x, int y) {
		Iterator<String> it = this.allLines();
		while (it.hasNext()) {
			String next = it.next();
			g.drawString(next, x, y += g.getFontMetrics().getHeight());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		drawText(g, 0, 0);

	}

	public Iterator<String> allLines() {
		TextEditorModel model = this.model;
		Iterator<String> it = new Iterator<String>() {

			private int currentIndex = 0;
			private int size = model.getLines().size();

			@Override
			public boolean hasNext() {
				if (currentIndex < size) {
					return true;
				}
				return false;
			}

			@Override
			public String next() {
				String line = model.getLines().get(currentIndex);
				currentIndex++;
				return line;
			}

			@Override
			public void remove() {
				model.getLines().remove(currentIndex - 1);
				size--;
				currentIndex--;
			}
		};
		return it;
	}

	public Iterator<String> linesRange(int index1, int index2) {
		TextEditorModel model = this.model;
		Iterator<String> it = new Iterator<String>() {

			private int currentIndex = index1;
			private int endIndex = index2;

			@Override
			public boolean hasNext() {
				if (currentIndex < endIndex) {
					return true;
				}
				return false;
			}

			@Override
			public String next() {
				String line = model.getLines().get(currentIndex);
				currentIndex++;
				return line;
			}
		};
		return it;
	}

	@Override
	public void updateCursorLocation() {
		removeEmptyLines();
		if (model.getShiftHeld().equals(true)) {
			model.getSelectionRange().setEnd(
					new Location(model.getCursorLocation().getRow(), model
							.getCursorLocation().getColumn() + 1));
		}
		this.repaint();
	}

	@Override
	public void updateText() {
		removeEmptyLines();
		this.repaint();
	}

	public void removeEmptyLines() {
		Iterator<String> it = this.allLines();
		while (it.hasNext()) {
			String next = it.next();
			if (next.equals("")) {
				it.remove();
				continue;
			}
		}
	}

}
