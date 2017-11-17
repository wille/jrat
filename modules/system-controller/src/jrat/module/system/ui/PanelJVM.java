package jrat.module.system.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.system.packets.Packet61SystemJavaProperties;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelJVM extends ClientPanel {

    private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public PanelJVM(Slave s) {
		super(s, "JVM", Resources.getIcon("java"));

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Raw data"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE).addContainerGap()));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(Resources.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(new Packet61SystemJavaProperties());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		btnClear.setIcon(Resources.getIcon("clear"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE).addGroup(gl_panel.createSequentialGroup().addComponent(btnClear).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnReload))).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addContainerGap()));

        JTable table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(181);
		table.getColumnModel().getColumn(1).setPreferredWidth(352);
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
}
