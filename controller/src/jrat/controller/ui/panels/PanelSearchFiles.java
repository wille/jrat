package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet53StartSearch;
import jrat.controller.packets.outgoing.Packet54StopSearch;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.frames.FrameRemoteFiles;
import jrat.controller.ui.renderers.table.FileSearchTableRenderer;
import jrat.controller.utils.Utils;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelSearchFiles extends PanelControlParent {

	private JTable table;

	private TableModel model;
	private JTextField txt;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtSearchRoot;
	private FileSearchTableRenderer renderer;
	private FrameRemoteFiles parent;

	public TableModel getModel() {
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

		txtSearchRoot = new JTextField(slave.getDrives()[0].getName());

		table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Path", "Name" }));
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
					parent.setTab(parent.getFilesPanel());
				}
			}
		});
		popupMenu.add(mntmOpenInFile);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);

		JLabel lblSearchRoot = new JLabel("Search from");

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
					.addComponent(lblSearchRoot)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSearchRoot, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(txtSearchRoot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSearchRoot))
					.addContainerGap())
		);
		setLayout(groupLayout);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton src = (JButton) arg0.getSource();
				if (src.getText().equals("Search")) {
					src.setText("Stop");
					sl.addToSendQueue(new Packet53StartSearch(txtSearchRoot.getText(), txt.getText(), tglbtnPathContains.isSelected()));
				} else if (src.getText().equals("Stop")) {
					src.setText("Search");
					stop();
				}

			}
		});
	}

	/**
	 * Start file search from this path
	 */
	public void setSearchRoot(String searchRoot) {
		txtSearchRoot.setText(searchRoot);
	}

	public void stop() {
		slave.addToSendQueue(new Packet54StopSearch());
	}
}
