package io.jrat.client.ui.frames;

import io.jrat.client.Constants;
import io.jrat.client.Contributors;
import io.jrat.client.Main;
import io.jrat.client.Contributors.Contributor;
import io.jrat.client.net.WebRequest;
import io.jrat.client.utils.FlagUtils;
import io.jrat.client.utils.NetUtils;
import io.jrat.common.Version;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


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
	private DefaultListModel<String> model;

	public FrameAbout() {
		super();
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAbout.class.getResource("/icons/information-button.png")));
		setTitle("About " + Constants.HOST + " " + Version.getVersion());
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
		lblJrat.setForeground(Color.RED);
		lblJrat.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JLabel lblPointOfOrigin = new JLabel("Point of origin:");

		JLabel lblSweden = new JLabel("Sweden");
		lblSweden.setIcon(new ImageIcon(FrameAbout.class.getResource("/flags/se.png")));

		JLabel lblWebsite = new JLabel("Website:");

		JLabel lblHttpredpoisncom = new JLabel("http://jrat.pro");
		lblHttpredpoisncom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetUtils.openUrl(WebRequest.domains[0]);
			}
		});
		lblHttpredpoisncom.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/url.png")));
		lblHttpredpoisncom.setForeground(Color.BLUE);

		JLabel lblWrittenBy = new JLabel("Written by:");

		JLabel lblRedpoisn = new JLabel("redpois0n (.com)");
		lblRedpoisn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NetUtils.openUrl("http://redpois0n.com");
			}
		});
		lblRedpoisn.setIcon(new ImageIcon(FrameAbout.class.getResource("/icons/action_disconnect.png")));
		lblRedpoisn.setForeground(Color.RED);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap(173, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE).addComponent(lblJrat).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblWebsite, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE).addComponent(lblWrittenBy, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)).addComponent(lblPointOfOrigin)).addGap(18).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblSweden, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(lblHttpredpoisncom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(lblRedpoisn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(lblJrat).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblSweden, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE).addComponent(lblPointOfOrigin, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblWebsite, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE).addComponent(lblHttpredpoisncom)).addGap(7).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblRedpoisn).addComponent(lblWrittenBy, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		JList<String> list = new JList<String>();
		final List<Contributor> contributors = new Contributors().getContributors();

		model = new DefaultListModel<String>();

		list.setModel((ListModel<String>) model);
		for (Contributor contributor : contributors) {
			model.addElement(contributor.getName() + " - " + contributor.getReason());
		}
		list.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				Contributor contributor = null;

				for (Contributor c : contributors) {
					if (c.getName().equals(value.toString().split(" - ")[0])) {
						contributor = c;
						break;
					}
				}

				setIcon(FlagUtils.getFlag(contributor.getCountry()));

				return this;
			}
		});
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
	}
}
