package editor;

import javax.swing.JFrame;

public interface Plugin {
	public String getName();	
	public String getDescription();
	public void execute(TextEditorModel model, UndoManager undoManager, JFrame frame);
}
