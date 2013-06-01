package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlHostsFile;

public class PacketHOSTF extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String host = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlHostsFile panel = (PanelControlHostsFile) frame.panels.get("hosts file");
			panel.getPane().setText(host);
		}
	}

}
