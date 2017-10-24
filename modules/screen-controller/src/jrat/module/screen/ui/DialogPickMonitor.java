package jrat.module.screen.ui;

import graphslib.monitors.MonitorListener;
import graphslib.monitors.PanelMonitors;
import graphslib.monitors.PanelMonitors.PanelMonitor;
import graphslib.monitors.RemoteMonitor;
import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.listeners.PickMonitorListener;
import jrat.controller.packets.outgoing.Packet75AllThumbnails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class DialogPickMonitor extends JDialog {

	public static final Map<Slave, DialogPickMonitor> instances = new HashMap<Slave, DialogPickMonitor>();

	private PanelMonitors panelMonitors;
	private JLabel label;
	private JButton btnThumbnails;

	public DialogPickMonitor(final Slave sl, final PickMonitorListener l) {
		setIconImage(Resources.getIcon("monitor-arrow").getImage());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				instances.remove(sl);
			}
		});
		instances.put(sl, this);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Pick Monitor");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelMonitors = new PanelMonitors(sl.getMonitors(), false);

		panelMonitors.addListener(new MonitorListener() {
			@Override
			public void onMonitorChange(RemoteMonitor monitor) {
				PanelMonitor panel = null;

				for (PanelMonitor panel1 : panelMonitors.getPanels()) {
					if (panel1.getMonitor().equals(monitor)) {
						panel = panel1;
						break;
					}
				}

				if (panel != null) {
					label.setText("X, Y: " + panel.getMonitor().getX() + ", " + panel.getMonitor().getY() + " - " + panel.getMonitor().getWidth()
							+ "x" + panel.getMonitor().getHeight());
					l.monitorPick(panel.getMonitor());
				}
			}
		});

		getContentPane().add(panelMonitors);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		label = new JLabel("");
		buttonPane.add(label);

		btnThumbnails = new JButton("Thumbnails");
		btnThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet75AllThumbnails());
				btnThumbnails.setText("Reload");
			}
		});
		buttonPane.add(btnThumbnails);

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}

	public PanelMonitors getPanelMonitors() {
		return panelMonitors;
	}

}
