package users;

import constants.FrameworkConstants;
import io.restassured.response.Response;
import requests.UserRequests;

import java.util.HashMap;
import java.util.Map;

public final class UserApis {
    private UserApis() {}

    static Response createUser(Object requestPayload) {
        String endpoint = FrameworkConstants.getBaseURI() + FrameworkConstants.getUsersEndpoint();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return UserRequests.performPost(endpoint, requestPayload, headers);
    }

    static Response getUserById(Object id) {
        String endpoint = FrameworkConstants.getBaseURI() + FrameworkConstants.getUsersEndpoint();

        return UserRequests.performGet(endpoint, id);
    }

    static Response updateUserById(Object id, Object userPayload) {
        String endpoint = FrameworkConstants.getBaseURI() + FrameworkConstants.getUsersEndpoint();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return UserRequests.performPut(endpoint, id, userPayload, headers);
    }
}
