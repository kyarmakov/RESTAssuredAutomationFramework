package users;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import reporting.Setup;
import utils.DataProvidersUtils;
import utils.RandomUtils;

import java.util.Map;
import java.util.Objects;

public final class UserTests extends BaseTest {
    private UserTests() {}

    @Test(priority = 1, dataProvider = "createUserProvider", dataProviderClass = DataProvidersUtils.class)
    void createUserTest(Map<String, Object> userTest, ITestContext context) {
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
    void getUserByIdTest(Map<String, Object> userTest, ITestContext context) throws JsonProcessingException {
        Map<String, Object> userMap = (Map<String, Object>) context.getAttribute("userMap");
        Response response;
        String errorMessage = null;

        if (userTest.get("testName").equals("GetUser_GetExistingUser"))
            response = UserApis.getUserById(userMap.get("id"));
        else {
            response = UserApis.getUserById(userTest.get("id"));
            errorMessage = ((String) userTest.get("expectedErrorMessage")).replace("{id}", String.valueOf(userTest.get("id")));
        }

        Assert.assertEquals(response.statusCode(), userTest.get("expectedStatusCode"));
        Assert.assertEquals(response.jsonPath().get("message"), errorMessage);

        if (response.statusCode() == 200) {
            Map<String, Object> responseMap = new ObjectMapper().readValue(response.getBody().asString(), new TypeReference<>() {});
            Assert.assertEquals(responseMap, userMap);
        }
    }

    @Test(priority = 3, dataProvider = "updateUserByIdProvider", dataProviderClass = DataProvidersUtils.class)
    void updateUserByIdTest(Map<String, Object> userTest, ITestContext context) throws JsonProcessingException {
        Map<String, Object> userMap = (Map<String, Object>) context.getAttribute("userMap");
        Response response;
        String errorMessage = (String) userTest.get("expectedErrorMessage");

        if (userTest.get("testName").equals("UpdateUser_NonexistentUser") ||
                userTest.get("testName").equals("UpdateUser_IdAsStr")) {
            response = UserApis.updateUserById(userTest.get("id"), getBody(userTest.get("body")));
            errorMessage = ((String) userTest.get("expectedErrorMessage")).replace("{id}", String.valueOf(userTest.get("id")));
        }
        else
            response = UserApis.updateUserById(userMap.get("id"), getBody(userTest.get("body")));

        Assert.assertEquals(response.statusCode(), userTest.get("expectedStatusCode"));
        Assert.assertEquals(response.jsonPath().get("message"), errorMessage);

        if (response.statusCode() == 200) {
            Map<String, Object> updatedUser = (Map<String, Object>) getBody(userTest.get("body"));
            Map<String, Object> responseMap = new ObjectMapper().readValue(response.getBody().asString(), new TypeReference<>() {});
            responseMap.remove("id");
            if (userTest.get("testName").equals("UpdateUser_WithoutMarried")) responseMap.remove("married");
            Assert.assertEquals(responseMap, updatedUser);
        }
    }

    @Test(priority = 4, dataProvider = "deleteUserByIdProvider", dataProviderClass = DataProvidersUtils.class)
    void deleteUserByIdTest(Map<String, Object> userTest, ITestContext context) {
        Map<String, Object> userMap = (Map<String, Object>) context.getAttribute("userMap");
        Response response;
        String errorMessage = null;

        if (userTest.get("testName").equals("DeleteUser_DeleteExistingUser"))
            response = UserApis.deleteUserById(userMap.get("id"));
        else {
            response = UserApis.deleteUserById(userTest.get("id"));
            errorMessage = ((String) userTest.get("expectedErrorMessage")).replace("{id}", String.valueOf(userTest.get("id")));
        }

        Assert.assertEquals(response.statusCode(), userTest.get("expectedStatusCode"));

        if (response.statusCode() != 204)
            Assert.assertEquals(response.jsonPath().get("message"), errorMessage);
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

            if (!body.isEmpty() && Objects.nonNull(body.get("email")) && body.get("email").equals("RANDOM_EMAIL"))
                body.put("email", RandomUtils.getRandomEmail());

            return body;
        }
    }
}
