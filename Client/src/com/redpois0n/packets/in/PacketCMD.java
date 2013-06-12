package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteShell;

public class PacketCMD extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String command = slave.readLine();
		FrameRemoteShell frame = FrameRemoteShell.instances.get(slave);
		if (frame != null && frame.slave != null && frame.slave.getIP().equals(slave.getIP())) {
			frame.textPane.setText(frame.textPane.getText() + command + "\n");
			int x;
			frame.textPane.selectAll();
			x = frame.textPane.getSelectionEnd();
			frame.textPane.select(x, x);
		}

	}

}
