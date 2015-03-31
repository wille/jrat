package se.jrat.common;

import se.jrat.common.io.TransferData;

public abstract class TransferRunnable extends Thread {
	
	protected boolean pause;
	protected TransferData data;
	
	public TransferRunnable(TransferData data) {
		this.data = data;
	}

	public void stopTransfer() {
		
	}
	
	public void pause() {
		pause = !pause;
		
		if (pause) {
			data.setState(TransferData.State.PAUSED);
		} else {
			data.setState(TransferData.State.IN_PROGRESS);
			interrupt();
		}
	}

}
