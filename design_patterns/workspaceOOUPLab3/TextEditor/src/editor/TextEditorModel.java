package editor;
import java.util.LinkedList;
import java.util.List;

public class TextEditorModel {
	private List<String> lines;
	private LocationRange selectionRange;
	private Location cursorLocation;
	private Location starLocation;
	private List<CursorObserver> cursorObservers;
	private List<TextObserver> textObservers;
	private List<SelectionObserver> selectionObservers;
	private Boolean shiftHeld;
	private ClipboardStack clipboard;// mozda premjestiti

	public void pushOnClipboard() {
		clipboard.stack.push(makeSelectionIntoText(this.selectionRange));
		clipboard.notifyObs();
	}

	public void pushOnClipboardAndDelete() {
		clipboard.stack.push(makeSelectionIntoText(this.selectionRange));
		deleteRange(this.selectionRange);
		clipboard.notifyObs();
	}

	public String peekOnClipboard() {
		String line = clipboard.stack.peek();
		clipboard.notifyObs();
		return line;
	}

	public String popOnClipboard() {
		String line = clipboard.stack.pop();
		clipboard.notifyObs();
		return line;
	}

	private String makeSelectionIntoText(LocationRange loc) {
		String text = "";
		if (this.starLocation != null) {
			selectionActionInit(loc);
			if (loc.getBegin().getRow() == loc.getEnd().getRow()) {
				Integer row = loc.getBegin().getRow();
				if (this.starLocation
						.isLowerColumnAndSameRow(this.cursorLocation)) {
					text = lines.get(row).substring(loc.getBegin().getColumn(),
							loc.getEnd().getColumn() - 1);
				} else {
					text = lines.get(row).substring(loc.getBegin().getColumn(),
							loc.getEnd().getColumn());
				}
			} else {
				text += lines.get(loc.getBegin().getRow()).substring(
						loc.getBegin().getColumn());
				text += "\n";
				for (int row = loc.getBegin().getRow() + 1; row < loc.getEnd()
						.getRow(); row++) {
					text += lines.get(row);
					text += "\n";
				}
				if (this.starLocation
						.isLowerColumnAndSameRow(this.cursorLocation)) {
					text += lines.get(loc.getEnd().getRow()).substring(0,
							loc.getEnd().getColumn());
				} else {
					text += lines.get(loc.getEnd().getRow()).substring(0,
							loc.getEnd().getColumn() + 1);
				}
			}

		}
		return text;
	}

	public void insert(Character c) {
		Location cursLoc = new Location(this.cursorLocation);
		TextEditorModel teModel = this;

		addToLoc(this.cursorLocation, c.toString());
		this.cursorLocation.setColumn(this.cursorLocation.getColumn() + 1);
		EditAction action = new EditAction() {

			private Character ch = c;
			private Location oldCursloc = cursLoc;
			private TextEditorModel model = teModel;

			@Override
			public void executeUndo() {
				model.removeFromLoc(oldCursloc);
				if (oldCursloc.isLowerColumnAndSameRow(model.cursorLocation)) {
					model.cursorLocation.setColumn(model.cursorLocation
							.getColumn() - 1);
				}
				model.notifyTextObs();
			}

			@Override
			public void executeDo() {
				model.addToLoc(oldCursloc, ch.toString());
				if (oldCursloc.isLowerColumnAndSameRow(model.cursorLocation)) {
					model.cursorLocation.setColumn(model.cursorLocation
							.getColumn() + 1);
				}
				model.notifyTextObs();
			}
		};
		UndoManager.instance().push(action);
		notifyTextObs();
	}

	public void insert(String text) {
		Integer firstRow = this.cursorLocation.getRow();
		int i = 0;
		for (String line : text.split("\n")) {
			if (i == 0) {
				addToLoc(this.cursorLocation, line);
				this.cursorLocation.setColumn(this.cursorLocation.getColumn()
						+ line.length());
				i++;
			} else {
				lines.add(firstRow + i, line);
				this.cursorLocation.setColumn(line.length());
				this.cursorLocation.setRow(firstRow + i);
				;
				i++;
			}
		}
		notifyTextObs();
	}

