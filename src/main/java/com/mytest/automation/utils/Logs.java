package com.mytest.automation.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Rajani
 */
public class Logs extends Properties {

    private static final Logger Log = LogManager.getLogger(Logs.class.getName());
    private static Logs instance = null;

    public static Logs getInstance() {
        if (instance == null)
            instance = new Logs();
        return instance;
    }

    private Logs() {
        try {
            this.load(new FileInputStream("src/main/resources/log4j2.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String message) {
        Log.info("[Info] " + message);
    }

    public static void warn(String message) {
        Log.warn("[Warn] " + message);
    }

    public static void error(String message) {
        Log.error("[Error] " + message);
    }

    public static void fatal(String message) {
        Log.fatal("[Fatal] " + message);
    }

    public static void debug(String message) {
        Log.debug("[Debug] " + message);
    }

}
