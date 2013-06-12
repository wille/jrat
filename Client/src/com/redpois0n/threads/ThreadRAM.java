package com.redpois0n.threads;

import com.redpois0n.Slave;
import com.redpois0n.packets.outgoing.Header;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlSystemMonitor;

public class ThreadRAM extends Thread {

	public Slave slave;

	public ThreadRAM(Slave slave) {
		this.slave = slave;
	}

	public void run() {
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlSystemMonitor panel = (PanelControlSystemMonitor) frame.panels.get("system monitor");
		while (frame != null && panel != null && panel.needRam && panel.slave.getIP().equals(slave.getIP())) {
			slave.addToSendQueue(Header.GET_RAM);
			try {
				if (panel.sliderRam.getValue() <= 1) {			
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