	public void breakLine() {
		String line = lines.get(this.cursorLocation.getRow());
		
		Location cursLoc = new Location(this.cursorLocation);
		TextEditorModel teModel = this;
		EditAction action = new EditAction() {

			private Location oldCursloc = cursLoc;
			private TextEditorModel model = teModel;

			@Override
			public void executeUndo() {
				lines.set(this.oldCursloc.getRow(),
						lines.get(this.oldCursloc.getRow())+lines.get(this.oldCursloc.getRow()+1));
				lines.remove(this.oldCursloc.getRow()+1);
				model.cursorLocation = oldCursloc;
				model.notifyTextObs();
			}

			@Override
			public void executeDo() {
				lines.set(this.oldCursloc.getRow(),
						line.substring(0, this.oldCursloc.getColumn()));
				lines.add(this.oldCursloc.getRow() + 1,
						line.substring(this.oldCursloc.getColumn()));
				model.cursorLocation = new Location(model.cursorLocation.getRow() + 1, 0);
				model.notifyTextObs();
			}
		};
		UndoManager.instance().push(action);
		
		lines.set(this.cursorLocation.getRow(),
				line.substring(0, this.cursorLocation.getColumn()));
		lines.add(this.cursorLocation.getRow() + 1,
				line.substring(this.cursorLocation.getColumn()));
		this.cursorLocation = new Location(this.cursorLocation.getRow() + 1, 0);
		notifyTextObs();
	}

	private void selectionActionInit(LocationRange loc) {
		if (loc.getBegin().getRow() > loc.getEnd().getRow()
				|| (loc.getBegin().getRow() == loc.getEnd().getRow() && loc
						.getBegin().getColumn() > loc.getEnd().getColumn())) {
			Location temp = null;
			temp = loc.getBegin();
			loc.setBegin(loc.getEnd());
			loc.setEnd(temp);
		}
	}

	public void deleteRange(LocationRange loc) {
		selectionActionInit(loc);
		if (this.starLocation.isLessThan(this.cursorLocation)) {
			removeFromLoc(this.starLocation);
			this.starLocation.setColumn(this.starLocation.getColumn() - 1);
			addCursor(this.starLocation);
			removeFromLoc(this.cursorLocation);
			this.cursorLocation.setColumn(this.cursorLocation.getColumn() - 1);
			addToLoc(this.cursorLocation, "*");
			Location temp = null;
			temp = this.cursorLocation;
			this.setCursorLocation(this.starLocation);
			this.setStarLocation(temp);
		}
		removeFromLoc(this.starLocation);
		this.starLocation = null;
		if (loc.getBegin().getRow() == loc.getEnd().getRow()) {
			Integer row = loc.getBegin().getRow();
			lines.set(row,
					lines.get(row).substring(0, loc.getBegin().getColumn())
							+ lines.get(row)
									.substring(loc.getEnd().getColumn()));// mozda
																			// puca
		} else {
			lines.set(
					loc.getBegin().getRow(),
					lines.get(loc.getBegin().getRow()).substring(0,
							loc.getBegin().getColumn()));
			for (int row = loc.getBegin().getRow() + 1; row < loc.getEnd()
					.getRow(); row++) {
				lines.set(row, "");
			}
			lines.set(loc.getEnd().getRow(), lines.get(loc.getEnd().getRow())
					.substring(loc.getEnd().getColumn() + 1));
		}
		notifyTextObs();
	}

	public void deleteBefore() {
		Location cursLoc = new Location(this.cursorLocation);
		TextEditorModel teModel = this;
		if (cursLoc.giveLeft().getColumn() >= 0) {
			EditAction action = new EditAction() {

				private Location oldCursloc = cursLoc.giveLeft();
				private TextEditorModel model = teModel;
				private String str = model.atLocation(oldCursloc);

				@Override
				public void executeUndo() {
					model.addToLoc(oldCursloc, str);

					if (model.cursorLocation
							.isLowerColumnAndSameRow(oldCursloc) || model.cursorLocation.equals(oldCursloc)) {
						model.cursorLocation.setColumn(model.cursorLocation
								.getColumn() + 1);
					}
					model.notifyTextObs();
				}

				@Override
				public void executeDo() {
					model.removeFromLoc(oldCursloc);
					if (oldCursloc
							.isLowerColumnAndSameRow(model.cursorLocation)) {
						model.cursorLocation.setColumn(model.cursorLocation
								.getColumn() - 1);
					}
					model.notifyTextObs();
				}
			};
			UndoManager.instance().push(action);
		}
		Integer currentColumn = this.cursorLocation.getColumn();
		Integer currentRow = this.cursorLocation.getRow();
		if (currentColumn == 0) {
			if (currentRow == 0) {
				return;
			} else {
				removeFromLoc(this.cursorLocation);
				this.cursorLocation.setRow(currentRow - 1);
				this.cursorLocation.setColumn(lines.get(
						this.cursorLocation.getRow()).length());
			}
		} else {
			removeFromLoc(this.cursorLocation);
			removeFromLoc(new Location(this.cursorLocation.getRow(),
					this.cursorLocation.getColumn() - 1));
			this.cursorLocation.setColumn(currentColumn - 1);
		}
		addCursor(this.cursorLocation);
		notifyTextObs();
	}

