package com.redpois0n.plugins;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jrat.project.api.Connection;
import org.jrat.project.api.IReader;
import org.jrat.project.api.IWriter;
import org.jrat.project.api.PacketBuilder;
import org.jrat.project.api.Queue;
import org.jrat.project.api.RATObject;

import com.redpois0n.Slave;

public class RATObjectFormat {

	public static RATObject format(final Slave s) {
		Connection con = new Connection(s.getConnection().getTimeout(), s.getConnection().getKey().getTextualKey(), s.getConnection().getPass(), s.getConnection().getName());
		
		final DataOutputStream out = s.getDataOutputStream();
		final DataInputStream in = s.getDataInputStream();
		
		RATObject j = new RATObject(s.getIP(), con, new IWriter() {

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
				
		}, new IReader() {

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
			public void addToSendQueue(PacketBuilder arg0, RATObject rat) throws Exception {
				arg0.write(rat);
			}	
		});
		return j;
	}

}
