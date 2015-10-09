package io.jrat.common.crypto;

import java.security.KeyPair;
import java.security.PublicKey;

public abstract class KeyExchanger {
	
	protected KeyPair pair;
	protected PublicKey remoteKey;
	
	public KeyExchanger(KeyPair pair) {
		this.pair = pair;
	}

	/**
	 * Read remote public key
	 * @return
	 * @throws Exception
	 */
	public abstract PublicKey readRemoteKey() throws Exception;
	
	/**
	 * write public key from keypair
	 * @throws Exception
	 */
	public abstract void writePublicKey() throws Exception;
}
