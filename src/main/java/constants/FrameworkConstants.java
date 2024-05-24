package constants;

import lombok.Getter;

@Getter
public final class FrameworkConstants {
    private FrameworkConstants() {}

    private static final String baseURI = "http://localhost:8989";
    private static final String usersEndpoint = "/users";
    private static final String testDataPath = System.getProperty("user.dir") + "/src/test/resources/testdata";
}
