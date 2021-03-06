package info.loenwind.autoconfig.util;

import java.io.File;

public class Configuration {

    //TODO Dummy Config handler. Needs to be replaced with actual storage system

    public Configuration(File configFile) {

    }

    public boolean hasChanged() {
        return false;
    }


    public void save() {

    }

    public ConfigProperty get(String section, String keyname, String defaultValue) {
        return new ConfigProperty("", "", ConfigProperty.Type.STRING);
    }

    public ConfigProperty get(String section, String keyname, String[] defaultValue) {
        return new ConfigProperty("", "", ConfigProperty.Type.STRING);
    }

    public ConfigProperty get(String section, String keyname, Boolean defaultValue) {
        return new ConfigProperty("", "", ConfigProperty.Type.BOOLEAN);
    }

    public ConfigProperty get(String section, String keyname, Double defaultValue, String comment) {
        return new ConfigProperty("", "", ConfigProperty.Type.DOUBLE);
    }

    public ConfigProperty get(String section, String keyname, double defaultValue) {
        return new ConfigProperty("", "", ConfigProperty.Type.DOUBLE);
    }

    public ConfigProperty get(String section, String keyname, int[] defaultValue) {
        return new ConfigProperty("", "", ConfigProperty.Type.INTEGER);
    }
}
