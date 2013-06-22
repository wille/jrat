package pro.jrat.ui.frames;

import javax.swing.JFrame;

import com.redpois0n.common.OperatingSystem;

@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {
	
	public BaseFrame() {
		
	}
	
	@Override
	public void setVisible(boolean t) {
		super.setVisible(true);
		
		if (OperatingSystem.getOperatingSystem() != OperatingSystem.WINDOWS) {
			super.pack();
		}
	}

}
