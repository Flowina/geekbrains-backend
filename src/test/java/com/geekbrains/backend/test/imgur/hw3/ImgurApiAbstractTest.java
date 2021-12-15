package com.geekbrains.backend.test.imgur.hw3;

import io.restassured.RestAssured;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.internal.AuthenticationSpecificationImpl;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;

import java.util.Properties;

public abstract class ImgurApiAbstractTest extends ImgurApiBase {

    protected static RequestSpecification requestSpecification;
    protected static String TOKEN;
    protected static String USER_NAME;

    static {
        try {
            Properties properties = readProperties();
            RestAssured.baseURI = properties.getProperty("imgur-api-url");
             TOKEN = properties.getProperty("imgur-api-token");
             USER_NAME= properties.getProperty("user-name");
            requestSpecification = new RequestSpecBuilder()
                    .setAuth(new OAuth2Scheme())
                    .build();
            AuthenticationSpecification auth = new AuthenticationSpecificationImpl(requestSpecification);
            requestSpecification = auth.oauth2(TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
