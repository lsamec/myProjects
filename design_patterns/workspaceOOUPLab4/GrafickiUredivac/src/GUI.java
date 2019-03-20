import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;


public class GUI extends JFrame {
	
	private DocumentModel model;
	private Canvas canvas;
	private JToolBar toolbar;
	private State currentState;
	
	public GUI(List<GraphicalObject> objects){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 500, 400);
		this.setFocusable(true);
		
		this.model = new DocumentModel();
		this.toolbar = new JToolBar();
		this.canvas = new Canvas(this);
		this.currentState = new IdleState();
		LineSegment line = new LineSegment();
		JButton lbutton = new JButton(line.getShapeName());
		lbutton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					currentState.onLeaving();
					currentState = new AddShapeState(model, line);					
				}
		});
		this.toolbar.add(lbutton);
		Triangle tr = new Triangle();
		JButton tbutton = new JButton(tr.getShapeName());
		tbutton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					currentState.onLeaving();
					currentState = new AddShapeState(model, tr);					
				}
		});
		this.toolbar.add(tbutton);
		Oval oval = new Oval();
		JButton obutton = new JButton(oval.getShapeName());
		obutton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					currentState.onLeaving();
					currentState = new AddShapeState(model, oval);					
				}
		});			
		this.toolbar.add(obutton);
		JButton selBut = new JButton("Selektiraj");
		selBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentState.onLeaving();
				currentState = new SelectShapeState(model,canvas);					
			}
		});
		this.toolbar.add(selBut);
		JButton brisBut = new JButton("Brisalo");
		brisBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentState.onLeaving();
				currentState = new EraserState(model,canvas);					
			}
		});
		this.toolbar.add(brisBut);
		JButton svgExpBut = new JButton("SVG export");
		svgExpBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int retrival = fc.showSaveDialog(null);
			    if (retrival == JFileChooser.APPROVE_OPTION) {
			    	String fileName = fc.getSelectedFile().getAbsolutePath();
			    	SVGRendererImpl r = new SVGRendererImpl(fileName);
			    	for(GraphicalObject obj : model.list()){
			    		obj.render(r);
			    	}
			    	r.close();
			    }
			}
		});
		this.toolbar.add(svgExpBut);
		JButton pohraniBut = new JButton("Pohrani");
		pohraniBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int retrival = fc.showSaveDialog(null);
			    if (retrival == JFileChooser.APPROVE_OPTION) {
			    	String fileName = fc.getSelectedFile().getAbsolutePath();
			    	try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
								fileName)));
						List<String> rows = new ArrayList<String>();
						for(GraphicalObject obj : model.list()){
							obj.save(rows);
						}
						for (String line : rows) {
							bw.write(line);
							bw.newLine();
						}
						bw.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
			    }
			}
		});
		this.toolbar.add(pohraniBut);
		JButton ucitajBut = new JButton("Ucitaj");
		ucitajBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String,GraphicalObject> objMap = new HashMap<String,GraphicalObject>();
				Stack<GraphicalObject> stack = new Stack<GraphicalObject>();
				LineSegment line =  new LineSegment();
				Oval oval = new Oval();
				Triangle tr = new Triangle();
				CompositeShape comp = new CompositeShape();
				objMap.put(line.getShapeID(),line);
				objMap.put(oval.getShapeID(), oval);
				objMap.put(comp.getShapeID(), comp);
				objMap.put(tr.getShapeID(), tr);
				JFileChooser fc = new JFileChooser();
				int retrival = fc.showOpenDialog(null);
			    if (retrival == JFileChooser.APPROVE_OPTION) {
			    	String fileName = fc.getSelectedFile().getAbsolutePath();
			    	try {
						List<String> lines = Files.readAllLines(Paths.get(fileName));
						for(String aLine : lines){
							String id = aLine.substring(0, aLine.indexOf(" "));
							GraphicalObject obj = objMap.get(id);
							String info = aLine.substring(aLine.indexOf(" ")+1);
							obj.load(stack, info);							
						}
						model.clear();
						for(GraphicalObject obj : stack){
							model.addGraphicalObject(obj);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
			    	repaint();
			    }
			}
		});
		this.toolbar.add(ucitajBut);
		
		for(GraphicalObject obj : objects){
			this.model.addGraphicalObject(obj);
		}
		
		this.add(this.toolbar, BorderLayout.PAGE_START);
		this.add(this.canvas,BorderLayout.CENTER);	
		
	}

	public DocumentModel getModel() {
		return model;
	}

	public void setModel(DocumentModel model) {
		this.model = model;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public JToolBar getToolbar() {
		return toolbar;
	}

	public void setToolbar(JToolBar toolbar) {
		this.toolbar = toolbar;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}
