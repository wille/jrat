package jrat.controller.ui.renderers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class JComboBoxIconRenderer extends DefaultListCellRenderer {

	private Map<String, Icon> map = new HashMap<>();

	public JComboBoxIconRenderer() {

	}

	public JComboBoxIconRenderer(Map<String, Icon> map) {
		this.map = map;
	}

	public void addIcon(String text, Icon icon) {
		map.put(text, icon);
	}

	public void removeIcon(String text) {
		map.remove(text);
	}

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if (value != null && map.containsKey(value.toString())) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Icon icon = map.get(value.toString());
			label.setIcon(icon);
			return label;
		}
		return this;
	}

}
