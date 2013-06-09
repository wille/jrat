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


@SuppressWarnings("serial")
public class FrameAd extends BaseDialog {
	
	public static final String BTC_ADDRESS = "19jr3SU9wKyCxdgApo1KiHmoY59iWztrdv";
	public static final String LTC_ADDRESS = "LWSRG5APbDn7R699Wjj9b5g5CgJE8PR9Ez";

	public FrameAd() {
		setTitle("Welcome");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setModal(true);
		
		JLabel lblJrat = new JLabel("jRAT " + Version.getVersion());
		lblJrat.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JLabel lblPleaseConsiderDonating = new JLabel("Please consider donating, any amount, ");
		
		JLabel lblBitcoin = new JLabel("");
		lblBitcoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(BTC_ADDRESS);
				clipboard.setContents(strSel, null);
				JOptionPane.showMessageDialog(null, "BTC Address copied to clipboard\n\r\n\r" + BTC_ADDRESS);
			}
		});
		lblBitcoin.setIcon(new ImageIcon(FrameAd.class.getResource("/files/btc.png")));
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		JLabel lblToKeepThis = new JLabel("to keep this project running and growing!");
		
		JLabel lblLitecoin = new JLabel("");
		lblLitecoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(BTC_ADDRESS);
				clipboard.setContents(strSel, null);
				JOptionPane.showMessageDialog(null, "LTC Address copied to clipboard\n\r\n\r" + LTC_ADDRESS);
			}
		});
		lblLitecoin.setIcon(new ImageIcon(FrameAd.class.getResource("/files/ltc.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJrat)
						.addComponent(lblPleaseConsiderDonating))
					.addContainerGap(244, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(359, Short.MAX_VALUE)
					.addComponent(btnContinue)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblToKeepThis)
					.addContainerGap(236, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBitcoin)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLitecoin, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(266, Short.MAX_VALUE))
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblBitcoin)
							.addPreferredGap(ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
							.addComponent(btnContinue))
						.addComponent(lblLitecoin, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
}
