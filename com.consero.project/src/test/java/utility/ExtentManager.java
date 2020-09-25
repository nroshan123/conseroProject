package utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
private static ExtentReports report = null;
public static String fileSeparator = System.getProperty("file.separator");
public static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public synchronized static ExtentReports getInstance() {
		if (report == null) {
			Date date = new Date();
			String todayDate = dateFormat.format(date);
			String TimeStamp = date.toString().replace(" ", "_").replace(":", "_");
			String folderPath = System.getProperty("user.dir") + "\\src\\test\\resources\\reports" + fileSeparator + todayDate;
			File reportFolder = new File(folderPath);
			if (!reportFolder.exists())
				reportFolder.mkdirs();
			String reportPath = reportFolder + fileSeparator + TimeStamp + ".html";

			report = new ExtentReports(reportPath);
		}
		return report;
	}
}
