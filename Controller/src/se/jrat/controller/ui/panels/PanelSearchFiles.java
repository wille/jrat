package se.jrat.controller.ui.panels;

import iconlib.IconUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import se.jrat.controller.Drive;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet53StartSearch;
import se.jrat.controller.packets.outgoing.Packet54StopSearch;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.frames.FrameRemoteFiles;
import se.jrat.controller.ui.renderers.JComboBoxIconRenderer;
import se.jrat.controller.ui.renderers.table.FileSearchTableRenderer;
import se.jrat.controller.utils.Utils;

@SuppressWarnings("serial")
public class PanelSearchFiles extends PanelControlParent {

	private JTable table;

	private DefaultTableModel model;
	private JTextField txt;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox<String> cbDrives;
	private FileSearchTableRenderer renderer;
	private FrameRemoteFiles parent;

	public DefaultTableModel getModel() {
		return model;
	}
	
	public FileSearchTableRenderer getRenderer() {
		return renderer;
	}

	public PanelSearchFiles(Slave slave, FrameRemoteFiles parentFrame) {
		super(slave);
		this.parent = parentFrame;
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnSearch = new JButton("Search");

		btnSearch.setIcon(IconUtils.getIcon("folder-search"));

		txt = new JTextField();
		txt.setColumns(10);

		JToggleButton tglbtnNameContains = new JToggleButton("Name contains");
		tglbtnNameContains.setIcon(IconUtils.getIcon("file"));
		buttonGroup.add(tglbtnNameContains);

		final JToggleButton tglbtnPathContains = new JToggleButton("Path contains");
		tglbtnPathContains.setIcon(IconUtils.getIcon("folder"));
		buttonGroup.add(tglbtnPathContains);
		tglbtnPathContains.setSelected(true);

		JLabel lblSearchFor = new JLabel("Search for");

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));

		cbDrives = new JComboBox<String>();
		cbDrives.setRenderer(getDrivesRenderer());

		for (Drive drive : slave.getDrives()) {
			cbDrives.addItem(drive.getName());
		}

		table = new DefaultJTable();
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Path", "Name" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(392);
		table.getColumnModel().getColumn(1).setPreferredWidth(178);
		renderer = new FileSearchTableRenderer();
		table.setDefaultRenderer(Object.class, renderer);

		JPopupMenu popupMenu = new JPopupMenu();
		Utils.addPopup(table, popupMenu);

		JMenuItem mntmOpenInFile = new JMenuItem("Open in file manager");
		mntmOpenInFile.setIcon(IconUtils.getIcon("folder-go"));
		mntmOpenInFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row != -1) {
					String val = model.getValueAt(row, 0).toString();
					parent.getFilesPanel().getRemoteTable().setDirectory(val);
				}
			}
		});
		popupMenu.add(mntmOpenInFile);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);

		JLabel lblDrive = new JLabel("Drive");

		JButton btnOpenInFile = new JButton("Open");
		btnOpenInFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row != -1) {
					FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(sl);
					if (frame == null) {
						frame = new FrameRemoteFiles(sl);
						frame.setVisible(true);
					}
					String val = model.getValueAt(row, 0).toString();
					String path = val.substring(0, val.lastIndexOf(sl.getFileSeparator()));
					frame.getFilesPanel().getRemoteTable().setDirectory(path);

				}
			}
		});
		btnOpenInFile.setIcon(IconUtils.getIcon("folder-go"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
					.addGap(2))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(184)
					.addComponent(btnOpenInFile)
					.addGap(6)
					.addComponent(btnClear)
					.addGap(6)
					.addComponent(tglbtnPathContains)
					.addGap(6)
					.addComponent(tglbtnNameContains)
					.addGap(2))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(135)
					.addComponent(lblDrive)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbDrives, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblSearchFor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txt, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSearch)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOpenInFile)
						.addComponent(btnClear)
						.addComponent(tglbtnPathContains)
						.addComponent(tglbtnNameContains))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch)
						.addComponent(txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSearchFor)
						.addComponent(cbDrives, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDrive))
					.addContainerGap())
		);
		setLayout(groupLayout);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton src = (JButton) arg0.getSource();
				if (src.getText().equals("Search")) {
					src.setText("Stop...");
					sl.addToSendQueue(new Packet53StartSearch(cbDrives.getSelectedItem().toString(), txt.getText(), tglbtnPathContains.isSelected()));
				} else if (src.getText().equals("Stop...")) {
					src.setText("Search");
					sl.addToSendQueue(new Packet54StopSearch());
				}

			}
		});
	}

	public DefaultListCellRenderer getDrivesRenderer() {
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();

		for (Drive drive : slave.getDrives()) {
			renderer.addIcon(drive.getName(), IconUtils.getIcon("drive"));
		}

		return renderer;
	}
}
