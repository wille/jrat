package io.jrat.common.utils;

public class MathUtils {

	public static int getPercentFromTotal(long subtotal, long total) {
		return (int) (((float) subtotal / (float) total) * 100);
	}
	
	public static int getRemainingSeconds(long speed, long total) {
		if (speed == 0) {
			speed = 1;
		}
		
		return (int) (total / speed);
	}
	
	public static String getRemainingTime(long speed, long total) {
		int secondsLeft = getRemainingSeconds(speed, total);
		
		return getTimeFromSeconds(secondsLeft);
	}
	
	public static String getTimeFromSeconds(int sec) {
		int h = sec / 3600;
		int m = (sec % 3600) / 60;
		int s = sec % 60;
		
		String hours = h < 10 ? "0" + h : Integer.toString(h);
		String minutes = m < 10 ? "0" + m : Integer.toString(m);
		String seconds = s < 10 ? "0" + s : Integer.toString(s);

		return hours + ":" + minutes + ":" + seconds;
	}
}
