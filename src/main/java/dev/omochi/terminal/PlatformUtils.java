package dev.omochi.terminal;

public class PlatformUtils {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public  boolean isLinux() {
        return OS_NAME.startsWith("linux");
    }

    public boolean isMac() {
        return OS_NAME.startsWith("mac");
    }

    public boolean isWindows() {
        return OS_NAME.startsWith("windows");
    }

    public boolean isSunOS() {
        return OS_NAME.startsWith("sunos");
    }
}