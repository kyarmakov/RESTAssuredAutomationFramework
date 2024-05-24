package reporting;

import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

public final class ReportingUtils {
    private ReportingUtils() {}

    public static void printRequestLogReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);

        if (queryableRequestSpecification.getMethod().equals("POST"))
            ExtentReportManager.logInfoDetails("Endpoint: " + queryableRequestSpecification.getBaseUri());

        if (queryableRequestSpecification.getMethod().equals("GET") ||
                queryableRequestSpecification.getMethod().equals("DELETE") ||
                queryableRequestSpecification.getMethod().equals("PUT")
        )
            ExtentReportManager.logInfoDetails("id: " + queryableRequestSpecification.getPathParams().get("id"));

        ExtentReportManager.logInfoDetails("Method: " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers: ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());

        if (queryableRequestSpecification.getMethod().equals("POST") || queryableRequestSpecification.getMethod().equals("PUT")) {
            ExtentReportManager.logInfoDetails("Request body: ");
            ExtentReportManager.logJson(queryableRequestSpecification.getBody());
        }
    }

    public static void printResponseLogReport(Response response) {
        ExtentReportManager.logInfoDetails("Status code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response headers: ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body: ");
        ExtentReportManager.logJson(response.getBody().prettyPrint());
    }
}
