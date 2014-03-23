package io.jrat.client.ui.frames;

import io.jrat.client.Main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameAbout extends BaseDialog {

    public static Image BACKGROUND;

    static {
        try {
            BACKGROUND = ImageIO.read(Main.class.getResource("/files/bg_about_450x300.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
            BACKGROUND = null;
        }
    }
	
	private JPanel contentPane;

	public FrameAbout() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAbout.class.getResource("/icons/info.png")));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
        contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (BACKGROUND != null) {
                    g.drawImage(BACKGROUND, 0, 0, null);
                }
            }
        };

		setContentPane(contentPane);
	}

}
