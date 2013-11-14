package pro.jrat.ui.frames;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;

import pro.jrat.Constants;
import pro.jrat.Contributors;
import pro.jrat.Contributors.Donator;
import pro.jrat.Main;
import pro.jrat.common.Version;
import pro.jrat.utils.FlagUtils;

@SuppressWarnings("serial")
public class FrameAd extends BaseDialog {

	public static Image BACKGROUND;

	private DefaultListModel<String> model;

	static {
		try {
			BACKGROUND = ImageIO.read(Main.class.getResource("/files/bg_450x300.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			BACKGROUND = null;
		}
	}

	public static final String BTC_ADDRESS = "19jr3SU9wKyCxdgApo1KiHmoY59iWztrdv";
	public static final String LTC_ADDRESS = "LWSRG5APbDn7R699Wjj9b5g5CgJE8PR9Ez";

	public FrameAd() {
		setTitle("Welcome");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setModal(true);

		JLabel lblJrat = new JLabel(Constants.NAME + " " + Version.getVersion());
		lblJrat.setBounds(185, 11, 138, 31);
		lblJrat.setFont(new Font("Tahoma", Font.BOLD, 25));

		JLabel lblPleaseConsiderDonating = new JLabel("Please consider donating, any amount, ");
		lblPleaseConsiderDonating.setBounds(185, 53, 190, 14);

		JLabel lblBitcoin = new JLabel("");
		lblBitcoin.setBounds(185, 93, 81, 27);
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
		btnContinue.setBounds(359, 238, 75, 23);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});

		JLabel lblToKeepThis = new JLabel("to keep this project running and growing!");
		lblToKeepThis.setBounds(185, 73, 198, 14);

		JLabel lblLitecoin = new JLabel("");
		lblLitecoin.setBounds(272, 93, 81, 27);
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

		setContentPane(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (BACKGROUND != null) {
					g.drawImage(BACKGROUND, 0, 0, null);
				}
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(lblJrat);
		getContentPane().add(lblPleaseConsiderDonating);
		getContentPane().add(btnContinue);
		getContentPane().add(lblToKeepThis);
		getContentPane().add(lblBitcoin);
		getContentPane().add(lblLitecoin);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(185, 131, 168, 104);
		getContentPane().add(scrollPane);

		final List<Donator> donators = new Contributors().getDonators();

		model = new DefaultListModel<String>();

		JList<String> list = new JList<String>();
		list.setModel((ListModel<String>) model);
		for (Donator donator : donators) {
			model.addElement(donator.getName() + " - " + donator.getReason());
		}
		list.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				Donator donor = null;

				for (Donator donator : donators) {
					if (donator.getName().equals(value.toString().split(" - ")[0])) {
						donor = donator;
						break;
					}
				}

				setIcon(FlagUtils.getFlag(donor.getCountry()));

				return this;
			}
		});
		scrollPane.setViewportView(list);
	}
}
