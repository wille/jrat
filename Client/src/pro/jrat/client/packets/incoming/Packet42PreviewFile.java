package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FramePreviewFile;

public class Packet42PreviewFile extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		String what = slave.readLine();
		FramePreviewFile frame = FramePreviewFile.instances.get(file);
		if (frame != null) {
			frame.getPane().getDocument().insertString(frame.getPane().getDocument().getLength(), what + "\n", null);
		}
	}

}
