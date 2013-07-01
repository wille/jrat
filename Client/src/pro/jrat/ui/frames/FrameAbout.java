package pro.jrat.ui.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pro.jrat.Constants;
import pro.jrat.Main;
import pro.jrat.common.Version;
import pro.jrat.utils.NetworkUtils;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class FrameAbout extends JFrame {

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
		super();
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAbout.class.getResource("/icons/information-button.png")));
		setTitle("About jRAT BETA " + Version.getVersion());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (BACKGROUND != null) {
					g.drawImage(BACKGROUND, 0, 0, null);
				}
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/delete.png")));

		JLabel lblJrat = new JLabel(Version.getVersion());
		lblJrat.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JLabel lblPointOfOrigin = new JLabel("Point of origin:");

		JLabel lblSweden = new JLabel("Sweden");
		lblSweden.setIcon(new ImageIcon(FrameAbout.class.getResource("/flags/se.png")));

		JLabel lblWebsite = new JLabel("Website:");

		JLabel lblHttpredpoisncom = new JLabel("http://jrat.pro");
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
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblJrat).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, 172, GroupLayout.PREFERRED_SIZE).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblPointOfOrigin).addComponent(lblWebsite, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE).addComponent(lblWrittenBy, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblSweden, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(lblHttpredpoisncom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblRedpoisn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED))))).addContainerGap()))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(6).addComponent(lblJrat).addGap(118).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblSweden, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE).addComponent(lblPointOfOrigin, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblWebsite, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE).addComponent(lblHttpredpoisncom)).addGap(7).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblRedpoisn).addComponent(lblWrittenBy, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
	}
}
