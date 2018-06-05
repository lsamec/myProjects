package editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TextEditorMenuBar extends JMenuBar implements SelectionObserver,ClipboardObserver,UndoObserver {
	
	private TextEditor editor;
	private JFrame parent;
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem pasteItem;
	private JMenuItem pasteTakeItem;
	private JMenuItem deleteItem;

	
	final JFileChooser fc = new JFileChooser();

	public TextEditorMenuBar(TextEditor editor, JFrame jframe) {
		this.editor = editor;
		this.parent = jframe;
		JMenu menuFile = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(parent);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					List<String> lines = null;
					if (file.isFile()){
						try {
							lines = Files.readAllLines(Paths.get(file.getPath()));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					editor.getModel().setLines(lines);
					editor.getModel().notifyTextObs();
				} 
			}
		});
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
	
				    int retrival = fc.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				        try {
				            FileWriter fw = new FileWriter(fc.getSelectedFile());
				            editor.getModel().removeFromLoc(editor.getModel().getCursorLocation());
				            for(String line : editor.getModel().getLines()){
				            	fw.write(line+ System.lineSeparator());
				            }
				            editor.getModel().addCursor(editor.getModel().getCursorLocation());
				            fw.close();
				        } catch (Exception ex) {
				            ex.printStackTrace();
				        }
				    }
			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
	
				parent.dispose();
			}
		});
		menuFile.add(openItem);
		menuFile.add(saveItem);
		menuFile.add(exitItem);
		
		JMenu menuEdit = new JMenu("Edit");
		cutItem = new JMenuItem("Cut");
		cutItem.setEnabled(false);
		cutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlx.actionPerformed(null);
			}
		});
		copyItem = new JMenuItem("Copy");
		copyItem.setEnabled(false);
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlc.actionPerformed(null);
			}
		});
		undoItem = new JMenuItem("Undo");
		undoItem.setEnabled(false);
		undoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlz.actionPerformed(null);
			}
		});
		redoItem = new JMenuItem("Redo");
		redoItem.setEnabled(false);
		redoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrly.actionPerformed(null);
			}
		});
		pasteItem = new JMenuItem("Paste");
		pasteItem.setEnabled(false);
		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlv.actionPerformed(null);
			}
		});
		pasteTakeItem = new JMenuItem("Paste And Take");
		pasteTakeItem.setEnabled(false);
		pasteTakeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlshiftv.actionPerformed(null);
			}
		});
		deleteItem = new JMenuItem("Delete Selection");
		deleteItem.setEnabled(false);
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.getModel().deleteRange(editor.getModel().getSelectionRange());
				editor.getModel().notifySelObs();
			}
		});
		JMenuItem clearItem = new JMenuItem("Clear Document");
		clearItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.setModel(new TextEditorModel(new ArrayList<String>()));
				editor.getActionMap().clear();
				editor.getInputMap().clear();
				for(KeyListener kl : editor.getListeners(KeyListener.class)){
					editor.removeKeyListener(kl);
				} 
				editor.addActions();
				editor.repaint();
				editor.getModel().notifySelObs();
				editor.getModel().getClipboard().notifyObs();
				UndoManager.instance().redoStack.clear();
				UndoManager.instance().undoStack.clear();
				UndoManager.instance().notifyObs();
			}
		});
		menuEdit.add(undoItem);
		menuEdit.add(redoItem);
		menuEdit.add(cutItem);
		menuEdit.add(copyItem);
		menuEdit.add(pasteItem);
		menuEdit.add(pasteTakeItem);
		menuEdit.add(deleteItem);
		menuEdit.add(clearItem);
		JMenu menuMove = new JMenu("Move");
		JMenuItem curStart = new JMenuItem("Cursor To Document Start");
		curStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editor.getModel().removeFromLoc(editor.getModel().getCursorLocation());
				editor.getModel().setCursorLocation(new Location());
				editor.getModel().addCursor(editor.getModel().getCursorLocation());
				editor.getModel().notifyCursorObs();
			}
		});
		JMenuItem curEnd = new JMenuItem("Cursor To Document End");
		curEnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> lines = editor.getModel().getLines();
				editor.getModel().removeFromLoc(editor.getModel().getCursorLocation());
				editor.getModel().setCursorLocation(new Location(lines.size()-1,lines.get(lines.size()-1).length()));
				editor.getModel().addCursor(editor.getModel().getCursorLocation());
				editor.getModel().notifyCursorObs();
			}
		});
		menuMove.add(curStart);
		menuMove.add(curEnd);
		this.add(menuFile);
		this.add(menuEdit);
		this.add(menuMove);
		editor.getModel().attachSO(this);
		UndoManager.instance().attachUO(this);
		editor.getModel().getClipboard().attach(this);

	}

	@Override
	public void updateSelectionAvailability() {
		if(this.editor.getModel().getStarLocation() == null){
			this.cutItem.setEnabled(false);
			this.copyItem.setEnabled(false);
			this.deleteItem.setEnabled(false);
		}else{
			this.cutItem.setEnabled(true);
			this.copyItem.setEnabled(true);
			this.deleteItem.setEnabled(true);
		}
		
	}

	@Override
	public void updateClipboard() {
		if(editor.getModel().getClipboard().stack.isEmpty()){
			this.pasteItem.setEnabled(false);
			this.pasteTakeItem.setEnabled(false);
		}else{
			this.pasteItem.setEnabled(true);
			this.pasteTakeItem.setEnabled(true);
		}
		
	}

	@Override
	public void updateUndo() {
		if(UndoManager.instance().undoStack.isEmpty()){
			undoItem.setEnabled(false);
		}else{
			undoItem.setEnabled(true);
		}
		if(UndoManager.instance().redoStack.isEmpty()){
			redoItem.setEnabled(false);
		}else{
			redoItem.setEnabled(true);
		}
	}
}
