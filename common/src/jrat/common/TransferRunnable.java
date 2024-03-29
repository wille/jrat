package jrat.common;

import jrat.common.io.TransferData;

public abstract class TransferRunnable extends Thread {
	
	protected boolean pause;
	protected TransferData data;
	
	public TransferRunnable() {
		
	}
	
	public TransferRunnable(TransferData data) {
		this.data = data;
	}

    public void pause() {
		if (data.getState() == TransferData.State.COMPLETED || data.getState() == TransferData.State.ERROR) {
			return;
		}
		
		pause = !pause;
		
		if (pause) {
			data.setState(TransferData.State.PAUSED);
		} else {
			data.setState(TransferData.State.IN_PROGRESS);
			interrupt();
		}
	}
	
	public TransferData getData() {
		return this.data;
	}

}
