package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentUtilManager {
    static ExtentReports extent;

    public static ExtentReports getReportInstance(){
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("TestReport.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }

}
