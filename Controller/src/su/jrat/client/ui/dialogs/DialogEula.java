package su.jrat.client.ui.dialogs;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import su.jrat.client.Constants;
import su.jrat.client.Main;
import su.jrat.client.settings.Settings;
import su.jrat.client.threads.ThreadCountDown;
import su.jrat.client.utils.IOUtils;
import su.jrat.common.Version;


@SuppressWarnings("serial")
public class DialogEula extends BaseDialog {

	private JPanel contentPane;

	public DialogEula(final boolean view) {
		super();
		setModal(true);
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (view) {
					setVisible(false);
					dispose();
				} else {
					System.exit(0);
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogEula.class.getResource("/icons/gavel.png")));
		setTitle(Constants.NAME + " " + Version.getVersion() + " EULA");
		setResizable(false);
		setBounds(100, 100, 468, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnDisagree = new JButton("Disagree");
		btnDisagree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnDisagree.setIcon(new ImageIcon(DialogEula.class.getResource("/icons/disconnect.png")));

		JButton btnAgree = new JButton("Agree (0)");
		btnAgree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!view) {
					// FrameLogin frame = new FrameLogin();
					// frame.setVisible(true);
					// TODO
					Main.instance.setVisible(true);
				}
				Settings.getGlobal().setVal("showeula", "true");
				setVisible(false);
				dispose();
			}
		});
		btnAgree.setIcon(new ImageIcon(DialogEula.class.getResource("/icons/gavel.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap(111, Short.MAX_VALUE).addComponent(btnAgree).addGap(33).addComponent(btnDisagree).addGap(114)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnDisagree).addComponent(btnAgree)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JTextPane txtEULA = new JTextPane();
		txtEULA.setEditable(false);

		scrollPane.setViewportView(txtEULA);
		contentPane.setLayout(gl_contentPane);
		try {
			txtEULA.setText(IOUtils.readString(Main.class.getResourceAsStream("/files/eula.txt")));
			txtEULA.setSelectionStart(0);
			txtEULA.setSelectionEnd(0);
		} catch (Exception e) {
			txtEULA.setText("Failed to load EULA: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error loading EULA: " + e.getClass().getSimpleName() + ": " + e.getMessage() + ", you now automatically agreed to it", "Error", JOptionPane.ERROR_MESSAGE);
		}
		if (!view) {
			new ThreadCountDown(this, btnAgree).start();
		}

		setLocationRelativeTo(null);
	}
}
