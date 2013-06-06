package com.redpois0n.packets;

import java.util.ArrayList;
import java.util.List;

import com.redpois0n.Connection;
import com.redpois0n.common.exceptions.PacketAlreadySentException;

public class PacketBuilder {

	private final String header;
	private List<Object> list;
	private boolean sent;

	public PacketBuilder(Header header) {
		this.header = header.getHeader();
	}

	public PacketBuilder(Header header, Object extra) {
		this(header, new Object[] { extra });
	}

	public PacketBuilder(Header header, Object[] extra) {
		String h = header.getHeader();

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

			Connection.writeLine(header);

			for (int i = 0; list != null && i < list.size(); i++) {
				Object obj = list.get(i);

				if (obj instanceof Integer) {
					Connection.writeInt((Integer) obj);
				} else if (obj instanceof Long) {
					Connection.writeLong((Long) obj);
				} else if (obj instanceof Boolean) {
					Connection.writeBoolean((Boolean) obj);
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