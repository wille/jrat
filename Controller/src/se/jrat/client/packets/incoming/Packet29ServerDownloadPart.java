package se.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.client.ui.panels.PanelFileTransfer;
import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;

public class Packet29ServerDownloadPart extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String remotePath = slave.readLine();
		int len = dis.readInt();

		byte[] buffer = new byte[len];
		dis.readFully(buffer);

		TransferData data = FileCache.get(remotePath);

		File output = data.getLocalFile();

		if (output.isDirectory()) {
			output = new File(output, remotePath.substring(remotePath.lastIndexOf(slave.getFileSeparator()) + 1, remotePath.length()));
		}

		data.getOutputStream().write(buffer);
		data.increaseRead(buffer.length);
		PanelFileTransfer.instance.repaint();
	}

}
