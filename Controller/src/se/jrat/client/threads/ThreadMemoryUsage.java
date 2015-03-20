package se.jrat.client.threads;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet33UsedMemory;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.panels.PanelMemoryUsage;

public class ThreadMemoryUsage extends Thread {

	public Slave slave;

	public ThreadMemoryUsage(Slave slave) {
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
