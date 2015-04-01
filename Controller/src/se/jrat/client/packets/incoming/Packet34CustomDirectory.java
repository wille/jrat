package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet15ListFiles;
import se.jrat.client.ui.frames.FrameRemoteFiles;


public class Packet34CustomDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(slave);
		if (frame != null) {
			frame.remoteTable.setDirectory(where);
			while (frame.remoteTable.getTableModel().getRowCount() > 0) {
				frame.remoteTable.getTableModel().removeRow(0);
			}
			slave.addToSendQueue(new Packet15ListFiles(where));
		}
	}

}
