package se.jrat.client.threads;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet33RAM;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.panels.PanelControlPerformance;

public class ThreadSystemMonitor extends Thread {

	public Slave slave;

	public ThreadSystemMonitor(Slave slave) {
		this.slave = slave;
	}

	public void run() {
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlPerformance panel = (PanelControlPerformance) frame.panels.get("system monitor");
		while (frame != null && panel != null && panel.shouldSend() && panel.slave.getIP().equals(slave.getIP())) {
			slave.addToSendQueue(new Packet33RAM());
			try {
				Thread.sleep(100L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
