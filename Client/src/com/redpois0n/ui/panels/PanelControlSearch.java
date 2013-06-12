package com.redpois0n.ui.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.Drive;
import com.redpois0n.Slave;
import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.out.Header;
import com.redpois0n.ui.frames.FrameRemoteFiles;
import com.redpois0n.ui.renderers.JComboBoxIconRenderer;
import com.redpois0n.utils.IconUtils;

@SuppressWarnings("serial")
public class PanelControlSearch extends PanelControlParent {

	private JTable table;

	private DefaultTableModel model;
	private JTextField txt;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox<String> cbDrives;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelControlSearch(Slave slave) {
		super(slave);
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnSearch = new JButton("Search");

		btnSearch.setIcon(new ImageIcon(PanelControlSearch.class.getResource("/icons/folder_search.png")));

		txt = new JTextField();
		txt.setColumns(10);

		JToggleButton tglbtnNameContains = new JToggleButton("Name contains");
		tglbtnNameContains.setIcon(new ImageIcon(PanelControlSearch.class.getResource("/icons/file.png")));
		buttonGroup.add(tglbtnNameContains);

		final JToggleButton tglbtnPathContains = new JToggleButton("Path contains");
		tglbtnPathContains.setIcon(new ImageIcon(PanelControlSearch.class.getResource("/icons/folder.png")));
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
		btnClear.setIcon(new ImageIcon(PanelControlSearch.class.getResource("/icons/clear.png")));
		
		cbDrives = new JComboBox<String>();
		cbDrives.setRenderer(getDrivesRenderer());
		
		for (Drive drive : slave.getDrives()) {
			cbDrives.addItem(drive.getName());
		}

		table = new JTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { " ", "Path", "Name" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(392);
		table.getColumnModel().getColumn(2).setPreferredWidth(178);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mntmOpenInFile = new JMenuItem("Open in file manager");
		mntmOpenInFile.setIcon(new ImageIcon(PanelControlSearch.class.getResource("/icons/folder_go.png")));
		mntmOpenInFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row != -1) {
					FrameRemoteFiles frame = FrameRemoteFiles.instances.get(sl);
					if (frame == null) {
						frame = new FrameRemoteFiles(sl);
						frame.setVisible(true);
					}
					String val = model.getValueAt(row, 1).toString();
					String path = val.substring(0, val.lastIndexOf(sl.getFileSeparator()));								
					sl.addToSendQueue(new PacketBuilder(Header.LIST_FILES, path));
					frame.txtDir.setText(path);
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
					FrameRemoteFiles frame = FrameRemoteFiles.instances.get(sl);
					if (frame == null) {
						frame = new FrameRemoteFiles(sl);
						frame.setVisible(true);
					}
					String val = model.getValueAt(row, 1).toString();
					String path = val.substring(0, val.lastIndexOf(sl.getFileSeparator()));								
					sl.addToSendQueue(new PacketBuilder(Header.LIST_FILES, path));
					frame.txtDir.setText(path);
				}
			}
		});
		btnOpenInFile.setIcon(new ImageIcon(PanelControlSearch.class.getResource("/icons/folder_go.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 598, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(184)
					.addComponent(btnOpenInFile)
					.addGap(6)
					.addComponent(btnClear)
					.addGap(6)
					.addComponent(tglbtnPathContains)
					.addGap(6)
					.addComponent(tglbtnNameContains))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(135)
					.addComponent(lblDrive)
					.addGap(4)
					.addComponent(cbDrives, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblSearchFor)
					.addGap(4)
					.addComponent(txt, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnSearch))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOpenInFile)
						.addComponent(btnClear)
						.addComponent(tglbtnPathContains)
						.addComponent(tglbtnNameContains))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblDrive))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(cbDrives, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblSearchFor))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSearch)))
		);
		setLayout(groupLayout);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton src = (JButton) arg0.getSource();
				if (src.getText().equals("Search")) {
					src.setText("Stop...");
					sl.addToSendQueue(new PacketBuilder(Header.START_SEARCH, new String[] { cbDrives.getSelectedItem().toString(), txt.getText(), tglbtnPathContains.isSelected() + "" }));
				} else if (src.getText().equals("Stop...")) {
					src.setText("Search");
					sl.addToSendQueue(Header.END_SEARCHING);
				}

			}
		});
	}
	
	public DefaultListCellRenderer getDrivesRenderer() {
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();
		
		for (Drive drive : slave.getDrives()) {
			renderer.addIcon(drive.getName().toLowerCase(), IconUtils.getFileIcon(drive.getName()));
		}
		
		return renderer;
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
