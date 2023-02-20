package com.epam.rd.autotasks.suffixing;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    private static Method mainMethod;
    private static Logger appLogger;

    private ByteArrayOutputStream logSink;
    private StreamHandler appLogHandler;

    @BeforeAll
    static void setup() throws IOException, ClassNotFoundException, NoSuchMethodException {
        JarInputStream jarStream = new JarInputStream(new FileInputStream("src/main/resources/suffixing.jar"));
        Manifest mf = jarStream.getManifest();
        String mainClassName = mf.getMainAttributes().getValue("Main-Class");
        Class<?> mainClass = Class.forName(mainClassName);
        mainMethod = mainClass.getMethod("main", String[].class);

        appLogger = LogManager.getLogManager().getLogger("");
        appLogger.setLevel(Level.ALL);

        FileUtils.deleteDirectory(new File("src/test/resources/sandbox/config"));
        FileUtils.deleteDirectory(new File("src/test/resources/sandbox/cases"));

        FileUtils.copyDirectory(
                new File("src/test/resources/reference/config"),
                new File("src/test/resources/sandbox/config"));
        FileUtils.copyDirectory(
                new File("src/test/resources/reference/cases"),
                new File("src/test/resources/sandbox/cases"));

    }

    @BeforeEach
    void setupLoggerHandler() {
        logSink = new ByteArrayOutputStream();
        appLogHandler = new StreamHandler(logSink, new SimpleFormatter());
        appLogger.addHandler(appLogHandler);
    }

    @Test
    void testAbsentFile() throws InvocationTargetException, IllegalAccessException {
        final String output = runApp("src/test/resources/sandbox/config/absent-file-config.properties");
        assertThat(output, containsString("SEVERE: No such file: src/test/resources/sandbox/cases/no-such-file.txt"));
    }

    @Test
    void testBadMode1() throws InvocationTargetException, IllegalAccessException {
        final String output = runApp("src/test/resources/sandbox/config/bad-mode-config-1.properties");
        assertThat(output, containsString("SEVERE: Mode is not recognized: CPY"));
    }

    @Test
    void testBadMode2() throws InvocationTargetException, IllegalAccessException {
        final String output = runApp("src/test/resources/sandbox/config/bad-mode-config-2.properties");
        assertThat(output, containsString("SEVERE: Mode is not recognized: DELETE"));
    }

    @Test
    void testCopy() throws InvocationTargetException, IllegalAccessException, IOException {
        final String output = runApp("src/test/resources/sandbox/config/copy-config.properties");
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/copy/copy.txt",
                "src/test/resources/sandbox/cases/copy/copy.copy.txt");
    }

    @Test
    void testLowercaseMode() throws InvocationTargetException, IllegalAccessException, IOException {
        final String output = runApp("src/test/resources/sandbox/config/lowercase-mode-config.properties");
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/lowercase-mode/lower.txt",
                "src/test/resources/sandbox/cases/lowercase-mode/lower-old.txt");
    }

    @Test
    void testMixcaseMode() throws InvocationTargetException, IllegalAccessException {
        final String output = runApp("src/test/resources/sandbox/config/mixcase-mode-config.properties");
        assertFileIsMoved(output,
                "src/test/resources/sandbox/cases/mixcase-mode/to-move.txt",
                "src/test/resources/sandbox/cases/mixcase-mode/to-move.mv.txt");
    }

    @Test
    void testMove() throws InvocationTargetException, IllegalAccessException {
        final String output = runApp("src/test/resources/sandbox/config/move-config.properties");
        assertFileIsMoved(output,
                "src/test/resources/sandbox/cases/move/origin.txt",
                "src/test/resources/sandbox/cases/move/origin.moved.txt");
    }

    @Test
    void testNoSuffixMove() throws InvocationTargetException, IllegalAccessException {
        final String output = runApp("src/test/resources/sandbox/config/no-suffix-config.properties");
        assertThat(output, containsString("SEVERE: No suffix is configured"));
    }

    @Test
    void testEmptyFiles() throws InvocationTargetException, IllegalAccessException {
        final String configFile = "src/test/resources/sandbox/config/empty-files-config.properties";
        final String output = runApp(configFile);
        assertThat(output, containsString("WARNING: No files are configured to be copied/moved"));
    }

    @Test
    void testThreeFiles() throws InvocationTargetException, IllegalAccessException, IOException {
        final String configFile = "src/test/resources/sandbox/config/three-files-config.properties";
        final String output = runApp(configFile);
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/three-files/1.txt",
                "src/test/resources/sandbox/cases/three-files/1.old.txt");
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/three-files/2.txt",
                "src/test/resources/sandbox/cases/three-files/2.old.txt");
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/three-files/3.txt",
                "src/test/resources/sandbox/cases/three-files/3.old.txt");
    }

    @Test
    void testThreeFilesMiddleAbsent() throws InvocationTargetException, IllegalAccessException, IOException {
        final String configFile = "src/test/resources/sandbox/config/three-files-middle-absent-config.properties";
        final String output = runApp(configFile);
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/three-files-middle-absent/1.txt",
                "src/test/resources/sandbox/cases/three-files-middle-absent/1.old.txt");
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/three-files-middle-absent/3.txt",
                "src/test/resources/sandbox/cases/three-files-middle-absent/3.old.txt");
        assertThat(output, containsString("SEVERE: No such file: src/test/resources/sandbox/cases/three-files-middle-absent/2.txt"));
    }

    @Test
    void testTwoFiles() throws InvocationTargetException, IllegalAccessException, IOException {
        final String configFile = "src/test/resources/sandbox/config/two-files-config.properties";
        final String output = runApp(configFile);
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/two-files/1.txt",
                "src/test/resources/sandbox/cases/two-files/1.old.txt");
        assertFileIsCopied(output,
                "src/test/resources/sandbox/cases/two-files/2.txt",
                "src/test/resources/sandbox/cases/two-files/2.old.txt");
    }

    @Test
    void testNoFiles() throws InvocationTargetException, IllegalAccessException {
        final String configFile = "src/test/resources/sandbox/config/no-files-config.properties";
        final String output = runApp(configFile);
        assertThat(output, containsString("WARNING: No files are configured to be copied/moved"));
    }

    private void assertFileIsCopied(final String output, final String sourceFile, final String copiedFile) throws IOException {
        final Path sourcePath = Paths.get(sourceFile);
        final Path copiedPath = Paths.get(copiedFile);

        assertThat(output, containsString("INFO: " + sourceFile + " -> " + copiedFile));
        assertTrue(Files.exists(sourcePath), "File " + sourceFile + "must exist");
        assertTrue(Files.exists(copiedPath), "File " + copiedFile + "must exist");

        assertEquals(Files.readString(sourcePath), Files.readString(copiedPath));
    }

    private void assertFileIsMoved(final String output, final String sourceFile, final String copiedFile) {
        assertThat(output, containsString("INFO: " + sourceFile + " => " + copiedFile));
        assertFalse(Files.exists(Paths.get(sourceFile)), "File " + sourceFile + "must not exist");
        assertTrue(Files.exists(Paths.get(copiedFile)), "File " + copiedFile + "must exist");
    }

    private String runApp(final String configFilePath) throws IllegalAccessException, InvocationTargetException {
        mainMethod.invoke(null, (Object) new String[]{configFilePath});
        appLogHandler.flush();
        return logSink.toString();
    }
}
