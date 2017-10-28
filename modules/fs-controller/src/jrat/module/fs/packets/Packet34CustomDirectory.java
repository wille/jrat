package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;

public class Packet34CustomDirectory implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		FrameRemoteFiles frame = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);
		if (frame != null) {
			frame.getFilesPanel().getRemoteTable().setDirectory(where);
		}
	}

}
