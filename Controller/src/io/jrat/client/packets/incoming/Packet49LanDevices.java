package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlLANScan;

import java.io.DataInputStream;


public class Packet49LanDevices extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String first = slave.readLine();
		String second;

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlLANScan panel = null;

		if (frame != null) {
			panel = (PanelControlLANScan) frame.panels.get("lan computers");
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
