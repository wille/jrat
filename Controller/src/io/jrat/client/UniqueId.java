package io.jrat.client;

import io.jrat.client.exceptions.InvalidKeyException;
import io.jrat.client.exceptions.MissingKeyException;
import io.jrat.client.net.WebRequest;
import io.jrat.common.Logger;
import io.jrat.common.codec.Hex;
import io.jrat.common.hash.Sha256;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.util.Random;


public final class UniqueId {

	private static byte[] systemId = null;

	public static final byte[] generateBinary() throws Exception {
		Random rn = new Random();

		String data = "";

		for (int i = 0; i < 256; i++) {
			data += rn.nextInt();
		}

		Sha256 sha256 = new Sha256();
		
		return sha256.hash(data.getBytes("UTF-8"));
	}

	public static byte[] getSystemId() throws Exception {
		if (systemId == null) {
			File file = Globals.getKeyFile();

			if (!file.exists()) {
				throw new MissingKeyException("Key file not found (" + Globals.getKeyFile().getAbsolutePath() + ")");
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

		HttpURLConnection archiveConnection = (HttpURLConnection) WebRequest.getConnection(Constants.HOST + "/api/validate.php?key=" + Hex.encode(id) + "&type=jrat", true);
		archiveConnection.connect();

		int response = archiveConnection.getResponseCode();
		archiveConnection.disconnect();
		if (response == 404) {
			throw new InvalidKeyException("Invalid key");
		} else if (response == 200) {
			return true;
		}

		throw new Exception("Response code: " + response);
	}

}