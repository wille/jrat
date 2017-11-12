package jrat.module.fs.packets.download;

import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.PanelFileTransfers;

import java.io.File;

public class Packet29DownloadPart implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String remotePath = slave.readLine();
		int len = slave.readInt();

		byte[] buffer = new byte[len];
        slave.readFully(buffer);

		TransferData data = FileCache.get(remotePath);

		if (data != null) {
			File output = data.getLocalFile();

			if (output.isDirectory()) {
				output = new File(output, remotePath.substring(remotePath.lastIndexOf(slave.getFileSeparator()) + 1, remotePath.length()));
			}

			data.getOutputStream().write(buffer);
			data.increaseRead(buffer.length);
			PanelFileTransfers.instance.repaint();
		}
	}

}
