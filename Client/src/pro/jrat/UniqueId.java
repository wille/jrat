package pro.jrat;

import java.io.File;
import java.io.FileInputStream;

import pro.jrat.common.hash.Sha256;

public final class UniqueId {
	
	private static byte[] systemId = null;

	public static final byte[] generateBinary() throws Exception {
		String name = System.getProperty("user.name");
		String os = System.getProperty("os.name") + " " + System.getProperty("os.version");
		String country = System.getProperty("user.country") + " " + System.getProperty("user.country.format") + " " + System.getProperty("user.language");

		String data = "&name=" + name + "&os=" + os + "&geo=" + country;

		return Sha256.sha256(data.getBytes("UTF-8"));
	}
	
	public static byte[] getSystemId() throws Exception {
		if (systemId == null) {
			File file = new File("jrat.key");
			FileInputStream input = new FileInputStream(file);
			systemId = new byte[32];
			input.read(systemId);
			input.close();
		}
		
		return systemId;
	}

}
