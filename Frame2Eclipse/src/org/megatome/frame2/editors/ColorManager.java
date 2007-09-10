package org.megatome.frame2.editors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {

	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	public void dispose() {
		for (Color color : this.fColorTable.values()) {
			color.dispose();
		}
	}

	public Color getColor(final RGB rgb) {
		Color color = this.fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			this.fColorTable.put(rgb, color);
		}
		return color;
	}
}
