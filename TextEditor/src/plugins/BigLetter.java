package plugins;

import java.util.List;

import javax.swing.JFrame;

import editor.Plugin;
import editor.TextEditorModel;
import editor.UndoManager;

public class BigLetter implements Plugin{

	@Override
	public String getName() {
		return "Big Letter";
	}

	@Override
	public String getDescription() {
		return "Make all first letters big";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager,
			JFrame frame) {
		List<String> lines = model.getLines();
		for(int br=0;br<model.getLines().size();br++){
			if(Character.isLetterOrDigit(lines.get(br).charAt(0))){
				lines.set(br,Character.toUpperCase(lines.get(br).charAt(0))+lines.get(br).substring(1));
			}
			for(int i=0;i<lines.get(br).length();i++){
				Character chr = lines.get(br).charAt(i);
				if(i<lines.get(br).length()-1){
					Character nextChr = lines.get(br).charAt(i+1);
					if (chr.equals(' ') && Character.isLetterOrDigit(nextChr)){
						lines.set(br, lines.get(br).substring(0,i+1)+ Character.toUpperCase(nextChr)+lines.get(br).substring(i+2));
					}
				}
			}
		}
		model.notifyTextObs();
		
	}

}
