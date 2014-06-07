package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.JOptionPane;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FrameControlPanel;
import su.jrat.client.ui.panels.PanelControlHostsFile;


public class Packet39HostEditResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
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
