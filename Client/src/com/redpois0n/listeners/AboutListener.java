package com.redpois0n.listeners;

import com.redpois0n.ui.frames.FrameAbout;

public class AboutListener extends Performable {

	@Override
	public void perform() {
		FrameAbout frame = new FrameAbout();
		frame.setVisible(true);
	}

}
