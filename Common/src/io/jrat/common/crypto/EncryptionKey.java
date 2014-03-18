package io.jrat.common.crypto;

import io.jrat.common.hash.Md5;

import java.io.Serializable;


public class EncryptionKey implements Serializable {

	private static final long serialVersionUID = -1898914977368670398L;

	private String textKey;
	private byte[] rawKey;

	public EncryptionKey(String textKey) {
		this.textKey = textKey;
	}

	public String getTextualKey() {
		return this.textKey;
	}

	public byte[] getKey() throws Exception {
		if (rawKey == null) {
			Md5 md5 = new Md5();
			rawKey = md5.hash(getTextualKey().getBytes("UTF-8"));
		}

		return this.rawKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EncryptionKey) {
			EncryptionKey key = (EncryptionKey) obj;
			return key.textKey.equals(this.textKey);
		}

		return false;
	}
}
