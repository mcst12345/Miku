package miku.Config;

import miku.utils.ConfigUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Config {
    protected String name;
    protected short type;
    protected boolean value_bool;
    protected int value_int;

    public Config(short type, String name) {
        this.type = type;
        this.name = name;
    }

    public void read_config() throws IOException {
        File ConfigFilePath = new File("miku_config/");
        File ConfigFile = new File("miku_config/" + name);
        String line;
        if (!ConfigFile.exists()) {
            System.out.println("Creating config file...");
            try {
                if (!ConfigFilePath.exists()) if (!ConfigFilePath.mkdir()) {
                    System.out.println("Error:Failed to create config folder.");
                }
                if (!ConfigFile.createNewFile()) {
                    System.out.println("Error:Failed to create config file.");
                }
                if (GetDefaultValue(name) == null) {
                    System.out.println("Error:Can't get default value for this config");
                    return;
                }
                System.out.println("Writing default value for config " + name + ":" + GetDefaultValue(name));
                try (FileWriter writer = new FileWriter(ConfigFile)) {
                    writer.write(Objects.requireNonNull(GetDefaultValue(name)));
                } catch (IOException e) {
                    System.out.println("Error:Failed to write default value.");
                }

            } catch (Exception ignored) {
            }
        }
        FileInputStream in = new FileInputStream(ConfigFile);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.US_ASCII);
        BufferedReader bufferedReader = new BufferedReader(reader);
        line = bufferedReader.readLine();
        switch (type) {
            case 0:
                try {
                    this.value_bool = ConfigUtils.toBool(line);
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 1:
                try {
                    this.value_int = Integer.parseInt(line);
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Error:Unknown config type.");
        }
    }

    public Object GetValue() {
        switch (type) {
            case 0:
                return value_bool;
            case 1:
                return value_int;
            default:
                return null;
        }
    }

    public static String GetDefaultValue(String name) {
        switch (name) {
            case "is_debug":
                return "0";
            case "":
            default:
                return null;
        }
    }
}
