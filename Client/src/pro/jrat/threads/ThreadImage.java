package pro.jrat.threads;

import pro.jrat.ScreenCommands;
import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameRemoteScreen;

public class ThreadImage extends Thread {

	public long sleep;
	public Slave slave;

	public ThreadImage(long sleep, Slave slave) {
		super("Image thread");
		this.sleep = sleep;
		this.slave = slave;
	}

	public void run() {
		try {
			Thread.sleep(100L);
			Thread.sleep(sleep);
			FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
			if (frame != null && frame.running && frame.slave.getIP().equals(slave.getIP())) {
				frame.clearSize();
				ScreenCommands.send(slave, frame.getPercentSize(), frame.monitorindex, frame.rows, frame.cols);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
