package io.jrat.controller.ui.frames;

import iconlib.IconUtils;
import io.jrat.controller.ErrorDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public abstract class FrameTextPane extends BaseFrame {

	private JPanel contentPane;
	private JTextPane txt;

	public JTextPane getPane() {
		return txt;
	}

	public void append(String str) {
		try {
			txt.getDocument().insertString(txt.getDocument().getLength(), str + "\n", null);
		} catch (Exception e) {
			ErrorDialog.create(e);
			e.printStackTrace();
		}
	}

	public FrameTextPane() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		txt = new JTextPane();
		scrollPane.setViewportView(txt);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
				setVisible(false);
				dispose();
			}
		});
		btnOk.setIcon(IconUtils.getIcon("enabled"));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser ch = new JFileChooser();
				ch.showSaveDialog(null);
				File file = ch.getSelectedFile();
				if (file != null) {
					try {
						FileWriter writer = new FileWriter(file);
						writer.write(txt.getText());
						writer.close();
					} catch (Exception ex) {
						ex.printStackTrace();
						ErrorDialog.create(ex);
					}
				}
			}
		});
		btnSave.setIcon(IconUtils.getIcon("save"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGap(248).addComponent(btnSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)).addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)).addGap(0)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnOk).addComponent(btnSave)).addGap(4)));
		contentPane.setLayout(gl_contentPane);
	}

	public abstract void exit();

}
