package pro.jrat.ui.renderers;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import pro.jrat.utils.ImageUtils;



@SuppressWarnings("serial")
public class JColorBoxRenderer extends DefaultListCellRenderer {

	public HashMap<String, ImageIcon> map = new HashMap<String, ImageIcon>();

	public JColorBoxRenderer() {
		map.clear();
		map.put("black", ImageUtils.generate(Color.black));
		map.put("blue", ImageUtils.generate(Color.blue));
		map.put("cyan", ImageUtils.generate(Color.cyan));
		map.put("dark gray", ImageUtils.generate(Color.darkGray));
		map.put("gray", ImageUtils.generate(Color.gray));
		map.put("green", ImageUtils.generate(Color.green));
		map.put("dark green", ImageUtils.generate(Color.green.darker()));
		map.put("light gray", ImageUtils.generate(Color.lightGray));
		map.put("magenta", ImageUtils.generate(Color.magenta));
		map.put("orange", ImageUtils.generate(Color.orange));
		map.put("pink", ImageUtils.generate(Color.pink));
		map.put("red", ImageUtils.generate(Color.red));
		map.put("white", ImageUtils.generate(Color.white));
		map.put("yellow", ImageUtils.generate(Color.yellow));
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
