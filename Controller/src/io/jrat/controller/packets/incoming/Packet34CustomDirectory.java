package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameRemoteFiles;

import java.io.DataInputStream;

public class Packet34CustomDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(slave);
		if (frame != null) {
			frame.getFilesPanel().getRemoteTable().setDirectory(where);
		}
	}

}
