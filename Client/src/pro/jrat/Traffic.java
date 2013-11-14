package pro.jrat;

import pro.jrat.ui.frames.FrameTraffic;

public class Traffic {

	public static void increaseSent(Slave sl, int bytes) {
		sl.setSent(sl.getSent() + bytes);

		String text = null;
		long display = 0;

		if (sl.getSent() >= 1024) {
			text = "kb";
			display = sl.getSent() / 1024;
		} else if (sl.getSent() >= 1024 * 1024) {
			text = "mb";
			display = sl.getSent() / 1024 / 1024;
		} else {
			text = "bytes";
			display = sl.getSent();
		}

		FrameTraffic frame = FrameTraffic.instances.get(sl);
		if (frame != null) {
			frame.getSent().setText(display + " " + text);
		}
	}

	public static void increaseReceived(Slave sl, int bytes) {
		sl.setReceived(sl.getReceived() + bytes);

		String text = null;
		long display = 0;

		if (sl.getReceived() >= 1024) {
			text = "kb";
			display = sl.getReceived() / 1024;
		} else if (sl.getReceived() >= 1024 * 1024) {
			text = "mb";
			display = sl.getReceived() / 1024 / 1024;
		} else {
			text = "bytes";
			display = sl.getReceived();
		}

		FrameTraffic frame = FrameTraffic.instances.get(sl);
		if (frame != null) {
			frame.getReceived().setText(display + " " + text);
		}
	}

}
