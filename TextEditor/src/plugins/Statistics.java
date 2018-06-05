package plugins;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import editor.Plugin;
import editor.TextEditorModel;
import editor.UndoManager;


public class Statistics implements Plugin {

	@Override
	public String getName() {
		return "Statistics";
	}

	@Override
	public String getDescription() {
		return "Statistika Dokumenta";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, JFrame frame) {
		model.removeFromLoc(model.getCursorLocation());
		Integer noOfLines = model.getLines().size();
		Integer noOfWords = 0;
		Integer noOfLetters = 0;
		for(String line : model.getLines()){
			if(Character.isLetterOrDigit(line.charAt(0))){
				noOfWords++;
			}
			for(int i=0;i<line.length();i++){
				Character chr = line.charAt(i);
				if(Character.isLetterOrDigit(chr)){
					noOfLetters++;
				}
				if(i<line.length()-1){
					Character nextChr = line.charAt(i+1);
					if (chr.equals(' ') && Character.isLetterOrDigit(nextChr)){
						noOfWords++;
					}
				}
			}
		}
		model.addCursor(model.getCursorLocation());
		JOptionPane.showMessageDialog(frame,
			    "Lines: "+noOfLines+ " Words: "+noOfWords+" Letters: "+noOfLetters,this.getDescription(),JOptionPane.PLAIN_MESSAGE);
		
	}

}
