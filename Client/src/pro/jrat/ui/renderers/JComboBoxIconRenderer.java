package pro.jrat.ui.renderers;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class JComboBoxIconRenderer extends DefaultListCellRenderer {

	private HashMap<String, Icon> map = new HashMap<String, Icon>();

	public JComboBoxIconRenderer() {

	}

	public JComboBoxIconRenderer(HashMap<String, Icon> map) {
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
		if (value != null && map.containsKey(value.toString().toLowerCase())) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Icon icon = map.get(value.toString().toLowerCase());
			label.setIcon(icon);
			return label;
		}
		return this;
	}

}
