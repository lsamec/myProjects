package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class TextEditorToolbar extends JToolBar implements SelectionObserver,ClipboardObserver,UndoObserver {
	
	private TextEditor editor;
	private JFrame parent;
	private JButton cutItem;
	private JButton copyItem;
	private JButton undoItem;
	private JButton redoItem;
	private JButton pasteItem;
	
	public TextEditorToolbar(TextEditor editor, JFrame jframe){
		this.editor = editor;
		this.parent = jframe;
		undoItem = new JButton("U");
		undoItem.setEnabled(false);
		undoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlz.actionPerformed(null);
				editor.requestFocusInWindow();
				
			}
		});
		redoItem = new JButton("R");
		redoItem.setEnabled(false);
		redoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrly.actionPerformed(null);
				editor.requestFocusInWindow();
				
			}
		});
		copyItem = new JButton("CO");
		copyItem.setEnabled(false);
		copyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlc.actionPerformed(null);
				editor.requestFocusInWindow();
				
			}
		});
		cutItem = new JButton("CU");
		cutItem.setEnabled(false);
		cutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlx.actionPerformed(null);
				editor.requestFocusInWindow();
				
			}
		});
		pasteItem = new JButton("P");
		pasteItem.setEnabled(false);
		pasteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.ctrlv.actionPerformed(null);
				editor.requestFocusInWindow();
				
			}
		});
		this.add(undoItem);
		this.add(redoItem);
		this.add(copyItem);
		this.add(cutItem);
		this.add(pasteItem);
		UndoManager.instance().attachUO(this);
		editor.getModel().attachSO(this);
		editor.getModel().getClipboard().attach(this);
		
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

	@Override
	public void updateClipboard() {
		if(editor.getModel().getClipboard().stack.isEmpty()){
			this.pasteItem.setEnabled(false);
		}else{
			this.pasteItem.setEnabled(true);
		}		
	}

	@Override
	public void updateSelectionAvailability() {
		if(this.editor.getModel().getStarLocation() == null){
			this.cutItem.setEnabled(false);
			this.copyItem.setEnabled(false);
		}else{
			this.cutItem.setEnabled(true);
			this.copyItem.setEnabled(true);
		}		
	}
}
