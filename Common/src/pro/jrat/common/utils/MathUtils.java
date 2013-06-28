package pro.jrat.common.utils;

public class MathUtils {

	public static int getPercentFromTotal(int subtotal, int total) {
		return (int) (((float) subtotal / (float) total) * 100);
	}

}
