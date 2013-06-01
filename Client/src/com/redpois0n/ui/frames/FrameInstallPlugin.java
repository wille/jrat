package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.redpois0n.listeners.PluginDownloadListener;
import com.redpois0n.net.Download;


@SuppressWarnings("serial")
public class FrameInstallPlugin extends BaseFrame {

	private JPanel contentPane;
	private JTextField txtURL;
	private JTextField txtPath;
	private JProgressBar bar;
	private FrameInstallPlugin frame;
	private JLabel lblStatus;
	private JTextField txtName;

	public void setStatus(String msg, Color color) {
		lblStatus.setText(msg);
		lblStatus.setForeground(color);
	}

	public JProgressBar getBar() {
		return bar;
	}

	public String getPath() {
		return txtPath.getText() + txtName.getText() + ".jar";
	}

	public String getURL() {
		return txtURL.getText();
	}

	public String getFileName() {
		return txtName.getText().trim();
	}

	public FrameInstallPlugin() {
		super();
		frame = this;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameInstallPlugin.class.getResource("/icons/plugin_go.png")));
		setTitle("Install Plugin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 386, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblInstallPluginFrom = new JLabel("Install plugin from URL");

		txtURL = new JTextField();
		txtURL.setColumns(10);

		JLabel lblLocation = new JLabel("Location");

		txtPath = new JTextField(System.getProperty("user.dir") + File.separator + "plugins" + File.separator);
		txtPath.setEnabled(false);
		txtPath.setColumns(10);

		bar = new JProgressBar();

		JButton btnInstall = new JButton("Install");
		btnInstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Download dl = new Download(new PluginDownloadListener(frame), new File(txtPath.getText().trim(), txtName.getText().trim() + ".jar"), txtURL.getText().trim());
				dl.start();
				bar.setIndeterminate(true);
			}
		});

		lblStatus = new JLabel("Waiting...");
		
		txtName = new JTextField();
		txtName.setText("Plugin 1");
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtPath)
										.addComponent(txtURL, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblStatus)
									.addPreferredGap(ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
									.addComponent(btnInstall))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(9)
							.addComponent(bar, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(19, Short.MAX_VALUE)
							.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLocation))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblInstallPluginFrom))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInstallPluginFrom)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtURL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLocation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(bar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblStatus)
						.addComponent(btnInstall))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
