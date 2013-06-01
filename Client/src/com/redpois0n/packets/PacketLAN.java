package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlLANComputers;

public class PacketLAN extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String first = slave.readLine();
		String second;
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);	
		PanelControlLANComputers panel = null;
		
		if (frame != null) {
			panel = (PanelControlLANComputers) frame.panels.get("lan computers");
		}
		
		if (first.equals("DONE")) {
			if (panel != null) {
				panel.done();
			}
			return;
		} else if (first.equals("FAIL")) {
			if (panel != null) {
				panel.fail();
			}
			return;
		} else {
			second = slave.readLine();
		}
		
		if (frame != null) {
			panel.getModel().addRow(new Object[] { second, first });
			panel.getLabel().setText("Received " + second + " - " + first);
		}
	}

}
