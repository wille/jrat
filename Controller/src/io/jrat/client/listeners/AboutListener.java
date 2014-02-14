package io.jrat.client.listeners;

import io.jrat.client.ui.frames.FrameAbout;

public class AboutListener extends Performable {

	@Override
	public void perform() {
		FrameAbout frame = new FrameAbout();
		frame.setVisible(true);
	}

}
