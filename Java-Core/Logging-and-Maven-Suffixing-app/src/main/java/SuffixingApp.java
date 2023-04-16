import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuffixingApp {
    private static final Logger LOGGER = Logger.getLogger(SuffixingApp.class.getName());
    private static String mode;
    private static String suffix;
    private static String[] files;

    public static void main(String[] args) throws IOException {
        if(!loadProperties(args[0])) {
            return;
        }

        for(String path : files) {
            File file = new File(path);
            if(!file.exists()) {
                LOGGER.log(Level.SEVERE, "No such file: " + path);
            } else {
                path = path.replace(".", suffix + ".");
                doSuffixing(file, path);
            }
        }
    }

    private static boolean loadProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(path));

        mode = properties.getProperty("mode");
        if (!mode.equalsIgnoreCase("copy") && !mode.equalsIgnoreCase("move")) {
            LOGGER.log(Level.SEVERE, "Mode is not recognized: " + mode);
            return false;
        }
        suffix = properties.getProperty("suffix");
        if (suffix == null) {
            LOGGER.log(Level.SEVERE, "No suffix is configured");
            return false;
        }
        String filesString = properties.getProperty("files");
        if (filesString == null || filesString.equals("")) {
            LOGGER.log(Level.WARNING, "No files are configured to be copied/moved");
            return false;
        }
        files = filesString.split(":");

        return true;
    }

    private static void doSuffixing(File file, String path) throws IOException {
        if(mode.equalsIgnoreCase("copy")) {
            Files.copy(file.toPath(), Path.of(path));
            LOGGER.log(Level.INFO, file.getPath() + " -> " + path);
        } else {
            file.renameTo(new File(path));
            LOGGER.log(Level.INFO, file.getPath() + " => " + path);
        }
    }
}