package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public final class UserRequests {
    private UserRequests() {}

    public static Response performPost(String endpoint, Object requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(requestPayload, headers);
        Response response = requestSpecification.post(endpoint);

        return response;
    }

    public static Response performGet(String endpoint, Object id) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(id);
        Response response = requestSpecification.get(endpoint + "/{id}");

        return response;
    }

    public static Response performPut(String endpoint, Object id, Object requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(id, requestPayload, headers);
        Response response = requestSpecification.put(endpoint + "/{id}");

        return response;
    }
}
