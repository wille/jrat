package jrat.module.system.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.system.ui.PanelStartup;


public class Packet53RegistryStartup implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int count = slave.readInt();
		String[] args = new String[count];
		for (int i = 0; i < count; i++) {
			args[i] = slave.readLine();
		}

        PanelStartup panel = (PanelStartup) slave.getPanel(PanelStartup.class);

		if (panel != null && count == 3) {
			panel.getModel().addRow(new Object[] { PanelStartup.ICON, args[0], args[1], args[2] });
		}
	}

}
