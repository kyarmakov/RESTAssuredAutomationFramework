package users;

import constants.FrameworkConstants;
import io.restassured.response.Response;
import requests.UserRequests;

import java.util.HashMap;
import java.util.Map;

public class UserApis {

    protected Response createUser(Object requestPayload) {
        String endpoint = FrameworkConstants.getBaseURI() + FrameworkConstants.getUsersEndpoint();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return UserRequests.performPost(endpoint, requestPayload, headers);
    }
}
