package com.geekbrains.backend.test.imgur.hw3;

import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;



public class ImgurApiBase {
    public static Properties readProperties() throws Exception {
        Properties properties = new Properties();
        Files.readAllLines(Path.of(
                        ImgurApiBase.class.getResource("test.properties").toURI()))
                .stream()
                .forEach(str -> {
                    String[] props = str.split("=");
                    properties.setProperty(props[0].trim(), props[1].trim());

                });
        return properties;
    }

        public String getStringResource(String name) throws IOException {
            String dir = getClass().getSimpleName();
            byte[] bytes = getClass().getResourceAsStream(dir + "/" + name)
                    .readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }

        public File getFileResource(String name) {
            String dir = getClass().getSimpleName();
            return new File(getClass().getResource(dir + "/" + name).getFile());
        }
    }


