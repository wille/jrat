package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.redpois0n.ErrorDialog;
import com.redpois0n.Main;
import com.redpois0n.Slave;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;
import com.redpois0n.utils.IOUtils;


@SuppressWarnings("serial")
public class PanelControlSpeech extends PanelControlParent {
	private JTextPane txt;

	public PanelControlSpeech(Slave sl) {
		super(sl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					try {
						File f = File.createTempFile("jratspeechtest", ".vbs");
						FileWriter writer = new FileWriter(f, false);
						BufferedWriter writer1 = new BufferedWriter(writer);
						writer1.write(IOUtils.readString(Main.class.getResourceAsStream("/files/speech.vbs")).replace("%TEXT%", txt.getText()));
						writer1.close();
						
						Process p = Runtime.getRuntime().exec("cscript \"" + f.getAbsolutePath() + "\"");
						p.waitFor();
						f.delete();
					} catch (Exception e) {
						e.printStackTrace();
						ErrorDialog.create(e);
					}
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
					try {
						Runtime.getRuntime().exec("say " + txt.getText().trim());
					} catch (Exception e) {
						e.printStackTrace();
						ErrorDialog.create(e);
					}
				} else {
					Exception ex = new Exception("Cannot do text to speech on current OS");
					ErrorDialog.create(ex);
				}
			}
		});
		btnTest.setIcon(new ImageIcon(PanelControlSpeech.class.getResource("/icons/balloon_sound.png")));
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slave.addToSendQueue(new PacketBuilder(Header.SPEECH, txt.getText().trim()));
			}
		});
		btnSend.setIcon(new ImageIcon(PanelControlSpeech.class.getResource("/icons/right.png")));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt.setText("");
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlSpeech.class.getResource("/icons/clear.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnTest)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSend)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear)
					.addContainerGap(332, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnTest)
							.addComponent(btnSend))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		txt = new JTextPane();
		txt.setText("ur computer is spekn 2 u");
		scrollPane.setViewportView(txt);
		setLayout(groupLayout);
	}
}
