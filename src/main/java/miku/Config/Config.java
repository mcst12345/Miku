package miku.Config;

import miku.utils.ConfigUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Config {
    protected String name;
    protected short type;
    protected boolean value_bool;
    protected Double value_int;

    protected Object Default;

    public Config(short type, String name, Object Default) {
        this.type = type;
        this.name = name;
        this.Default = Default;
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
                System.out.println("Writing default value for config " + name + ":" + Default);
                try (FileWriter writer = new FileWriter(ConfigFile)) {
                    writer.write((Integer) Objects.requireNonNull(Default));
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
                    System.out.println("The value of " + this.name + " is:" + this.value_bool);
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 1:
                try {
                    this.value_int = Double.parseDouble(line);
                    System.out.println("The value of " + this.name + " is:" + this.value_int);
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
}
