package pro.jrat.extractor;

public class Utils {

	public static int getPercentFromTotal(int subtotal, int total) {
		return (int) (((float) subtotal / (float) total) * 100);
	}
	
}
