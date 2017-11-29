package jrat.module.system.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.system.ui.PanelStartup;


public class Packet53RegistryStartup implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String path = slave.readLine();

        PanelStartup panel = (PanelStartup) slave.getPanel(PanelStartup.class);

		if (panel != null) {
			panel.getModel().addRow(new Object[] { PanelStartup.ICON, path });
		}
	}

}
