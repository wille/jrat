package pro.jrat;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import pro.jrat.common.codec.Hex;
import pro.jrat.common.hash.Sha256;
import pro.jrat.exceptions.InvalidKeyException;
import pro.jrat.exceptions.MissingKeyException;

public final class UniqueId {
	
	private static byte[] systemId = null;

	public static final byte[] generateBinary() throws Exception {
		Random rn = new Random();
		
		String data = "";
		
		for (int i = 0; i < 256; i++) {
			data += rn.nextInt();
		}

		return Sha256.sha256(data.getBytes("UTF-8"));
	}
	
	public static byte[] getSystemId() throws Exception {
		if (systemId == null) {
			File file = new File("jrat.key");
			
			if (!file.exists()) {
				throw new MissingKeyException("Key file not found (jrat.key)");
			}
			
			FileInputStream input = new FileInputStream(file);
			systemId = new byte[32];
			input.read(systemId);
			input.close();
		}
		
		return systemId;
	}
	
	public static boolean validate(boolean b) throws MissingKeyException, InvalidKeyException, Exception {		
		byte[] id = getSystemId();
		
		if (b) {
			System.out.println(Hex.encode(id));
		}
										
		HttpURLConnection archiveConnection = (HttpURLConnection) new URL(Constants.HOST + "/misc/checkkey.php?key=" + Hex.encode(id)).openConnection();
		archiveConnection.connect();
		
		int response = archiveConnection.getResponseCode();
		
		if (response == 403 || response == 404) {
			throw new InvalidKeyException("Invalid key");
		} else if (response == 200) {
			return true;
		}
		
		throw new Exception("Response code: " + response);
	}

}
