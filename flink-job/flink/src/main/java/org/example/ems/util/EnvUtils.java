package org.example.ems.util;

public class EnvUtils {

    public static final String POSTGRES_HOST =
        env("POSTGRES_HOST", "postgres");

    public static final String POSTGRES_PORT =
        env("POSTGRES_PORT", "5432");

    public static final String POSTGRES_DB =
        env("POSTGRES_DB", "ems");

    public static final String POSTGRES_USERNAME =
        env("POSTGRES_USERNAME", "ems_user");

    public static final String POSTGRES_PASSWORD =
        env("POSTGRES_PASSWORD", "ems_pass");

    public static final String POSTGRES_URL =
        "jdbc:postgresql://" + POSTGRES_HOST + ":" + POSTGRES_PORT + "/" + POSTGRES_DB;

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