	public void deleteAfter() {
		Location cursLoc = new Location(this.cursorLocation);
		TextEditorModel teModel = this;
		if (cursLoc.giveLeft().getColumn() >= 0) {
			EditAction action = new EditAction() {

				private Location oldCursloc = cursLoc.giveRight();
				private TextEditorModel model = teModel;
				private String str = model.atLocation(oldCursloc);

				@Override
				public void executeUndo() {
					if(model.cursorLocation.isNotSameRow(oldCursloc)){
						oldCursloc = oldCursloc.giveLeft();
					}
					model.addToLoc(oldCursloc, str);

					if (oldCursloc
							.isLowerColumnAndSameRow(model.cursorLocation)) {
						model.cursorLocation.setColumn(model.cursorLocation
								.getColumn() + 1);
					}
					model.notifyTextObs();
				}

				@Override
				public void executeDo() {
					model.removeFromLoc(oldCursloc);
					if (oldCursloc
							.isLowerColumnAndSameRow(model.cursorLocation)) {
						model.cursorLocation.setColumn(model.cursorLocation
								.getColumn() - 1);
					}
					model.notifyTextObs();
				}
			};
			UndoManager.instance().push(action);
		}
		Integer currentColumn = this.cursorLocation.getColumn();
		Integer currentRow = this.cursorLocation.getRow();
		if (currentColumn != lines.get(currentRow).length() - 1) {
			removeFromLoc(new Location(this.cursorLocation.getRow(),
					this.cursorLocation.getColumn() + 1));
		}
		notifyCursorObs();
	}

	public void removeFromLoc(Location loc) {
		Integer currentColumn = loc.getColumn();
		Integer currentRow = loc.getRow();
		lines.set(currentRow, lines.get(currentRow).substring(0, currentColumn)
				+ lines.get(currentRow).substring(currentColumn + 1));

	}

	public void addCursor(Location loc) {
		Integer currentColumn = loc.getColumn();
		Integer currentRow = loc.getRow();
		lines.set(currentRow, lines.get(currentRow).substring(0, currentColumn)
				+ "|" + lines.get(currentRow).substring(currentColumn));
	}

	public void addToLoc(Location loc, String str) {
		Integer currentColumn = loc.getColumn();
		Integer currentRow = loc.getRow();
		lines.set(currentRow, lines.get(currentRow).substring(0, currentColumn)
				+ str + lines.get(currentRow).substring(currentColumn));
	}

	public void moveCursorLeft() {
		Integer currentColumn = this.cursorLocation.getColumn();
		Integer currentRow = this.cursorLocation.getRow();
		if (currentColumn == 0) {
			if (currentRow == 0) {
				return;
			} else {
				removeFromLoc(this.cursorLocation);
				this.cursorLocation.setRow(currentRow - 1);
				this.cursorLocation.setColumn(lines.get(
						this.cursorLocation.getRow()).length());
			}
		} else {
			removeFromLoc(this.cursorLocation);
			this.cursorLocation.setColumn(currentColumn - 1);
		}
		addCursor(this.cursorLocation);
		movedWithShiftUp();
		notifyCursorObs();
	}

	public void moveCursorRight() {
		Integer currentColumn = this.cursorLocation.getColumn();
		Integer currentRow = this.cursorLocation.getRow();

		if (currentColumn == lines.get(currentRow).length() - 1) {
			if (currentRow == lines.size() - 1) {
				return;
			} else {
				removeFromLoc(this.cursorLocation);
				this.cursorLocation.setRow(currentRow + 1);
				this.cursorLocation.setColumn(0);
			}
		} else {
			removeFromLoc(this.cursorLocation);
			this.cursorLocation.setColumn(currentColumn + 1);

		}
		addCursor(this.cursorLocation);
		movedWithShiftUp();
		notifyCursorObs();
	}

	public void moveCursorUp() {
		Integer currentRow = this.cursorLocation.getRow();
		Integer currentColumn = this.cursorLocation.getColumn();
		if (currentRow == 0) {
			return;
		} else {
			removeFromLoc(this.cursorLocation);
			this.cursorLocation.setRow(currentRow - 1);
			if (lines.get(this.cursorLocation.getRow()).length() < currentColumn) {
				this.cursorLocation.setColumn(lines.get(
						this.cursorLocation.getRow()).length());
			}
		}
		addCursor(this.cursorLocation);
		movedWithShiftUp();
		notifyCursorObs();
	}

	public void moveCursorDown() {
		Integer currentRow = this.cursorLocation.getRow();
		Integer currentColumn = this.cursorLocation.getColumn();

		if (currentRow == lines.size() - 1) {
			return;
		} else {
			removeFromLoc(this.cursorLocation);
			this.cursorLocation.setRow(currentRow + 1);
			if (lines.get(this.cursorLocation.getRow()).length() < currentColumn) {
				this.cursorLocation.setColumn(lines.get(
						this.cursorLocation.getRow()).length());
			}
		}
		addCursor(this.cursorLocation);
		movedWithShiftUp();
		notifyCursorObs();
	}

