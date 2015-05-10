package jrat.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrat.api.net.PacketListener;

public class Packet {
	
	private static final Map<Byte, List<PacketListener>> PACKETS_INCOMING = new HashMap<Byte, List<PacketListener>>();
	private static final Map<Byte, List<PacketListener>> PACKETS_OUTGOING = new HashMap<Byte, List<PacketListener>>();
	
	public static void registerIncoming(byte type, PacketListener listener) {
		if (!PACKETS_INCOMING.containsKey(type)) {
			PACKETS_INCOMING.put(type, new ArrayList<PacketListener>());
		}
		
		List<PacketListener> list = PACKETS_INCOMING.get(type);
		
		list.add(listener);
	}
	
	public static void unregisterIncoming(byte header, PacketListener listener) {
		if (PACKETS_INCOMING.containsKey(header)) {
			PACKETS_INCOMING.get(header).remove(listener);
		}
	}
	
	public static List<PacketListener> getIncoming(byte header) {
		if (!PACKETS_INCOMING.containsKey(header)) {
			PACKETS_INCOMING.put(header, new ArrayList<PacketListener>());
		}
		
		return PACKETS_INCOMING.get(header);
	}
	
	public static void registerOutgoing(byte type, PacketListener listener) {
		if (!PACKETS_OUTGOING.containsKey(type)) {
			PACKETS_OUTGOING.put(type, new ArrayList<PacketListener>());
		}
		
		List<PacketListener> list = PACKETS_OUTGOING.get(type);
		
		list.add(listener);
	}
	
	public static void unregisterOutgoing(byte header, PacketListener listener) {
		if (PACKETS_OUTGOING.containsKey(header)) {
			PACKETS_OUTGOING.get(header).remove(listener);
		}
	}
	
	public static List<PacketListener> getOutgoing(byte header) {
		if (!PACKETS_OUTGOING.containsKey(header)) {
			PACKETS_OUTGOING.put(header, new ArrayList<PacketListener>());
		}
		
		return PACKETS_OUTGOING.get(header);
	}
	
	private final byte header;

	public Packet(byte header) {
		this.header = header;
	}

	/**
	 * 
	 * @return The header
	 */
	public byte getHeader() {
		return header;
	}

	/**
	 * 
	 * @return Packet-getHeader()
	 */
	@Override
	public String toString() {
		return "Packet-" + this.getHeader();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Packet) {
			return ((Packet) o).getHeader() == this.header;
		} else {
			return this == o;
		}
	}

}
