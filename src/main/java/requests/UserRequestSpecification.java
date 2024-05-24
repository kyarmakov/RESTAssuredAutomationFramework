package requests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public final class UserRequestSpecification {
    private UserRequestSpecification() {}

    static RequestSpecification getRequestSpecification(Object requestPayload, Map<String, String> headers) {
        return RestAssured.given()
                .headers(headers)
                .body(requestPayload);
    }

    static RequestSpecification getRequestSpecification(Object id) {
        return RestAssured.given()
                .pathParam("id", id);
    }

    static RequestSpecification getRequestSpecification(Object id, Object requestPayload, Map<String, String> headers) {
        return RestAssured.given()
                .headers(headers)
                .pathParam("id", id)
                .body(requestPayload);
    }
}
