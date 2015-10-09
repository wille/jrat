package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.common.utils.Utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.DataOutputStream;


public class Packet18InitMonitors extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		if (!Utils.isHeadless()) {		
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
		} else {
			dos.writeInt(0);
		}
	}

	@Override
	public byte getPacketId() {
		return (byte) 18;
	}

}
