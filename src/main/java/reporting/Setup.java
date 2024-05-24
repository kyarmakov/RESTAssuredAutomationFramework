package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.Arrays;

public class Setup implements ITestListener {
    public static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public void onStart(ITestContext context) {
        String fileName = ExtentReportManager.getReportNameWithTimestamp();
        String fullReportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + fileName;
        extentReports = ExtentReportManager.createInstance(fullReportPath,
                "Test API Automation Report", "Test Execution Report");
    }

    public void onFinish(ITestContext context) {
        if (extentReports != null)
            extentReports.flush();
    }

    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logFailureDetails(result.getThrowable().getMessage());

        String stacktrace = Arrays.toString(result.getThrowable().getStackTrace());
        stacktrace = stacktrace.replaceAll(",", "<br/>");
        String formattedTrace = "<details>\n" +
                "<summary>Click Here To See Exception Logs</summary>\n" +
                "" + stacktrace + "\n" +
                "</details>";
        ExtentReportManager.logExceptionDetails(formattedTrace);
    }
}

