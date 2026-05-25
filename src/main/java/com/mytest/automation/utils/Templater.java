package com.mytest.automation.utils;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Rajani
 */
public class Templater {
    private String content;
    Logs logger = Logs.getInstance();

    private Templater() {
    }

    public Templater(InputStream stream) {
        try {
            content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Templater(String string) {
        content = string;
    }

    public Templater replace(String name, String value) {
        content = content.replaceAll(Pattern.quote("${" + name + "}"), value);
        return this;
    }

    public Templater replace(Map<String, String> map) {
        for(Map.Entry<String, String> e:map.entrySet()) {
            replace(e.getKey(), e.getValue());
        }
        Logs.info("Replaced all specified values in payload \n" +this);
        return this;
    }

    public String toString() {
        return content;
    }
}
