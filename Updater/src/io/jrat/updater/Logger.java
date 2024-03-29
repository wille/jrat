package io.jrat.updater;

import java.util.Date;

@SuppressWarnings("deprecation")
public class Logger {

	public static void log(Object obj) {
		Date date = new Date(System.currentTimeMillis());
		
		String hours = (date.getHours() + "").length() == 1 ? "0" + date.getHours() : Integer.toString(date.getHours());
		String minutes = (date.getMinutes() + "").length() == 1 ? "0" + date.getMinutes() : Integer.toString(date.getMinutes());
		String seconds = (date.getSeconds() + "").length() == 1 ? "0" + date.getSeconds() : Integer.toString(date.getSeconds());
		
		String time = hours + ":" + minutes + ":" + seconds;
		
		String s = "[" + time + "] [LOG]: " + obj.toString();
		
		System.out.println(s);
	}
}
