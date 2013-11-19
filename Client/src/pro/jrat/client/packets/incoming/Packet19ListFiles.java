package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameRemoteFiles;
import pro.jrat.client.utils.IconUtils;

public class Packet19ListFiles extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteFiles fr = FrameRemoteFiles.instances.get(slave);
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
				String size = (dis.readLong() / 1024L) + " kB";
				boolean hidden = dis.readBoolean();

				if (line != null) {
					files.add(new Object[] { line, size, date, hidden ? "Yes" : "" });
				}
			}
		}

		if (fr != null) {
			while (fr.model.getRowCount() > 0) {
				fr.model.removeRow(0);
			}

			for (Object[] string : dirs) {
				try {
					fr.renderer.icons.put(string[0].toString(), IconUtils.getFileIconFromExtension(string[0].toString(), true));
					fr.model.addRow(new Object[] { string[0], string[1], "", string[3] });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (Object[] string : files) {
				try {
					fr.renderer.icons.put(string[0].toString(), IconUtils.getFileIconFromExtension(string[0].toString(), false));
					fr.model.addRow(new Object[] { string[0], string[1], string[2], string[3] });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
