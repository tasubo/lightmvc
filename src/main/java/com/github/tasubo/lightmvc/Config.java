package com.github.tasubo.lightmvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

    private static final String CONFIG_PATH = "/config.properties";
    private final static Logger LOG = LoggerFactory.getLogger(Config.class);
    private ResourceBundle bundle;

    Config() {
        InputStream inputStream =
                Config.class.getResourceAsStream(CONFIG_PATH);
        try {
            bundle = new PropertyResourceBundle(inputStream);
        } catch (IOException ex) {
            LOG.error("Could not load properties", ex);
        }
    }

    public String get(String string) {
        return bundle.getString(string);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
