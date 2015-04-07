package se.jrat.controller.ui.renderers;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import se.jrat.controller.ui.panels.PanelThumbView;


@SuppressWarnings("serial")
public class ThumbsListRenderer extends DefaultListCellRenderer {

	private PanelThumbView frame;

	public ThumbsListRenderer(PanelThumbView frame) {
		this.frame = frame;
	}

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		String val = value.toString();

		if (frame.getThumbMap().containsKey(val)) {
			setIcon(frame.getThumbMap().get(val));
		} else {
			setIcon(null);
		}

		if (val.contains(frame.getSlave().getFileSeparator())) {
			setText(new File(val).getName());
		} else {
			setText(val);
		}

		return this;
	}

}
