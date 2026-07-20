package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads config.properties plus the platform-specific properties file
 * (android.properties / ios.properties) into a single merged Properties
 * instance. Platform resolution order: -Dplatform system property,
 * then the "platform" key in config.properties, defaulting to "android".
 */
public final class ConfigReader {

    private static final String PROPERTIES_DIR = "Properties/";
    private static Properties properties;

    private ConfigReader() {
    }

    public static synchronized Properties get() {
        if (properties == null) {
            properties = load();
        }
        return properties;
    }

    public static String get(String key) {
        return get().getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return get().getProperty(key, defaultValue);
    }

    public static String resolvePlatform() {
        String platform = System.getProperty("platform");
        if (platform == null || platform.isBlank()) {
            platform = get().getProperty("platform", "android");
        }
        platform = platform.toLowerCase();
        if (!platform.equals("android") && !platform.equals("ios")) {
            throw new IllegalArgumentException("Unsupported platform \"" + platform + "\". Use \"android\" or \"ios\".");
        }
        return platform;
    }

    private static Properties load() {
        Properties merged = new Properties();
        loadInto(merged, PROPERTIES_DIR + "config.properties");

        String platform = System.getProperty("platform", merged.getProperty("platform", "android")).toLowerCase();
        loadInto(merged, PROPERTIES_DIR + platform + ".properties");

        return merged;
    }

    private static void loadInto(Properties target, String resourcePath) {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalStateException("Could not find properties file on classpath: " + resourcePath);
            }
            target.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load properties file: " + resourcePath, e);
        }
    }
}
