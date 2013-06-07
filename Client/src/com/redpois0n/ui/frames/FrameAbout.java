package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
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
import com.redpois0n.common.Version;
import com.redpois0n.net.WebRequest;
import com.redpois0n.ui.panels.PanelImage;
import com.redpois0n.utils.IOUtils;
import com.redpois0n.utils.NetworkUtils;


@SuppressWarnings("serial")
public class FrameAbout extends BaseFrame {

	private JPanel contentPane;

	public FrameAbout() {
		super();
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAbout.class.getResource("/icons/info.png")));
		setTitle("About jRAT BETA " + Version.getVersion());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 428, 238);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		PanelImage panel = new PanelImage();
		try {
			panel.image = ImageIO.read(FrameAbout.class.getResource("/files/logo.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/delete.png")));
		
		JLabel lblJrat = new JLabel("jRAT " + Version.getVersion());
		lblJrat.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JLabel lblPointOfOrigin = new JLabel("Point of origin:");
		
		JLabel lblSweden = new JLabel("Sweden");
		lblSweden.setIcon(new ImageIcon(FrameAbout.class.getResource("/flags/se.png")));
		
		JLabel lblWebsite = new JLabel("Website:");
		
		JLabel lblHttpredpoisncom = new JLabel(Constants.HOST + "");
		lblHttpredpoisncom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetworkUtils.openUrl(Constants.HOST + "");
			}
		});
		lblHttpredpoisncom.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/url.png")));
		lblHttpredpoisncom.setForeground(Color.BLUE);
		
		JLabel lblWrittenBy = new JLabel("Written by:");
		
		JLabel lblRedpoisn = new JLabel("redpois0n (.com)");
		lblRedpoisn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetworkUtils.openUrl("http://redpois0n.com");
			}
		});
		lblRedpoisn.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/action_disconnect.png")));
		lblRedpoisn.setForeground(Color.RED);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnClose)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblJrat)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblWebsite)
										.addComponent(lblPointOfOrigin)
										.addComponent(lblWrittenBy))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRedpoisn)
										.addComponent(lblHttpredpoisncom)
										.addComponent(lblSweden))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClose))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblJrat)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPointOfOrigin)
								.addComponent(lblSweden))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWebsite)
								.addComponent(lblHttpredpoisncom))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWrittenBy)
								.addComponent(lblRedpoisn))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		JTextPane txtAbout = new JTextPane();
		txtAbout.setEditable(false);
		try {
			txtAbout.setText(IOUtils.readString(WebRequest.getInputStream(Constants.HOST + "/misc/about.txt")));
			txtAbout.setSelectionStart(0);
			txtAbout.setSelectionEnd(0);
		} catch (Exception e) {
			txtAbout.setText("Failed to load about: " + e.getMessage());
			e.printStackTrace();
		}
		scrollPane.setViewportView(txtAbout);
		contentPane.setLayout(gl_contentPane);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
	}
}
