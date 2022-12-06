package services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Constants {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String API_CREATE_USER = "/api/auth/register";
    public static final String API_EDIT_USER = "/api/auth/user";
    public static final String API_LOGIN_USER = "/api/auth/login";
    public static final String API_GET_INGREDIENTS = "/api/ingredients";
    public static final String API_ORDERS = "/api/orders";

    public static RequestSpecification REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .addHeader("Content-type", "application/json")
            .setBaseUri(BASE_URL)
            .build();
}
