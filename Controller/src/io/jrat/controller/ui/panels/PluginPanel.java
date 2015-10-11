package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Constants;
import io.jrat.controller.addons.OnlinePlugin;
import io.jrat.controller.addons.PluginInstaller;
import io.jrat.controller.listeners.ExtensionInstallerListener;
import io.jrat.controller.ui.frames.FrameInstallPlugins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class PluginPanel extends JPanel {

	private FrameInstallPlugins parent;
	private OnlinePlugin op;
	private JLabel lblPluginName;
	private JLabel lblAuthorText;
	private JLabel lblVersion;
	private JLabel lblDescription;
	private JLabel lblAuthor;
	private JButton btnAction;
	private JLabel lblVerified;
	private JLabel lblUpToDate;
	private JLabel lblIcon;
	
	public PluginPanel(FrameInstallPlugins parent, OnlinePlugin onlinePlugin) {
		this.parent = parent;
		this.op = onlinePlugin;
		
		setPreferredSize(new Dimension(600, 100));
		
		lblPluginName = new JLabel("Plugin Name");
		lblPluginName.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblVersion = new JLabel("Version");
		lblVersion.setForeground(Color.LIGHT_GRAY);
		
		lblAuthorText = new JLabel("Author:");
		
		JSeparator separator = new JSeparator();
		
		lblDescription = new JLabel("Description");
		
		lblAuthor = new JLabel("Author");
		
		btnAction = new JButton("Action");
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				install();
			}
		});
		
		lblVerified = new JLabel("");
		lblVerified.setToolTipText("Files not hosted by jRAT");
		lblVerified.setIcon(IconUtils.getIcon("log-warning"));
		
		lblUpToDate = new JLabel("Up to date");
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(IconUtils.getIcon("plugin"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIcon)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblUpToDate)
						.addComponent(lblDescription)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblAuthorText)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblAuthor))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPluginName)
									.addGap(18)
									.addComponent(lblVersion)))
							.addPreferredGap(ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
							.addComponent(lblVerified)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAction, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPluginName)
								.addComponent(lblVersion)
								.addComponent(lblVerified)
								.addComponent(lblIcon))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAuthorText)
								.addComponent(lblAuthor)))
						.addComponent(btnAction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescription)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUpToDate)
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
		
		update();
	}
	
	public void update() {
		setDescription(op.getDescription());
		setAuthor(op.getAuthor());
		setVersion(op.getVersion());
		setPluginName(op.getDisplayName());
		setVerified(op.isUrlVerified());
		setInstalled(op.isInstalled());
		setUpToDate(op.isUpToDate());
		setIcon(op.getIcon());
	}
	
	public void setIndex(int i) {
	}
	
	public void setDescription(String text) {
		lblDescription.setText(text);
	}
	
	public void setAuthor(String text) {
		lblAuthor.setText(text);
	}
	
	public void setVersion(String text) {
		lblVersion.setText(text);
	}
	
	public void setPluginName(String text) {
		lblPluginName.setText(text);
	}
	
	public void setUpToDate(boolean upToDate) {
		if (upToDate) {
			lblUpToDate.setForeground(Color.green.darker());
		} else {
			lblUpToDate.setForeground(Color.red);
			lblUpToDate.setText("Not up to date");
			btnAction.setFont(new Font("Arial", Font.BOLD, 12));
			btnAction.setText("Update");
		}		
	}
	
	public void setInstalled(boolean installed) {
		lblUpToDate.setVisible(installed);
		btnAction.setText(installed ? "Reinstall" : "Install");
	}
	
	public void setVerified(boolean verified) {
		if (verified) {
			lblVerified.setIcon(IconUtils.getIcon("enabled"));
			lblVerified.setToolTipText("Files hosted by jRAT");
		}
	}
	
	public void setIcon(Icon icon) {
		lblIcon.setIcon(icon);
	}
	
	public void install() {
		final boolean isInstalled = op.isInstalled();
		String what = op.isInstalled() ? "reinstall" : "install";
		if (JOptionPane.showConfirmDialog(null, "Are you sure that you want to " + what + " " + op.getDisplayName() + "?\n\n" + Constants.HOST + " is not behind many of these plugins and cannot verify their content", "Plugin", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
			return;
		}

		final PluginInstaller installer = new PluginInstaller(op, new ExtensionInstallerListener() {
			@Override
			public void status(Color color, String message, int current, int total) {
				parent.lblStatus.setForeground(color);
				parent.lblStatus.setText(message);
				parent.progressBar.setValue(current);

				if (total == -1) {
					parent.progressBar.setIndeterminate(true);
				} else {
					parent.progressBar.setIndeterminate(false);
					parent.progressBar.setMaximum(total);
				}
			}
		});

		parent.progressBar.setVisible(true);
		parent.lblStatus.setVisible(true);

		new Thread(new Runnable() {
			public void run() {
				try {
					installer.toggle();

					parent.progressBar.setVisible(false);
					parent.lblStatus.setVisible(false);

					if (isInstalled) {
						JOptionPane.showMessageDialog(null, "Successfully uninstalled and removed " + op.getDisplayName(), "Plugin", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Successfully installed and enabled " + op.getDisplayName(), "Plugin", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();

					parent.progressBar.setVisible(false);
					parent.lblStatus.setVisible(false);

					JOptionPane.showMessageDialog(null, "Could not find package, maybe this is a paid plugin", "Plugin", JOptionPane.WARNING_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();

					parent.progressBar.setVisible(false);
					parent.lblStatus.setVisible(false);

					JOptionPane.showMessageDialog(null, "Failed to install " + op.getDisplayName() + ", " + ex.getClass().getSimpleName() + ": " + ex.getMessage(), "Plugin", JOptionPane.ERROR_MESSAGE);
				}
			}
		}).start();
	}
}