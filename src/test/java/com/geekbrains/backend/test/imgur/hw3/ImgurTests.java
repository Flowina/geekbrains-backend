package com.geekbrains.backend.test.imgur.hw3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ImgurTests extends ImgurApiAbstractTest {

    private static int IMAGES_COUNT = 0;
    private static String IMAGE_HASH;
    private static String ALBUM_HASH;


    @Test
    @Order(1)
    void getImageCount() {
        IMAGES_COUNT = given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(goodResponseSpecification())
                .body("success", is(true))
                .log()
                .all()
                .when()
                .get("account/" + USER_NAME + "/images/count")
                .body()
                .jsonPath()
                .getInt("data");
    }

    @Test
    @Order(2)
    void postImageTest() {
        JsonPath jp = given()
                .spec(requestSpecification)
                .multiPart("image", getFileResource("mops.jpg"))
                .log()
                .all()
                .expect()
                .body("data.type", is("image/jpeg"))
                .body("data.id", matchesPattern("(?i)^[a-z0-9]{7}$"))
                .body("data.deletehash", matchesPattern("(?i)^[a-z0-9]{15}$"))
                .log()
                .all()
                .when()
                .post("upload")
                .body()
                .jsonPath();
        IMAGE_HASH = jp.getString("data.id");
        String imageDeleteHash = jp.getString("data.deletehash");
        IMAGES_COUNT++;
    }

    @Test
    @Order(3)
    void deleteImageById() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(goodResponseSpecification())
                .log()
                .all()
                .when()
                .delete("image/" + IMAGE_HASH);
    }

    @Test
    @Order(4)
    void createAlbumTest() {
        String albumTitle = "Album";
        String albumDescription = "Description";

        JsonPath al = given()
                .spec(requestSpecification)
                .formParam("title", albumTitle)
                .formParam("description", albumDescription)
                .log()
                .all()
                .expect()
                .spec(goodResponseSpecification())
                .body("data.id", matchesPattern("(?i)^[a-z0-9]{7}$"))
                .body("data.deletehash", matchesPattern("(?i)^[a-z0-9]{15}$"))
                .log()
                .all()
                .when()
                .post("album")
                .body()
                .jsonPath();
        ALBUM_HASH = al.getString("data.id");
    }

    @Test
    @Order(5)
    void addImageToAlbumTest() {
        given()
                .spec(requestSpecification)
                .multiPart("ids[]", IMAGE_HASH)
                .log()
                .all()
                .expect()
                .spec(goodResponseSpecification())
                .log()
                .all()
                .when()
                .post("album/" + ALBUM_HASH + "/add");
    }

    @Test
    @Order(6)
    void deleteAlbumTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(goodResponseSpecification())
                .body("data", is(true))
                .log()
                .all()
                .when()
                .delete("album/" + ALBUM_HASH);
        IMAGES_COUNT--;

    }

    @Test
    @Order(7)
    void getAccountImageCount() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("success", is(true))
                .body("data", hasSize(IMAGES_COUNT))
                .log()
                .all()
                .when()
                .get("account/" + USER_NAME + "/images");

    }

    @Test
    @Order(8)
    void albumNotExistTest() {

        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(goodResponseSpecification())
                .body("data", is(not(contains(ALBUM_HASH))))
                .log()
                .all()
                .when()
                .get("account/" + USER_NAME + "/albums/ids/0");

    }

}
