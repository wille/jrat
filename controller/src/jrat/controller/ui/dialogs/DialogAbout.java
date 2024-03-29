package jrat.controller.ui.dialogs;

import jrat.common.Version;
import jrat.controller.Main;
import jrat.controller.VersionChecker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;

@SuppressWarnings("serial")
public class DialogAbout extends JDialog {

    public static Image BACKGROUND;

    static {
        try {
            BACKGROUND = ImageIO.read(Main.class.getResource("/files/java-background.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
            BACKGROUND = null;
        }
    }

    private JLabel lblUpToDate;

	public DialogAbout() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogAbout.class.getResource("/system-info.png")));
		setBounds(100, 100, 700, 400);
        JPanel contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (BACKGROUND != null) {
                    g.drawImage(BACKGROUND, 0, getHeight() - BACKGROUND.getHeight(null), null);
                }
            }
        };
        contentPane.setBackground(Color.WHITE);

		setContentPane(contentPane);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(DialogAbout.class.getResource("/icon-128x128.png")));
		
		JLabel lblJrat = new JLabel("jRAT");
		lblJrat.setForeground(Color.GRAY);
		lblJrat.setFont(new Font("Tahoma", Font.BOLD, 35));

        JLabel lblVersion = new JLabel("Version " + Version.getVersion());
		
		lblUpToDate = new JLabel("Loading...");
		lblUpToDate.setForeground(Color.LIGHT_GRAY);
		lblUpToDate.setFont(new Font("Tahoma", Font.ITALIC, 11));
		GroupLayout gl_contentPane_1 = new GroupLayout(contentPane);
		gl_contentPane_1.setHorizontalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addGap(231)
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJrat)
						.addComponent(lblVersion)
						.addComponent(lblUpToDate))
					.addPreferredGap(ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
					.addComponent(label)
					.addContainerGap())
		);
		gl_contentPane_1.setVerticalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(label))
						.addGroup(gl_contentPane_1.createSequentialGroup()
							.addGap(24)
							.addComponent(lblJrat)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblVersion)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblUpToDate)))
					.addContainerGap(232, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane_1);
		
		new Thread() {
			@Override
			public void run() {
				VersionChecker checker = new VersionChecker();
				
				if (!checker.isUpToDate()) {
					lblUpToDate.setText("jRAT is not up to date - Latest version: " + checker.getLatest());
					lblUpToDate.setFont(new Font("Tahoma", Font.BOLD, 11));
					lblUpToDate.setForeground(Color.red);
				} else {
					lblUpToDate.setText("jRAT is up to date");
				}
			}
		}.start();
	}
}
