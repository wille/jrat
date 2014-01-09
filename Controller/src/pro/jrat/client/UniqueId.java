package pro.jrat.client;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.util.Random;

import pro.jrat.api.Logger;
import pro.jrat.client.exceptions.InvalidKeyException;
import pro.jrat.client.exceptions.MissingKeyException;
import pro.jrat.client.net.WebRequest;
import pro.jrat.common.codec.Hex;
import pro.jrat.common.hash.Sha256;

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
			Logger.log(Hex.encode(id));
		}

		HttpURLConnection archiveConnection = (HttpURLConnection) WebRequest.getConnection(Constants.HOST + "/misc/checkkey.php?key=" + Hex.encode(id), true);
		archiveConnection.connect();

		int response = archiveConnection.getResponseCode();

		if (response == 404) {
			throw new InvalidKeyException("Invalid key");
		} else if (response == 200) {
			return true;
		}

		throw new Exception("Response code: " + response);
	}

}