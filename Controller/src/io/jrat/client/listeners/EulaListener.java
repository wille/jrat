package io.jrat.client.listeners;

import io.jrat.client.ui.frames.FrameEULA;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		FrameEULA frame = new FrameEULA(true);
		frame.setVisible(true);
	}

}
