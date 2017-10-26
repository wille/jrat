package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.fs.ui.FramePreviewFile;


public class Packet42PreviewFile extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String file = slave.readLine();
		String what = slave.readLine();
		FramePreviewFile frame = FramePreviewFile.INSTANCES.get(file);
		if (frame != null) {
			frame.getPane().getDocument().insertString(frame.getPane().getDocument().getLength(), what + "\n", null);
		}
	}

}
