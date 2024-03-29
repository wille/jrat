package jrat.controller.ui.renderers;

import jrat.common.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


@SuppressWarnings("serial")
public class JColorBoxRenderer extends DefaultListCellRenderer {

	public HashMap<String, ImageIcon> map = new HashMap<>();

	public JColorBoxRenderer() {
		map.clear();
		map.put("default", ImageUtils.generateColorBoxSquare(Color.black));
		map.put("black", ImageUtils.generateColorBoxSquare(Color.black));
		map.put("blue", ImageUtils.generateColorBoxSquare(Color.blue));
		map.put("cyan", ImageUtils.generateColorBoxSquare(Color.cyan));
		map.put("dark gray", ImageUtils.generateColorBoxSquare(Color.darkGray));
		map.put("gray", ImageUtils.generateColorBoxSquare(Color.gray));
		map.put("green", ImageUtils.generateColorBoxSquare(Color.green));
		map.put("dark green", ImageUtils.generateColorBoxSquare(Color.green.darker()));
		map.put("light gray", ImageUtils.generateColorBoxSquare(Color.lightGray));
		map.put("magenta", ImageUtils.generateColorBoxSquare(Color.magenta));
		map.put("orange", ImageUtils.generateColorBoxSquare(Color.orange));
		map.put("pink", ImageUtils.generateColorBoxSquare(Color.pink));
		map.put("red", ImageUtils.generateColorBoxSquare(Color.red));
		map.put("white", ImageUtils.generateColorBoxSquare(Color.white));
		map.put("yellow", ImageUtils.generateColorBoxSquare(Color.yellow));
	}

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (map.containsKey(value.toString().toLowerCase())) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(map.get(value.toString().toLowerCase()));
			return label;
		}
		return this;
	}

}
