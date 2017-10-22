package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.controller.ErrorDialog;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet55HostsFile;
import jrat.controller.packets.outgoing.Packet56UpdateHostsFile;
import jrat.controller.utils.Utils;
import oslib.OperatingSystem;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


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
		btnClear.setIcon(IconUtils.getIcon("clear"));

		JButton btnGetHostsFile = new JButton("Get hosts file");
		btnGetHostsFile.setIcon(IconUtils.getIcon("computer"));
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
		btnUpdateHostsFile.setIcon(IconUtils.getIcon("computer"));

		JButton btnGetLocalHosts = new JButton("Get local hosts file");
		btnGetLocalHosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File file = null;
					
					if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
						file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
					} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
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
		btnGetLocalHosts.setIcon(IconUtils.getIcon("arrow-left"));

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
