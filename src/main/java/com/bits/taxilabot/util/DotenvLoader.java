import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DotenvLoader {
    public static void loadEnv() {
        try (FileInputStream fis = new FileInputStream(".env")) {
            Properties props = new Properties();
            props.load(fis);
            for (String key : props.stringPropertyNames()) {
                if (System.getenv(key) == null) {
                    System.setProperty(key, props.getProperty(key));
                }
            }
        } catch (IOException e) {
            // .env file is optional; ignore if not found
        }
    }
}
