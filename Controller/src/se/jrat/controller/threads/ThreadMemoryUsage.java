package se.jrat.controller.threads;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet33UsedMemory;
import se.jrat.controller.ui.frames.FrameControlPanel;
import se.jrat.controller.ui.panels.PanelMemoryUsage;

public class ThreadMemoryUsage extends Thread {

	public Slave slave;

	public ThreadMemoryUsage(Slave slave) {
		super("Memory usage update thread");
		this.slave = slave;
	}

	public void run() {
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelMemoryUsage panel = (PanelMemoryUsage) frame.panels.get("memory usage");
		while (frame != null && panel != null && panel.shouldSend() && panel.slave.getIP().equals(slave.getIP())) {
			slave.addToSendQueue(new Packet33UsedMemory());
			try {
				Thread.sleep(100L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
