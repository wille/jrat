package pro.jrat.client.ui.frames;

import java.awt.Component;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class QuickFrame extends BaseFrame {

	public QuickFrame(Component component) {
		super();
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setSize(component.getWidth(), component.getHeight());
		super.add(component);
	}

	public void addClosingListener(WindowAdapter l) {
		super.addWindowListener(l);
	}

	public void setIcon(ImageIcon icon) {
		super.setIconImage(icon.getImage());
	}

	public void setTitle(String title) {
		super.setTitle(title);
	}

	public void close() {
		super.setVisible(false);
		super.dispose();
	}

}
