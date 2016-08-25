import java.io.File;

public class Main {

	public static void main(String[] args) {
		try {
			new File("injected").createNewFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
