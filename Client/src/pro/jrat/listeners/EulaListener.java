package pro.jrat.listeners;

import pro.jrat.ui.frames.FrameEULA;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		FrameEULA frame = new FrameEULA(true);
		frame.setVisible(true);
	}

}
