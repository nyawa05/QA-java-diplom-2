package services;

import io.restassured.response.Response;
import pojo.UserAuth;
import pojo.UserAuthCredentials;
import pojo.UserCredentials;

import static io.restassured.RestAssured.given;
import static services.Constants.*;

public class UserRequests {
    public Response createUser(UserCredentials user) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .and()
                .body(user)
                .when()
                .post(API_CREATE_USER);
    }

    public void deleteUser(UserCredentials user) {
        UserAuth userAuth = authUser(user)
                .getBody()
                .as(UserAuth.class);
        given()
                .spec(REQUEST_SPECIFICATION)
                .auth().oauth2(userAuth.getAccessToken())
                .delete(API_EDIT_USER);

    }

    public Response authUser(UserCredentials user) {
        UserAuthCredentials userAuth = new UserAuthCredentials(user.getEmail(), user.getPassword());
        return given()
                .spec(REQUEST_SPECIFICATION)
                .and()
                .body(userAuth)
                .when()
                .post(API_LOGIN_USER);
    }

    public Response editUser(String token, String json) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .auth().oauth2(token)
                .and()
                .body(json)
                .when()
                .patch(API_EDIT_USER);
    }
}
