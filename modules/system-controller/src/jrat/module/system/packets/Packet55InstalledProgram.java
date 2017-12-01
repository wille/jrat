package jrat.module.system.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.system.ui.PanelApplications;


public class Packet55InstalledProgram implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String program = slave.readLine();

        PanelApplications panel = (PanelApplications) slave.getPanel(PanelApplications.class);

		if (panel != null) {
			panel.getModel().addRow(new Object[] { program });
		}
	}

}
