package com.redpois0n;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.redpois0n.io.Files;
import com.redpois0n.ui.components.JColorBox;

public class Colors {
	
	private static HashMap<String, ColorProfile> colors = new HashMap<String, ColorProfile>();
	
	@SuppressWarnings("unchecked")
	public static void load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(Files.getSettings(), "colors.dat")));
			colors = (HashMap<String, ColorProfile>) in.readObject();
			in.close();
			if (colors.size() == 0) {
				putDefault();
			}
		} catch (FileNotFoundException ex) {
			putDefault();
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
 	}
	
	public static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(Files.getSettings(), "colors.dat")));
			out.writeObject(colors);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
	}
	
	public static void put(String str, ColorProfile col) {
		colors.put(str, col);
	}
	
	public static ColorProfile get(String str) {
		return colors.get(str);
	}
	
	public static Color getColorFromIndex(int index) {
		return getColorFromString(JColorBox.colors[index]);
	}
	
	public static Color getColorFromString(String text) {
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
	
	public static void putDefault() {
		colors.clear();
		colors.put("system monitor", ColorProfile.generate("blue"));
		colors.put("outdated servers", ColorProfile.generate("red"));
	}

}
