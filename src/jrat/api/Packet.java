package jrat.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrat.api.net.PacketListener;

public class Packet {
	
	private static final Map<Byte, List<PacketListener>> map = new HashMap<Byte, List<PacketListener>>();
	
	public static void register(byte type, PacketListener listener) {
		if (!map.containsKey(type)) {
			map.put(type, new ArrayList<PacketListener>());
		}
		
		List<PacketListener> list = map.get(type);
		
		list.add(listener);
	}
	
	public static void unregister(byte header, PacketListener listener) {
		if (map.containsKey(header)) {
			map.get(header).remove(listener);
		}
	}
	
	public static List<PacketListener> getEvents(byte header) {
		if (!map.containsKey(header)) {
			map.put(header, new ArrayList<PacketListener>());
		}
		
		return map.get(header);
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
