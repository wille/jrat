package su.jrat.client.extensions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import su.jrat.client.Main;
import su.jrat.client.Slave;
import su.jrat.client.packets.outgoing.AbstractOutgoingPacket;
import jrat.api.Connection;
import jrat.api.PacketBuilder;
import jrat.api.Queue;
import jrat.api.RATObject;
import jrat.api.Reader;
import jrat.api.Writer;

public class RATObjectFormat {

	public static RATObject format(final Slave s) {
		Connection con = new Connection(s.getConnection().getTimeout(), s.getConnection().getPass(), s.getConnection().getName());

		final DataOutputStream out = s.getDataOutputStream();
		final DataInputStream in = s.getDataInputStream();

		RATObject j = new RATObject(s.getIP(), s.getUniqueId(), con, new Writer() {

			@Override
			public void write(byte arg0) throws IOException {
				out.write(arg0);
			}

			@Override
			public void write(byte[] arg0) throws IOException {
				out.write(arg0);
			}

			@Override
			public void writeLine(String arg0) throws IOException {
				s.writeLine(arg0);
			}

			@Override
			public void writeShort(short arg0) throws IOException {
				out.writeShort(arg0);
			}

			@Override
			public void writeInt(int arg0) throws IOException {
				out.writeInt(arg0);
			}

			@Override
			public void writeLong(long arg0) throws IOException {
				out.writeLong(arg0);
			}

			@Override
			public void writeBoolean(boolean arg0) throws IOException {
				out.writeBoolean(arg0);
			}

			@Override
			public void writeChar(char arg0) throws IOException {
				out.writeChar(arg0);
			}

		}, new Reader() {

			@Override
			public byte read() throws IOException {
				return in.readByte();
			}

			@Override
			public void readFully(byte[] arg0, int arg1, int arg2) throws IOException {
				in.readFully(arg0, arg1, arg2);
			}

			@Override
			public int readInt() throws IOException {
				return in.readInt();
			}

			@Override
			public long readLong() throws IOException {
				return in.readLong();
			}

			@Override
			public short readShort() throws IOException {
				return in.readShort();
			}

			@Override
			public String readLine() throws IOException {
				try {
					return s.readLine();
				} catch (Exception ex) {
					throw new IOException(ex);
				}
			}

			@Override
			public boolean readBoolean() throws IOException {
				return in.readBoolean();
			}

			@Override
			public char readChar() throws IOException {
				return in.readChar();
			}

		}, in, out, new Queue() {
			@Override
			public void addToSendQueue(final PacketBuilder arg0, final RATObject rat) throws Exception {
				Slave slave = getFromRATObject(rat);

				if (slave != null) {
					AbstractOutgoingPacket packet = new AbstractOutgoingPacket() {
						@Override
						public void write(Slave slave, DataOutputStream dos) throws Exception {
							arg0.write(rat, slave.getDataOutputStream(), slave.getDataInputStream());
						}

						@Override
						public byte getPacketId() {
							return arg0.getHeader();
						}
					};

					slave.addToSendQueue(packet);
				} else {
					throw new NullPointerException("No connection found for IP " + rat.getIP());
				}
			}
		});
		return j;
	}

	public static Slave getFromRATObject(RATObject obj) {
		String ip = obj.getIP();

		for (int i = 0; i < Main.connections.size(); i++) {
			Slave slave = Main.connections.get(i);

			if (slave.getIP().equals(ip)) {
				return slave;
			}
		}

		return null;
	}

}
