package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlLoadedPlugins;


public class Packet67LoadedPlugins extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = slave.readInt();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		PanelControlLoadedPlugins panel = (PanelControlLoadedPlugins) frame.panels.get("view installed plugins");
		
		if (panel != null) {
			for (int i = 0; i < len; i++) {
				String name = slave.readLine();
				
				panel.getModel().addRow(new Object[] { name });
			}
		}
	}
	
}
