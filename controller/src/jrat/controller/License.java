package jrat.controller;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import jrat.common.hash.Sha256;
import jrat.controller.exceptions.InvalidKeyException;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public final class License {

	private static byte[] systemId = null;

	public static byte[] generateBinary() throws Exception {
		Random rn = new Random();

		String data = "";

		for (int i = 0; i < 256; i++) {
			data += rn.nextInt();
		}

		Sha256 sha256 = new Sha256();
		
		return sha256.hash(data.getBytes("UTF-8"));
	}

	public static Map<String, String> getLicense() throws Exception {
		Map args = new HashMap();
		args.put(JsonReader.USE_MAPS, true);

		Map<String, String> license = (JsonObject) JsonReader.jsonToJava(new FileInputStream(Globals.getKeyFile()), args);

		return license;
	}

	public static boolean validate(boolean b) throws Exception {
		Map<String, String> license = getLicense();
		String key = license.get("key");

		HttpURLConnection archiveConnection = (HttpURLConnection) new URL(Constants.HOST + "/api/validate/" + key).openConnection();
		archiveConnection.connect();

		int response = archiveConnection.getResponseCode();
		archiveConnection.disconnect();
		if (response == 404) {
			throw new InvalidKeyException("Invalid key");
		} else if (response == 410) {
			throw new InvalidKeyException("Key expired");
		} else if (response == 403) {
			throw new InvalidKeyException("Not paid");
		} else if (response == 200) {
			return true;
		}

		throw new Exception("Response code: " + response);
	}

}
