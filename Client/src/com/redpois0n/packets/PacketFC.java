package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FramePreviewFile;

public class PacketFC extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String file = slave.readLine();
		String what = slave.readLine();
		FramePreviewFile frame = FramePreviewFile.instances.get(file);
		if (frame != null) {
			frame.getPane().getDocument().insertString(frame.getPane().getDocument().getLength(), what + "\n", null);
		}
	}

}
