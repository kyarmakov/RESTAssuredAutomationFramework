package users;

import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.BeforeMethod;
import reporting.Setup;

import java.util.Map;

public class BaseTest {
    @BeforeMethod
    protected void setup(Object[] data) {
        Map<String, Object> userTest = (Map<String, Object>) data[0];
        ExtentTest test = Setup.extentReports.createTest("Test name - " + userTest.get("testName"), (String) userTest.get("description"));
        Setup.extentTest.set(test);
    }
}
