package jrat.module.system;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.module.system.ui.PanelMemoryUsage;

public class ThreadMemoryUsage extends Thread {

	public Slave slave;

	public ThreadMemoryUsage(Slave slave) {
		super("Memory usage update thread");
		this.slave = slave;
	}

	public void run() {
		while (true) {
			PanelMemoryUsage panel = (PanelMemoryUsage) slave.getPanel(PanelMemoryUsage.class);
			
			if (panel != null) {
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
