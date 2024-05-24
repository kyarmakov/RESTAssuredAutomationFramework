package constants;

import lombok.Getter;

public final class FrameworkConstants {
    private FrameworkConstants() {}

    private @Getter static final String baseURI = "http://localhost:8989";
    private @Getter static final String usersEndpoint = "/users";
    private @Getter static final String testDataPath = System.getProperty("user.dir") + "/src/test/resources/testdata/users";
}
