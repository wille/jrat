package se.jrat.controller.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import se.jrat.common.Version;
import se.jrat.controller.Main;
import se.jrat.controller.utils.IconUtils;

@SuppressWarnings("serial")
public class DialogAbout extends BaseDialog {

    public static Image BACKGROUND;

    static {
        try {
            BACKGROUND = ImageIO.read(Main.class.getResource("/files/bg_450x300.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
            BACKGROUND = null;
        }
    }
	
	private JPanel contentPane;
	private JPanel contentPane_1;

	public DialogAbout() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogAbout.class.getResource("/icons/info.png")));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
        contentPane_1 = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (BACKGROUND != null) {
                    g.drawImage(BACKGROUND, 0, 0, null);
                }
            }
        };

		setContentPane(contentPane_1);
		
		JLabel lblCopyrightJrat = new JLabel("Copyright jRAT 2012-2015");
		lblCopyrightJrat.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblWwwjratse = new JLabel("www.jrat.se");
		
		JLabel lblWwwjratio = new JLabel("www.jrat.io");
		
		JLabel lblJrat = new JLabel("jRAT " + Version.getVersion());
		lblJrat.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel label = new JLabel("");
		label.setIcon(IconUtils.getIcon("icon-128x128"));
		GroupLayout gl_contentPane_1 = new GroupLayout(contentPane_1);
		gl_contentPane_1.setHorizontalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addContainerGap(267, Short.MAX_VALUE)
					.addComponent(lblCopyrightJrat)
					.addContainerGap())
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addContainerGap(98, Short.MAX_VALUE)
					.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane_1.createSequentialGroup()
							.addGap(134)
							.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblWwwjratse)
								.addComponent(lblWwwjratio))
							.addContainerGap(152, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane_1.createSequentialGroup()
							.addGroup(gl_contentPane_1.createParallelGroup(Alignment.LEADING)
								.addComponent(label)
								.addComponent(lblJrat))
							.addGap(101))))
		);
		gl_contentPane_1.setVerticalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane_1.createSequentialGroup()
					.addGap(21)
					.addComponent(lblJrat)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addGap(18)
					.addComponent(lblWwwjratio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWwwjratse)
					.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
					.addComponent(lblCopyrightJrat)
					.addContainerGap())
		);
		contentPane_1.setLayout(gl_contentPane_1);
	}
}
