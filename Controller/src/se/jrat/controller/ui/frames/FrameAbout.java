package se.jrat.controller.ui.frames;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import se.jrat.controller.Main;
import se.jrat.controller.ui.dialogs.BaseDialog;

@SuppressWarnings("serial")
public class FrameAbout extends BaseDialog {

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

	public FrameAbout() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAbout.class.getResource("/icons/info.png")));
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
		GroupLayout gl_contentPane_1 = new GroupLayout(contentPane_1);
		gl_contentPane_1.setHorizontalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 444, Short.MAX_VALUE)
		);
		gl_contentPane_1.setVerticalGroup(
			gl_contentPane_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 271, Short.MAX_VALUE)
		);
		contentPane_1.setLayout(gl_contentPane_1);
	}

}
