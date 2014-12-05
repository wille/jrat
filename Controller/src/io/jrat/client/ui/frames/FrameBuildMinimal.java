package io.jrat.client.ui.frames;

import io.jrat.client.BuildStatus;
import io.jrat.client.Constants;
import io.jrat.client.ErrorDialog;
import io.jrat.client.Globals;
import io.jrat.client.Main;
import io.jrat.client.OSConfig;
import io.jrat.client.addons.PluginList;
import io.jrat.client.build.Build;
import io.jrat.client.listeners.MinimalBuildListener;
import io.jrat.client.listeners.SocketComboBoxListener;
import io.jrat.client.net.PortListener;
import io.jrat.client.settings.Settings;
import io.jrat.client.ui.components.PortListenerJComboBox;
import io.jrat.client.utils.NetUtils;
import io.jrat.common.OperatingSystem;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.util.LinkedHashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class FrameBuildMinimal extends BaseFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtHost;
	private JSpinner spPort;
	private JTextField txtId;
	private JTextField txtFile;
	private JTextField txtPass;
	private JComboBox<String> cbLocation;
	private JCheckBox chckbxEmbed;
	private JProgressBar progressBar;
	private JButton btnBuild;
	private LinkedHashMap<String, BuildStatus> statuses = new LinkedHashMap<String, BuildStatus>();

	public FrameBuildMinimal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameBuildMinimal.class.getResource("/icons/build.png")));
		setTitle("Normal Builder");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblStatus = new JLabel("...");

		progressBar = new JProgressBar();

		btnBuild = new JButton("Build");
		btnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				build();
			}
		});
		btnBuild.setIcon(new ImageIcon(FrameBuildMinimal.class.getResource("/icons/final.png")));

		JLabel lblHost = new JLabel("Host:");

		JLabel lblPort = new JLabel("Port:");

		txtHost = new JTextField();
		txtHost.setColumns(10);

		spPort = new JSpinner();
		spPort.setModel(new SpinnerNumberModel(Settings.getGlobal().getInt("bport"), 1, 65535, 1));

		JLabel lblId = new JLabel("ID:");

		txtId = new JTextField();
		txtId.setText(Settings.getGlobal().getString("bid"));
		txtId.setColumns(10);

		chckbxEmbed = new JCheckBox("Embed using installer");

		cbLocation = new JComboBox<String>();
		cbLocation.setModel(new DefaultComboBoxModel<String>(new String[] { "appdata", "root/C drive", "temp/documents (UNIX)", "desktop" }));

		JLabel lblKeyfile = new JLabel("Key/File:");

		txtFile = new JTextField();
		txtFile.setColumns(10);

		JLabel lblPath = new JLabel("Path:");

		JLabel lbljar = new JLabel(".jar");

		JLabel lblPass = new JLabel("Pass:");

		txtPass = new JTextField(Settings.getGlobal().getString("bpass"));
		txtPass.setColumns(10);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JComboBox<?> comboBox = new PortListenerJComboBox(new SocketComboBoxListener() {
			@Override
			public void onChange(PortListener pl) {
				txtPass.setText(pl.getPass());
				spPort.setValue(pl.getServer().getLocalPort());
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxEmbed)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblKeyfile)
								.addComponent(lblPath))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtFile)
								.addComponent(cbLocation, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lbljar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPort)
								.addComponent(lblHost)
								.addComponent(lblId))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(spPort, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(txtId, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)))
							.addGap(21))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPass)
							.addGap(10)
							.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addGap(16))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBuild, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblStatus)
							.addPreferredGap(ComponentPlacement.RELATED, 339, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblHost))
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(spPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPort)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblId))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPass))
					.addGap(43)
					.addComponent(chckbxEmbed)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPath))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKeyfile)
						.addComponent(txtFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljar))
					.addGap(18)
					.addComponent(lblStatus)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnBuild, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED, 4, GroupLayout.PREFERRED_SIZE)))
					.addGap(44))
		);

		JButton btnLocal = new JButton("");
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtHost.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (Exception e1) {
					e1.printStackTrace();
					ErrorDialog.create(e1);
				}
			}
		});
		btnLocal.setIcon(new ImageIcon(FrameBuildMinimal.class.getResource("/icons/network-ip-local.png")));
		toolBar.add(btnLocal);

		JButton btnWan = new JButton("");
		btnWan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					txtHost.setText(NetUtils.getIP());
				} catch (Exception e1) {
					e1.printStackTrace();
					ErrorDialog.create(e1);
				}
			}
		});
		btnWan.setIcon(new ImageIcon(FrameBuildMinimal.class.getResource("/icons/network-ip.png")));
		toolBar.add(btnWan);
		contentPane.setLayout(gl_contentPane);

		// update();
	}

	/*public void update() {

		if (text.length() == Crypto.KEY_LENGTH) {
			txtKey.setBackground(Color.green);
			lblLength.setForeground(Color.green);
		} else {
			txtKey.setBackground(Color.red);
			lblLength.setForeground(Color.red);
		}
	}*/

	public void build() {
		/*if (txtKey.getText().trim().length() != Crypto.KEY_LENGTH) {
			return;
		}*/

		JFileChooser c = new JFileChooser();
		c.showSaveDialog(null);

		final File file = c.getSelectedFile();

		if (file == null) {
			return;
		}

		new Thread() {
			public void run() {
				MinimalBuildListener l = new MinimalBuildListener(FrameBuildMinimal.this);

				String[] address = new String[] { txtHost.getText().trim() + ":" + spPort.getValue().toString() };
				String id = txtId.getText().trim();
				String pass = txtPass.getText().trim();
				boolean crypt = chckbxEmbed.isSelected();
				String droppath = cbLocation.getSelectedItem().toString();
				int reconSec = 10;
				String name = txtFile.getText().trim();
				boolean fakewindow = false;
				String faketitle = "";
				String fakemessage = "";
				int fakeicon = -1;
				boolean melt = false;
				boolean runNextBoot = false;
				boolean bind = false;
				boolean hiddenFile = false;
				String bindpath = null;
				String bindname = null;
				String droptarget = null;
				boolean usemutex = false;
				int mutexport = -1;
				PluginList pluginlist = null;
				boolean timeout = false;
				int timeoutms = -1;
				boolean delay = false;
				int delayms = -1;
				boolean usehost = false;
				String hosttext = null;
				boolean overwritehost = false;
				boolean trayicon = Main.liteVersion;
				String icon = null;
				String traymsg = Main.liteVersion ? "Connected to control center" : "";
				String traymsgfail = Main.liteVersion ? "Disconnected from control center" : "";
				String traytitle = Main.liteVersion ? Constants.NAME + "" : "";
				boolean handleerr = true;
				boolean persistance = false;
				int persistancems = -1;
				boolean debugmsg = true;
				OSConfig osconfig = new OSConfig();
				osconfig.addOS(OperatingSystem.WINDOWS);
				osconfig.addOS(OperatingSystem.OSX);
				osconfig.addOS(OperatingSystem.LINUX);
				boolean antivm = false;

				Build.build(l, Globals.getStub(), file, address, id, pass, crypt, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, runNextBoot, hiddenFile, bind, bindpath, bindname, droptarget, usemutex, mutexport, pluginlist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, debugmsg, osconfig, true, null, antivm);

				Settings.getGlobal().setVal("baddresses", address);
				Settings.getGlobal().setVal("bid", id);
				Settings.getGlobal().setVal("bpass", pass);
				Settings.getGlobal().setVal("bcrypt", crypt);
				Settings.getGlobal().setVal("brecat", reconSec);
			}
		}.start();
	}

	public void log() {

	}

	public JLabel getStatusLabel() {
		return lblStatus;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JButton getBuildButton() {
		return btnBuild;
	}

	public LinkedHashMap<String, BuildStatus> getStatuses() {
		return statuses;
	}
}