package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Transfer;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

public class Packet102ClientDownload implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
	    int action = con.readByte();
		String file = con.readLine();

        switch (action) {
            case Transfer.Complete:
                break;
            case Transfer.CANCEL:
                TransferData localData = FileCache.get(file);

                if (localData != null) {
                    localData.getOutputStream().close();
                    FileCache.remove(localData);
                    localData.getRunnable().interrupt();
                }
                break;
            case Transfer.PAUSE:
                FileCache.get(file).getRunnable().pause();
                break;
        }
	}

}
