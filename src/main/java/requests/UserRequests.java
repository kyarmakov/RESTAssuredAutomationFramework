package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public final class UserRequests {
    private UserRequests() {}

    public static Response performPost(String endpoint, Object requestPayload, Map<String, String> headers) {
        RequestSpecification requestSpecification = UserRequestSpecification.getRequestSpecification(endpoint, requestPayload, headers);
        Response response = requestSpecification.post();

        return response;
    }
}
