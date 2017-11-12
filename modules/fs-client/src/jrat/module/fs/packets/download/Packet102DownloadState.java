package jrat.module.fs.packets.download;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

public class Packet102DownloadState implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
	    TransferData.State action = TransferData.State.values()[con.readByte()];
		String file = con.readLine();

        switch (action) {
            case COMPLETED:
                FileCache.remove(file);
                break;
            case CANCELLED:
                TransferData localData = FileCache.get(file);

                if (localData != null) {
                    localData.getOutputStream().close();
                    FileCache.remove(localData);
                    localData.getRunnable().interrupt();
                }
                break;
            case PAUSED:
                FileCache.get(file).getRunnable().pause();
                break;
        }
	}

}
