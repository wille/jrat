package pro.jrat.listeners;

import pro.jrat.ui.frames.FrameAbout;

public class AboutListener extends Performable {

	@Override
	public void perform() {
		FrameAbout frame = new FrameAbout();
		frame.setVisible(true);
	}

}
