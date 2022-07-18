package miku.utils;

import java.util.Objects;

public class ConfigUtils {
    public static boolean toBool(String in) {
        return !(Objects.equals(in, "0"));
    }
}
