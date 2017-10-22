package jrat.controller.ui.frames;

import jrat.common.Version;
import jrat.controller.Constants;
import oslib.OperatingSystem;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;


@SuppressWarnings("serial")
public class FrameDebugInfo extends BaseFrame {

	private JPanel contentPane;
	private JTextPane textPane;

	public FrameDebugInfo() {
		setTitle("Debug Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 439, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel lblDebugAndError = new JLabel("Debug and error reporting information:");

		JButton btnCopyToClipboard = new JButton("Copy to Clipboard");
		btnCopyToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = textPane.getText();
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(str);
				clipboard.setContents(strSel, null);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE).addGap(1)).addGroup(gl_contentPane.createSequentialGroup().addComponent(lblDebugAndError).addPreferredGap(ComponentPlacement.RELATED, 97, Short.MAX_VALUE).addComponent(btnCopyToClipboard).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(5).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblDebugAndError).addComponent(btnCopyToClipboard)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)));

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		contentPane.setLayout(gl_contentPane);

		loadData();
	}

	public void loadData() {
		StringBuilder sb = new StringBuilder();

		sb.append(Constants.NAME + " Version: " + Version.getVersion() + "\n");
		sb.append("JVM Version: " + System.getProperty("java.vm.version") + "\n");
		sb.append("Java Runtime Version: " + System.getProperty("java.runtime.version") + "\n");
		sb.append("Java Version: " + System.getProperty("java.version") + "\n");
		sb.append("Java Class Version: " + System.getProperty("java.class.version") + "\n");
		sb.append("Java Spec Version: " + System.getProperty("java.specification.version") + "\n");

		sb.append("Operating System: " + OperatingSystem.getOperatingSystem().getDetailedString() + "\n");
		sb.append("Arch: " + OperatingSystem.getOperatingSystem().getArch().getName() + "\n");
		sb.append("DE: " + OperatingSystem.getOperatingSystem().getDesktopEnvironment().getDisplayString() + "\n");

		String date = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
		sb.append("Date: " + date);

		textPane.setText(sb.toString());
	}
}
