package com.redpois0n.ui.renderers;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.redpois0n.ui.frames.FrameRemoteThumbView;


@SuppressWarnings("serial")
public class ThumbsListRenderer extends DefaultListCellRenderer {
	
	private FrameRemoteThumbView frame;
	
	public ThumbsListRenderer(FrameRemoteThumbView frame) {
		this.frame = frame;
	}
	
	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		String val = value.toString();

		if (frame.thumbs.containsKey(val)) {
			setIcon(frame.thumbs.get(val));
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
