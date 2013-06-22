package pro.jrat.threads;

import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet33RAM;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlSystemMonitor;

public class ThreadRAM extends Thread {

	public Slave slave;

	public ThreadRAM(Slave slave) {
		this.slave = slave;
	}

	public void run() {
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlSystemMonitor panel = (PanelControlSystemMonitor) frame.panels.get("system monitor");
		while (frame != null && panel != null && panel.needRam && panel.slave.getIP().equals(slave.getIP())) {
			slave.addToSendQueue(new Packet33RAM());
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
