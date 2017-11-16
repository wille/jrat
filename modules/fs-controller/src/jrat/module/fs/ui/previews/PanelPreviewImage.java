package jrat.module.fs.ui.previews;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelImage;
import jrat.controller.utils.Utils;
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
        add(imagePanel, BorderLayout.CENTER);

		JPopupMenu menu = new JPopupMenu();

		JMenuItem itemSave = new JMenuItem("Save");
		itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                save();
            }
        });
		menu.add(itemSave);

		JMenuItem itemClose = new JMenuItem("Close");
		itemClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                close();
            }
        });
        menu.add(itemClose);

        Utils.addPopup(imagePanel, menu);

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
