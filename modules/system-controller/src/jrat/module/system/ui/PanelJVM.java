package jrat.module.system.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.module.system.packets.Packet61SystemJavaProperties;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class PanelJVM extends ClientPanel {

    private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public PanelJVM(Slave s) {
		super(s, "JVM", Resources.getIcon("java"));

		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JTable table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(181);
		table.getColumnModel().getColumn(1).setPreferredWidth(352);
		scrollPane.setViewportView(table);

		add(scrollPane, BorderLayout.CENTER);

        slave.addToSendQueue(new Packet61SystemJavaProperties());
    }
}
