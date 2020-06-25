package utility;

import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
private static ExtentReports report = null;
	
	public synchronized static ExtentReports getInstance() {
		if(report==null) {			
			Date d=new Date();
			String TimeStamp = d.toString().replace(" ", "_").replace(":", "_");
			String ReportPath = System.getProperty
					("user.dir")+"\\src\\test\\resources\\reports\\r_"+TimeStamp+".html";
			
			report = new ExtentReports(  ReportPath );
		}	
		return report;
	}
}
