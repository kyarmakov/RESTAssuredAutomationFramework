package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import reporting.ReportingUtils;

import java.util.Map;

public final class UserRequests {
    private UserRequests() {}

    public static Response performPost(String endpoint, Object requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(requestPayload, headers);
        Response response = requestSpecification.post(endpoint);

        ReportingUtils.printRequestLogReport(requestSpecification);
        ReportingUtils.printResponseLogReport(response);

        return response;
    }

    public static Response performGet(String endpoint, Object id) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(id);
        Response response = requestSpecification.get(endpoint + "/{id}");

        ReportingUtils.printRequestLogReport(requestSpecification);
        ReportingUtils.printResponseLogReport(response);

        return response;
    }

    public static Response performPut(String endpoint, Object id, Object requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(id, requestPayload, headers);
        Response response = requestSpecification.put(endpoint + "/{id}");

        ReportingUtils.printRequestLogReport(requestSpecification);
        ReportingUtils.printResponseLogReport(response);

        return response;
    }

    public static Response performDelete(String endpoint, Object id) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(id);
        Response response = requestSpecification.delete(endpoint + "/{id}");

        ReportingUtils.printRequestLogReport(requestSpecification);
        ReportingUtils.printResponseLogReport(response);

        return response;
    }
}
