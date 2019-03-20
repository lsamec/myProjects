import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class DocumentModel {

	public final static double SELECTION_PROXIMITY = 10;

	// Kolekcija svih grafièkih objekata:
	private List<GraphicalObject> objects = new ArrayList<GraphicalObject>();
	// Read-Only proxy oko kolekcije grafièkih objekata:
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
	// Kolekcija prijavljenih promatraèa:
	private List<DocumentModelListener> listeners = new ArrayList<DocumentModelListener>();
	// Kolekcija selektiranih objekata:
	private List<GraphicalObject> selectedObjects = new ArrayList<>();
	// Read-Only proxy oko kolekcije selektiranih objekata:
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

	// Promatraè koji æe biti registriran nad svim objektima crteža
	private final GraphicalObjectListener goListener = new GraphicalObjectListener(){
		@Override
		public void graphicalObjectChanged(GraphicalObject go) {			
		}

		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject go) {			
		}		
	};
	
	// Konstruktor
	public DocumentModel() {
	}
	
	public void addObjectToSelection(GraphicalObject obj){
		this.selectedObjects.add(obj);
	}
	
	public void clearSelObj(){
		for(GraphicalObject obj : this.selectedObjects){
			obj.setSelected(false);
		}
		this.selectedObjects.clear();
		this.notifyListeners();
	}
	
	// Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
	// i potom obavijeste svi promatraèi modela
	public void clear() {
		for(GraphicalObject obj : this.objects){
			obj.removeGraphicalObjectListener(goListener);
		}
		this.objects.clear();
		this.selectedObjects.clear();
		this.notifyListeners();
	}

	// Dodavanje objekta u dokument (pazite je li veæ selektiran; registrirajte model kao promatraèa)
	public void addGraphicalObject(GraphicalObject obj) {
		obj.addGraphicalObjectListener(goListener);
		this.objects.add(obj);
		if(obj.isSelected()){
			this.selectedObjects.add(obj);
		}
	}
	
	// Uklanjanje objekta iz dokumenta (pazite je li veæ selektiran; odregistrirajte model kao promatraèa)
	public void removeGraphicalObject(GraphicalObject obj) {
		obj.removeGraphicalObjectListener(goListener);
		this.objects.remove(obj);
		if(obj.isSelected()){
			this.selectedObjects.remove(obj);
		}
	}

	// Vrati nepromjenjivu listu postojeæih objekata (izmjene smiju iæi samo kroz metode modela)
	public List<GraphicalObject> list() {
		return this.roObjects;
	}

	// Prijava
	public void addDocumentModelListener(DocumentModelListener l) {
		this.listeners.add(l);
	}
	
	// Odjava
	public void removeDocumentModelListener(DocumentModelListener l) {
		this.listeners.remove(l);
	}

	// Obavještavanje
	public void notifyListeners() {
		for(DocumentModelListener l : this.listeners){
			l.documentChange();
		}
		
	}
	
	// Vrati nepromjenjivu listu selektiranih objekata
	public List<GraphicalObject> getSelectedObjects() {
		return this.roSelectedObjects;
	}

	// Pomakni predani objekt u listi objekata na jedno mjesto kasnije
	// Time æe se on iscrtati kasnije (pa æe time možda veæi dio biti vidljiv)
	public void increaseZ(GraphicalObject go) {
		int index = this.objects.indexOf(go);
		if (index == this.objects.size()-1){
			return;
		}
		this.objects.add(index+2,go);
		this.objects.remove(index);
	}
	
	// Pomakni predani objekt u listi objekata na jedno mjesto ranije
	public void decreaseZ(GraphicalObject go) {
		int index = this.objects.indexOf(go);
		if(index == 0){
			return;
		}
		this.objects.add(index-1,go);
		this.objects.remove(index+1);
	}
	
	// Pronaði postoji li u modelu neki objekt koji klik na toèku koja je
	// predana kao argument selektira i vrati ga ili vrati null. Toèka selektira
	// objekt kojemu je najbliža uz uvjet da ta udaljenost nije veæa od
	// SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		Map<Integer,Double> mapDistance = new HashMap<Integer, Double>();
		int size= this.objects.size();
		for(int i =0;i<size;i++){
			mapDistance.put(i, this.objects.get(i).selectionDistance(mousePoint));
		}
		GraphicalObject nearest = null;
		Double distance = null; 
		for(Entry<Integer,Double> entry : mapDistance.entrySet()){
			if(distance == null && entry.getValue() <= SELECTION_PROXIMITY){
				distance = entry.getValue();
				nearest = this.objects.get(entry.getKey());
			}else if (distance != null && entry.getValue() < distance){
				distance = entry.getValue();
				nearest = this.objects.get(entry.getKey());
			}
		}
		return nearest;
	}

	// Pronaði da li u predanom objektu predana toèka miša selektira neki hot-point.
	// Toèka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
	// udaljenost nije veæa od SELECTION_PROXIMITY. Vraæa se indeks hot-pointa 
	// kojeg bi predana toèka selektirala ili -1 ako takve nema. Status selekcije 
	// se pri tome NE dira.
	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
		Map<Integer,Double> mapDistance = new HashMap<Integer, Double>();
		int size= object.getNumberOfHotPoints();
		for(int i =0;i<size;i++){
			mapDistance.put(i, GeometryUtil.distanceFromPoint(mousePoint, object.getHotPoint(i)));
		}
		Integer nearest = -1;
		Double distance = null; 
		for(Entry<Integer,Double> entry : mapDistance.entrySet()){
			if(distance == null && entry.getValue() <= SELECTION_PROXIMITY){
				distance = entry.getValue();
				nearest = entry.getKey();
			}else if (distance != null && entry.getValue() < distance){
				distance = entry.getValue();
				nearest = entry.getKey();
			}
		}
		return nearest;
	}

}
