package com.redpois0n.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.Locale;
import com.redpois0n.Slave;
import com.redpois0n.packets.outgoing.Header;
import com.redpois0n.ui.renderers.table.LocaleTableRenderer;
import com.redpois0n.utils.FlagUtils;

@SuppressWarnings("serial")
public class PanelControlLocales extends PanelControlParent {
	private JTable table;
	private JLabel lblCountry;

	public PanelControlLocales(Slave sl) {
		super(sl);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Available locales"));
		
		JLabel lblCountrys = new JLabel("Country:");
		
		lblCountry = new JLabel(slave.getCountry());
		lblCountry.setIcon(FlagUtils.getFlag(slave));
		
		JLabel lblDisplayCountry = new JLabel("Display Country: " + slave.getLongCountry());
		
		JLabel lblLanguage = new JLabel("Language: " + slave.getLanguage());
		
		JLabel lblDisplayLanguage = new JLabel("Display language: " + slave.getDisplayLanguage());
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 581, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblCountrys)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblCountry))
								.addComponent(lblDisplayCountry))
							.addGap(33)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDisplayLanguage)
								.addComponent(lblLanguage))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCountrys)
						.addComponent(lblCountry)
						.addComponent(lblLanguage))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDisplayCountry)
						.addComponent(lblDisplayLanguage))
					.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 551, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new LocaleTableRenderer());
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Country", "Display Country", "Language", "Display Language"
			}
		));
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
		if (slave.getLocales() == null) {
			slave.addToSendQueue(Header.LOCALES);
		} else {
			load();
		}
	}
	
	public void load() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		for (Locale locale : slave.getLocales()) {
			model.addRow(new Object[] { locale.getCountry(), locale.getDisplaycountry(), locale.getLanguage(), locale.getDisplaylanguage() });
		}
	}
}
