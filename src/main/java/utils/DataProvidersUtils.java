package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FrameworkConstants;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DataProvidersUtils {
    private DataProvidersUtils() {}

    @DataProvider
    public static Iterator<LinkedHashMap<String, Object>> createUserProvider() {
        List<LinkedHashMap<String, Object>> list;

        try {
            list = new ObjectMapper()
                    .readValue(new File(FrameworkConstants.getTestDataPath() + "/createUser.json"), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list.iterator();
    }
}
