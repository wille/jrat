package com.redpois0n.listeners;

import com.redpois0n.ui.frames.FrameEULA;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		FrameEULA frame = new FrameEULA(true);
		frame.setVisible(true);
	}

}
