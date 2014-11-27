package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameRemoteShell;

import java.io.DataInputStream;


public class Packet21RemoteShell extends AbstractIncomingPacket {

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
