package se.jrat.client.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import se.jrat.client.Slave;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class FrameTraffic extends BaseFrame {

	private JPanel contentPane;
	publicRENIOVEOEDASDASD
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getSent().setText("0 bytes");
				getReceived().setText("0 bytes");
				sl.setReceived(0L);
				sl.setSent(0L);
			}
		});
		btnReset.setIcon(IconUtils.getIcon("delete"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap(226, Short.MAX_VALUE).addComponent(btnReset).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE).addContainerGap()).addComponent(panel, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnOk).addComponent(btnReset)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JLabel lblReceived = new JLabel("Received");

		lblReceivedM = new JLabel(sl.getReceived() + " bytes");

		JLabel lblSent = new JLabel("Sent");

		lblSentM = new JLabel(sl.getSent() + " bytes");

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setIndeterminate(true);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(38).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblSent).addComponent(lblReceived)).addGap(18).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblReceivedM).addComponent(lblSentM)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(progressBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(72, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblReceivedM).addComponent(lblReceived)).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(progressBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblSentM).addComponent(lblSent))).addContainerGap(112, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
