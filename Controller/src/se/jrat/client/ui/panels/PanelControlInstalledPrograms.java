package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet81InstalledPrograms;
import se.jrat.client.ui.renderers.JComboBoxIconRenderer;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelControlInstalledPrograms extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> comboBox;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelControlInstalledPrograms(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				String location;

				if (comboBox.getSelectedIndex() == 1) {
					location = "hklm";
				} else {
					location = "HKEY_CURRENT_USER";
				}

				slave.addToSendQueue(new Packet81InstalledPrograms(location));
			}
		});
		btnReload.setIcon(new ImageIcon(PanelControlInstalledPrograms.class.getResource("/icons/update.png")));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlInstalledPrograms.class.getResource("/icons/clear.png")));

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "HKEY_CURRENT_USER", "HKEY_LOCAL_MACHINE" }));
		comboBox.setRenderer(getRenderer());

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReload)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(276, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReload)
						.addComponent(btnClear)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);

		table = new JTable();
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Installed Programs" }));
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(588);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public JComboBoxIconRenderer getRenderer() {
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();

		ImageIcon icon = IconUtils.getIcon("folder_network");
		renderer.addIcon("hkey_current_user", icon);
		renderer.addIcon("hkey_local_machine", icon);

		return renderer;
	}
}
