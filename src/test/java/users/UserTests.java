package users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DataProvidersUtils;

import java.util.Map;

public class UserTests extends UserApis {
    @Test(dataProvider = "createUserProvider", dataProviderClass = DataProvidersUtils.class)
    void createUserTest(Map<String, Object> userPayload) {

        Response response = createUser(getBody(userPayload.get("body")));

        Assert.assertEquals(response.statusCode(), userPayload.get("expectedStatus"));
    }


    private Object getBody(Object bodyValue) {
        if (!bodyValue.getClass().getName().equals("java.util.LinkedHashMap")) {
            Object body = new ObjectMapper()
                    .convertValue(bodyValue, new TypeReference<>() {});

            return body;
        }
        else {
            Map<String, Object> body = new ObjectMapper()
                    .convertValue(bodyValue, new TypeReference<>() {});

            return body;
        }
    }
}
