package editor;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;


public class TextEditorLabel extends JLabel implements TextObserver,CursorObserver {
	
	private String cursorLoc;
	private String noOfLines;
	private TextEditor editor;
	
	public TextEditorLabel(TextEditor editor){
		this.editor = editor;
		this.setBorder((BorderFactory.createMatteBorder(
                1, 1, 1, 1, Color.BLUE)));
		this.cursorLoc ="Cursor - Row: " + editor.getModel().getCursorLocation().getRow() + " Column: "+editor.getModel().getCursorLocation().getColumn()+ " | ";
		this.noOfLines = "No. of lines: "+ editor.getModel().getLines().size();
		this.setText(cursorLoc+" "+noOfLines);
		editor.getModel().attachTO(this);
		editor.getModel().attachCO(this);
	}

	@Override
	public void updateCursorLocation() {
		this.cursorLoc ="Cursor - Row: " + editor.getModel().getCursorLocation().getRow() + " Column: "+editor.getModel().getCursorLocation().getColumn()+" | ";
		this.noOfLines = "No. of lines: "+ editor.getModel().getLines().size();
		this.setText(cursorLoc+" "+noOfLines);	
	}

	@Override
	public void updateText() {
		this.cursorLoc ="Cursor - Row: " + editor.getModel().getCursorLocation().getRow() + " Column: "+editor.getModel().getCursorLocation().getColumn()+" | ";
		this.noOfLines = "No. of lines: "+ editor.getModel().getLines().size();
		this.setText(cursorLoc+" "+noOfLines);	
	}
}