	public void notifyCursorObs() {
		for (CursorObserver obs : cursorObservers) {
			obs.updateCursorLocation();
		}
	}

	public void notifyTextObs() {
		for (TextObserver obs : textObservers) {
			obs.updateText();
		}
	}
	
	public void notifySelObs() {
		for (SelectionObserver obs : selectionObservers) {
			obs.updateSelectionAvailability();
		}
	}

	public void attachCO(CursorObserver obs) {
		cursorObservers.add(obs);
	}

	public void dettachCO(CursorObserver obs) {
		cursorObservers.remove(obs);
	}

	public void attachTO(TextObserver obs) {
		textObservers.add(obs);
	}

	public void dettachTO(TextObserver obs) {
		textObservers.remove(obs);
	}
	
	public void attachSO(SelectionObserver obs) {
		selectionObservers.add(obs);
	}

	public void dettachSO(SelectionObserver obs) {
		selectionObservers.remove(obs);
	}

	public TextEditorModel() {
		super();
		cursorObservers = new LinkedList<CursorObserver>();
		textObservers = new LinkedList<TextObserver>();
		selectionObservers = new LinkedList<SelectionObserver>();
		this.clipboard = new ClipboardStack();
		this.shiftHeld = false;
	}

	public Boolean getShiftHeld() {
		return shiftHeld;
	}

	public void setShiftHeld(Boolean shiftHeld) {
		if (shiftHeld.equals(true)) {
			Integer beginColumn = this.cursorLocation.getColumn() + 1;
			if (beginColumn < 0) {
				beginColumn++;
			}
			this.selectionRange.setBegin(new Location(this.cursorLocation
					.getRow(), beginColumn));
			this.setStarLocation(new Location(
					this.getCursorLocation().getRow(), this.getCursorLocation()
							.getColumn() + 1));
			addToLoc(this.starLocation, "*");
		}
		this.shiftHeld = shiftHeld;
		notifySelObs();

	}

	public TextEditorModel(List<String> lines) {
		this();
		if (lines.size() == 0) {
			lines.add("|");
		} else {
			lines.set(0, "|" + lines.get(0));
		}
		this.lines = lines;
		this.selectionRange = new LocationRange();
		this.cursorLocation = new Location();
	}

	public TextEditorModel(List<String> lines, LocationRange range,
			Location cursorLocation) {
		this();
		this.lines = lines;
		this.selectionRange = range;
		this.cursorLocation = cursorLocation;
	}

	public void movedWithShiftUp() {
		if (this.starLocation != null && this.shiftHeld.equals(false)) {
			if (this.starLocation.isLowerColumnAndSameRow(this.cursorLocation)) {
				Integer newColumnStar = this.starLocation.getColumn() - 1;
				Integer newColumnCurs = this.cursorLocation.getColumn() - 1;
				if (newColumnStar < 0) {
					newColumnStar = 0;
				}
				if (newColumnCurs < 0) {
					newColumnCurs = 0;
				}
				this.starLocation.setColumn(newColumnStar);
				this.cursorLocation.setColumn(newColumnCurs);
			} else if (this.starLocation.isNotSameRow(this.cursorLocation)) {
				Integer newColumnStar = this.starLocation.getColumn() - 1;
				if (newColumnStar < 0) {
					newColumnStar = 0;
				}
				this.starLocation.setColumn(newColumnStar);
			}
			removeFromLoc(starLocation);
			this.starLocation = null;
			notifySelObs();
		}
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
		if (lines.size() == 0) {
			lines.add("|");
		} else {
			lines.set(0, "|" + lines.get(0));
		}
		this.lines = lines;
		this.selectionRange = new LocationRange();
		this.cursorLocation = new Location();
	}

	public LocationRange getSelectionRange() {
		return selectionRange;
	}

	public void setSelectionRange(LocationRange selectionRange) {
		this.selectionRange = selectionRange;
	}

	public Location getCursorLocation() {
		return cursorLocation;
	}

	public void setCursorLocation(Location cursorLocation) {
		this.cursorLocation = cursorLocation;
	}

	public Location getStarLocation() {
		return starLocation;
	}

	public void setStarLocation(Location starLocation) {
		this.starLocation = starLocation;
	}

	public ClipboardStack getClipboard() {
		return clipboard;
	}

	public void setClipboard(ClipboardStack clipboard) {
		this.clipboard = clipboard;
	}

	public String atLocation(Location loc) {
		if (loc.getColumn() < 0) {
			return "";
		}
		String line = lines.get(loc.getRow());
		return line.substring(loc.getColumn(), loc.getColumn() + 1);
	}
}
