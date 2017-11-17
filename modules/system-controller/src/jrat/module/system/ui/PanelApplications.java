package jrat.module.system.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.system.packets.Packet81InstalledPrograms;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.JComboBoxIconRenderer;
import oslib.OperatingSystem;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelApplications extends ClientPanel {

    private TableModel model;
	private JComboBox<String> comboBox;

	public TableModel getModel() {
		return model;
	}

	public PanelApplications(Slave s) {
		super(s, "Applications", Resources.getIcon("application-detail"));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		comboBox = new JComboBox<>();
		comboBox.setVisible(slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS);
		comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"HKEY_CURRENT_USER", "HKEY_LOCAL_MACHINE"}));
		comboBox.setRenderer(getRenderer());

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

        JTable table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Installed Programs" }));
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(588);
		scrollPane.setViewportView(table);
	}

	public void opened() {
        clear();
        String location;

        if (comboBox.getSelectedIndex() == 1) {
            location = "hklm";
        } else {
            location = "HKEY_CURRENT_USER";
        }

        slave.addToSendQueue(new Packet81InstalledPrograms(location));
    }

	private void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public JComboBoxIconRenderer getRenderer() {
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();

		ImageIcon icon = Resources.getIcon("folder-network");
		renderer.addIcon("HKEY_CURRENT_USER", icon);
		renderer.addIcon("HKEY_LOCAL_MACHINE", icon);

		return renderer;
	}
}
