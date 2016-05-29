package io.jrat.controller.ui.dialogs;

import iconlib.IconUtils;
import io.jrat.common.Version;
import io.jrat.controller.Constants;
import io.jrat.controller.Main;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.utils.IOUtils;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class DialogEula extends JDialog {

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
		btnDisagree.setIcon(IconUtils.getIcon("forbidden"));
		
		JButton btnAgree = new JButton("Agree");
		btnAgree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!view) {
					Main.instance.setVisible(true);
				}
				Settings.getGlobal().setVal("showeula", "true");
				setVisible(false);
				dispose();
			}
		});
		btnAgree.setIcon(IconUtils.getIcon("gavel"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap(111, Short.MAX_VALUE).addComponent(btnAgree).addGap(33).addComponent(btnDisagree).addGap(114)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnDisagree).addComponent(btnAgree)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JTextPane txtEULA = new JTextPane();
		txtEULA.setEditable(false);

		scrollPane.setViewportView(txtEULA);
		contentPane.setLayout(gl_contentPane);
		try {
			txtEULA.setText(IOUtils.readString(Main.class.getResourceAsStream("/files/eula.txt")).replace("Version", Version.getVersion()));
			txtEULA.setSelectionStart(0);
			txtEULA.setSelectionEnd(0);
		} catch (Exception e) {
			txtEULA.setText("Failed to load EULA: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error loading EULA: " + e.getClass().getSimpleName() + ": " + e.getMessage() + ", you now automatically agreed to it", "Error", JOptionPane.ERROR_MESSAGE);
		}

		setLocationRelativeTo(null);
	}
}
