package users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import utils.DataProvidersUtils;

import java.util.HashMap;
import java.util.Map;

public final class UserTests {
    private UserTests() {}

    @Test(priority = 1, dataProvider = "createUserProvider", dataProviderClass = DataProvidersUtils.class)
    private void createUserTest(Map<String, Object> userTest, ITestContext context) {

        Response response = UserApis.createUser(getBody(userTest.get("body")));

        Assert.assertEquals(response.statusCode(), userTest.get("expectedStatusCode"));
        Assert.assertEquals(response.jsonPath().get("message"), userTest.get("expectedErrorMessage"));

        if (userTest.get("testName").equals("CreateUser_ValidRequest")) {
            Map<String, Object> userMap = (Map<String, Object>) getBody(userTest.get("body"));
            userMap.put("id", response.jsonPath().get("id"));
            context.setAttribute("userMap", userMap);
        }
    }

    @Test(priority = 2, dataProvider = "getUserByIdProvider", dataProviderClass = DataProvidersUtils.class)
    private void getUserByIdTest(Map<String, Object> userTest, ITestContext context) throws JsonProcessingException {
        Map<String, Object> userMap = (Map<String, Object>) context.getAttribute("userMap");

        Response response = UserApis.getUserById(userMap.get("id"));

        Assert.assertEquals(response.statusCode(), userTest.get("expectedStatusCode"));
        Assert.assertEquals(response.jsonPath().get("message"), userTest.get("expectedErrorMessage"));

        if (response.statusCode() == 200) {
            Map<String, Object> responseMap = new ObjectMapper().readValue(response.getBody().asString(), new TypeReference<>() {});
            Assert.assertEquals(responseMap, userMap);
        }
    }

    @Test(priority = 3, dataProvider = "updateUserByIdProvider", dataProviderClass = DataProvidersUtils.class)
    private void updateUserByIdTest(Map<String, Object> userTest, ITestContext context) throws JsonProcessingException {
        Map<String, Object> userMap = (Map<String, Object>) context.getAttribute("userMap");

        Response response = UserApis.updateUserById(userMap.get("id"), getBody(userTest.get("body")));

        Assert.assertEquals(response.statusCode(), userTest.get("expectedStatusCode"));
        Assert.assertEquals(response.jsonPath().get("message"), userTest.get("expectedErrorMessage"));

        if (response.statusCode() == 200) {
            Map<String, Object> updatedUser = (Map<String, Object>) getBody(userTest.get("body"));
            Map<String, Object> responseMap = new ObjectMapper().readValue(response.getBody().asString(), new TypeReference<>() {});
            responseMap.remove("id");
            Assert.assertEquals(responseMap, updatedUser);
        }
    }

    private Object getBody(Object bodyValue) {
        if (!bodyValue.getClass().getName().equals("java.util.LinkedHashMap")) {
            Object body = new ObjectMapper()
                    .convertValue(bodyValue, new TypeReference<>() {
                    });

            return body;
        } else {
            Map<String, Object> body = new ObjectMapper()
                    .convertValue(bodyValue, new TypeReference<>() {
                    });

            return body;
        }
    }
}
