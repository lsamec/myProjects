import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class SVGRendererImpl implements Renderer {

	private List<String> lines = new ArrayList<String>();
	private String fileName;

	public SVGRendererImpl(String fileName) {
		// zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
		// <svg xmlns=... >
		// ...
		this.fileName = fileName;
		String line = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">";
		lines.add(line);

	}

	public void close(){
		// u lines još dodaj završni tag SVG dokumenta: </svg>
		// sve retke u listi lines zapiši na disk u datoteku
		// ...
		String endLine = "</svg>";
		lines.add(endLine);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					this.fileName)));
			for (String line : this.lines) {
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void drawLine(Point s, Point e) {
		// Dodaj u lines redak koji definira linijski segment:
		// <line ... />
		String line = "<line x1=\"" + Integer.toString(s.getX()) + "\" y1=\""
				+ Integer.toString(s.getY()) + "\" x2=\""
				+ Integer.toString(e.getX()) + "\" y2=\""
				+ Integer.toString(e.getY()) + "\" style=\"stroke:blue;\"/>";
		lines.add(line);
	}

	@Override
	public void fillPolygon(Point[] points) {
		// Dodaj u lines redak koji definira popunjeni poligon:
		// <polygon points="..." style="stroke: ...; fill: ...;" />
		String line = "<polygon points=\"";
		for (Point p : points) {
			line += Integer.toString(p.getX()) + ","
					+ Integer.toString(p.getY()) + " ";
		}
		line = line.substring(0, line.length()-1);
		line += "\" style=\"stroke:red; fill:blue; stroke-width: 2;\"/>   ";
		lines.add(line);
	}

}
