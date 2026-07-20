package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Reads JSON fixtures out of src/test/resources/TestData and maps them
 * into POJOs, keeping test data out of the test classes themselves.
 */
public final class JsonDataReader {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String TEST_DATA_DIR = "TestData/";

    private JsonDataReader() {
    }

    public static <T> T read(String fileName, Class<T> type) {
        String resourcePath = TEST_DATA_DIR + fileName;
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("Could not find test data file on classpath: " + resourcePath);
            }
            return MAPPER.readValue(input, type);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse test data file: " + resourcePath, e);
        }
    }
}
