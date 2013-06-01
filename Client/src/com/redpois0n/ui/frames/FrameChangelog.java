package com.redpois0n.ui.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.redpois0n.Constants;
import com.redpois0n.ErrorDialog;
import com.redpois0n.common.Version;
import com.redpois0n.net.WebRequest;


@SuppressWarnings("serial")
public class FrameChangelog extends BaseFrame {

	private JPanel contentPane;

	public FrameChangelog(String url, String version) {
		super();
		setAlwaysOnTop(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameChangelog.class.getResource("/icons/question.png")));
		setTitle("Whats new? " + Version.getVersion());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 527, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnDownloadLatestUpdate = new JButton("Download latest update");
		btnDownloadLatestUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI(Constants.DOWNLOAD_URL));
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnDownloadLatestUpdate.setIcon(new ImageIcon(FrameChangelog.class.getResource("/icons/drive-download.png")));

		JLabel lblYourVersion = new JLabel("Your version: " + Version.getVersion());

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setIcon(new ImageIcon(FrameChangelog.class.getResource("/icons/delete.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnDownloadLatestUpdate)
					.addPreferredGap(ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
					.addComponent(lblYourVersion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnClose))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDownloadLatestUpdate)
						.addComponent(btnClose)
						.addComponent(lblYourVersion))
					.addGap(30))
		);

		JTextPane txt = new JTextPane();
		txt.setEditable(false);
		scrollPane.setViewportView(txt);
		contentPane.setLayout(gl_contentPane);

		try {
			URL u = WebRequest.getUrl(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));

			String s = null;
			while ((s = reader.readLine()) != null) {
				txt.getDocument().insertString(txt.getDocument().getLength(), s + "\n", null);
			}
			
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
	}
}
