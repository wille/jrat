package io.jrat.client.ui.frames;

import io.jrat.client.Slave;
import io.jrat.client.Status;
import io.jrat.client.exceptions.CloseException;
import io.jrat.client.packets.outgoing.Packet40Thumbnail;
import io.jrat.client.ui.panels.PanelImage;
import io.jrat.client.utils.FlagUtils;
import io.jrat.client.utils.IconUtils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class FrameInfo extends BaseFrame {

	private JPanel contentPane;
	public Slave slave;
	public static HashMap<Slave, FrameInfo> instances = new HashMap<Slave, FrameInfo>();
	private JTextField txtServerID;
	private JTextField txtIP;
	private JTextField txtUsername;
	private JTextField txtOSname;
	public PanelImage panel;

	public FrameInfo(Slave slave) {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameInfo.class.getResource("/icons/info.png")));
		this.slave = slave;
		final Slave sl = slave;
		instances.put(slave, this);
		setTitle("Info - " + slave.getIP() + " - " + slave.getComputerName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblCountry = new JLabel("Country:");

		JLabel lblFlag = new JLabel("");
		lblFlag.setIcon(new ImageIcon(FrameInfo.class.getResource("/icons/unknown.png")));

		JLabel lblLocation = new JLabel("...");

		JLabel lblServerId = new JLabel("Connection ID:");

		txtServerID = new JTextField();
		txtServerID.setEditable(false);
		txtServerID.setColumns(10);

		JLabel lblStatus = new JLabel("Status:");

		JLabel lblIp = new JLabel("IP:");

		txtIP = new JTextField();
		txtIP.setEditable(false);
		txtIP.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");

		JLabel lblOsName = new JLabel("OS Name:");

		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setColumns(10);

		txtOSname = new JTextField();
		txtOSname.setEditable(false);
		txtOSname.setColumns(10);

		JLabel lblStat = new JLabel("Unknown");

		JLabel lblPingT = new JLabel("Ping:");

		JLabel lblPing = new JLabel("0");

		JLabel lblSpeedT = new JLabel("Speed:");

		JLabel lblSpeed = new JLabel(".");
		lblSpeed.setIcon(new ImageIcon(FrameInfo.class.getResource("/icons/ping0.png")));

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setIcon(null);

		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sl.closeSocket(new CloseException("Disconnecting"));
				} catch (Exception ex) {

				}
				setVisible(false);
				dispose();
			}
		});
		btnDisconnect.setIcon(new ImageIcon(FrameInfo.class.getResource("/icons/delete.png")));

		JButton btnReload = new JButton("");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
				FrameInfo frame = new FrameInfo(sl);
				frame.setVisible(true);
			}
		});
		btnReload.setIcon(new ImageIcon(FrameInfo.class.getResource("/icons/refresh.png")));

		panel = new PanelImage();

		JLabel lblThumbnail = new JLabel("Thumbnail:");

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet40Thumbnail());
			}
		});
		button.setIcon(new ImageIcon(FrameInfo.class.getResource("/icons/update.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDisconnect).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblCountry).addComponent(lblStatus).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(2).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblIp).addComponent(lblServerId))).addComponent(lblUsername).addComponent(lblPingT, Alignment.TRAILING).addComponent(lblSpeedT, Alignment.TRAILING).addComponent(lblOsName, Alignment.TRAILING))).addComponent(lblThumbnail)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(lblFlag).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblLocation)).addComponent(lblSpeed).addComponent(lblPing).addComponent(lblStat).addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE).addComponent(txtServerID, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE).addComponent(txtIP, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE).addComponent(txtOSname, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE).addGroup(gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(button, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblCountry).addComponent(lblFlag).addComponent(lblLocation)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblServerId).addComponent(txtServerID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblIp).addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblUsername).addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblOsName).addComponent(txtOSname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblStat).addComponent(lblStatus)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPingT).addComponent(lblPing)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblSpeedT).addComponent(lblSpeed)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(panel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addComponent(button)).addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addComponent(btnDisconnect)).addComponent(btnReload))).addComponent(lblThumbnail)).addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		lblLocation.setText(slave.getCountry());
		lblFlag.setIcon(FlagUtils.getFlag(slave));
		txtServerID.setText(slave.getServerID());
		txtIP.setText(slave.getIP());
		txtOSname.setText(slave.getOperatingSystem());
		txtUsername.setText(slave.getComputerName());
		lblStat.setText(Status.getStatusFromID(slave.getStatus()));
		lblPing.setText(slave.getPing() + "");
		lblSpeed.setIcon(IconUtils.getPingIcon(slave));
		lblSpeed.setText("");

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
		if (sl.getThumbnail() == null) {
			sl.addToSendQueue(new Packet40Thumbnail());
		} else {
			panel.image = slave.getThumbnail().getImage();
			// sl.thumbnail = null;
		}
	}

	public void exit() {
		instances.remove(slave);
		setVisible(false);
		dispose();
	}
}
