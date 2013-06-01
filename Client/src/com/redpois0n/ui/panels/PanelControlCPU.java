package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import com.redpois0n.Slave;

@SuppressWarnings("serial")
public class PanelControlCPU extends PanelControlParent {
	
	private JSpinner sSecs;
	private JSpinner sThreads;
	
	public int getSeconds() {
		return Integer.parseInt(sSecs.getValue().toString()) * 1000;
	}
	
	public int getThreads() {
		return Integer.parseInt(sThreads.getValue().toString());
	}

	public PanelControlCPU(Slave sl) {
		super(sl);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Drain CPU"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(302, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(185, Short.MAX_VALUE))
		);
		
		JLabel lblThreads = new JLabel("Threads:");
		
		JLabel lblSeconds = new JLabel("Seconds:");
		
		sThreads = new JSpinner();
		sThreads.setModel(new SpinnerNumberModel(50, 10, 1000, 1));
		
		sSecs = new JSpinner();
		sSecs.setModel(new SpinnerNumberModel(10, 1, 1000, 1));
		
		JButton btnSend = new JButton("Send");
		btnSend.setIcon(new ImageIcon(PanelControlCPU.class.getResource("/icons/right.png")));
		
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < getThreads(); i++) {
					new Thread() {
						public void run() {
							for (int s = 0; s < getSeconds() / 1000; s++) {
								try {
									
									Thread.sleep(1000L);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}.start();
				}
			}
		});
		btnTest.setIcon(new ImageIcon(PanelControlCPU.class.getResource("/icons/burn.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblThreads)
						.addComponent(lblSeconds))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(sSecs)
						.addComponent(sThreads, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
					.addContainerGap(211, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(94, Short.MAX_VALUE)
					.addComponent(btnTest)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSend)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblThreads)
						.addComponent(sThreads, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSeconds)
						.addComponent(sSecs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSend)
						.addComponent(btnTest))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
}
