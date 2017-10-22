package jrat.controller.ui.frames;

import iconlib.IconUtils;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet62PreviewImage;
import jrat.controller.ui.panels.PanelImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class FramePreviewImage extends BaseFrame {

	public static final Map<Slave, FramePreviewImage> INSTANCES = new HashMap<Slave, FramePreviewImage>();

	private JPanel contentPane;
	private String file;

	private PanelImage panel_img;
	private JButton btnReloadImage;

	public PanelImage getPanel() {
		return panel_img;
	}

	public void setButtonEnabled(boolean b) {
		btnReloadImage.setEnabled(b);
	}

	public FramePreviewImage(Slave slave, String f) {
		super(slave);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePreviewImage.class.getResource("/icons/image.png")));
		setTitle("Image Preview - " + f);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		this.file = f;
		this.slave = slave;
		INSTANCES.put(slave, this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 562, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

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
		btnReloadImage.setIcon(IconUtils.getIcon("update"));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showSaveDialog(null);
				File file = c.getSelectedFile();
				if (file != null) {
					try {
						ImageIO.write(getPanel().getBufferedImage(), "png", file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error saving image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setIcon(IconUtils.getIcon("save"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReloadImage).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave).addGap(342)).addGroup(gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReloadImage).addComponent(btnSave)).addGap(10)));
		panel.setLayout(new BorderLayout(0, 0));

		panel_img = new PanelImage();
		panel.add(panel_img, BorderLayout.CENTER);
		contentPane.setLayout(gl_contentPane);

		reload();
	}

	public void reload() {
		slave.addToSendQueue(new Packet62PreviewImage(file));
	}

	public void exit() {
		INSTANCES.remove(slave);
	}
}
