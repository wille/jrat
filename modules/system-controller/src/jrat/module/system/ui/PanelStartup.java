package jrat.module.system.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.module.system.packets.Packet78RegistryStartup;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class PanelStartup extends ClientPanel {

    private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public PanelStartup(Slave s) {
		super(s, "Registry Startup", Resources.getIcon("application-run"));

		setLayout(new BorderLayout());

        JTable table = new DefaultJTable();

		table.setRowHeight(25);

		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Application" }));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

		slave.addToSendQueue(new Packet78RegistryStartup());
	}
}
