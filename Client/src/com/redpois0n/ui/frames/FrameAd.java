package com.redpois0n.ui.frames;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.redpois0n.common.Version;
import com.redpois0n.util.NetworkUtils;


@SuppressWarnings("serial")
public class FrameAd extends BaseDialog {
	
	public static final String BTC_ADDRESS = "19jr3SU9wKyCxdgApo1KiHmoY59iWztrdv";
	public static final String LR_ADDRESS = "U3902875";

	public FrameAd() {
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setModal(true);
		
		JLabel lblJrat = new JLabel("jRAT " + Version.getVersion());
		lblJrat.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JLabel lblPleaseConsiderDonating = new JLabel("Please consider donating, any amount, ");
		
		JLabel lblLr = new JLabel("");
		lblLr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetworkUtils.openUrl("https://sci.libertyreserve.com/?lr_acc=" + LR_ADDRESS + "&lr_currency=LRUSD");
			}
		});
		lblLr.setIcon(new ImageIcon(FrameAd.class.getResource("/files/lr.png")));
		
		JLabel lblBtc = new JLabel("");
		lblBtc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(BTC_ADDRESS);
				clipboard.setContents(strSel, null);
				JOptionPane.showMessageDialog(null, "BTC Address copied to clipboard\n\r\n\r" + BTC_ADDRESS);
			}
		});
		lblBtc.setIcon(new ImageIcon(FrameAd.class.getResource("/files/btc.png")));
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		JLabel lblToKeepThis = new JLabel("to keep this project running and growing!");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJrat)
						.addComponent(lblPleaseConsiderDonating))
					.addContainerGap(46, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(359, Short.MAX_VALUE)
					.addComponent(btnContinue)
					.addContainerGap())
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLr)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblBtc)
					.addContainerGap(266, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblToKeepThis)
					.addContainerGap(388, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblJrat)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblPleaseConsiderDonating)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblToKeepThis)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblLr)
						.addComponent(lblBtc))
					.addPreferredGap(ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
					.addComponent(btnContinue)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
}
