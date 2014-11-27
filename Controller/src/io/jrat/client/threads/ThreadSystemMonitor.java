package io.jrat.client.threads;

import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet33RAM;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlPerformance;

public class ThreadSystemMonitor extends Thread {

	public Slave slave;

	public ThreadSystemMonitor(Slave slave) {
		this.slave = slave;
	}

	public void run() {
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlPerformance panel = (PanelControlPerformance) frame.panels.get("system monitor");
		while (frame != null && panel != null && panel.needRam && panel.slave.getIP().equals(slave.getIP())) {
			slave.addToSendQueue(new Packet33RAM());
			try {
				if (panel.sliderRam.getValue() < 1) {
					Thread.sleep(100L);
				} else {
					Thread.sleep(panel.sliderRam.getValue() * 1000L);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
