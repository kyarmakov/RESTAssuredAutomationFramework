package requests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public final class UserRequestSpecification {
    private UserRequestSpecification() {}

    static RequestSpecification getRequestSpecification(String endpoint, Object requestPayload, Map<String, String> headers) {
        return RestAssured.given()
                .baseUri(endpoint)
                .headers(headers)
                .body(requestPayload);
    }

    static RequestSpecification getRequestSpecification(Object id) {
        return RestAssured.given()
                .pathParam("id", id);
    }
}
