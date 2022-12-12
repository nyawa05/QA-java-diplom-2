package services;

import io.restassured.response.Response;
import pojo.IngredientsList;

import static io.restassured.RestAssured.given;
import static services.Constants.*;

public class OrderRequests {
    public static Response getIngredients() {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get(API_GET_INGREDIENTS);
    }

    public static Response createOrder(String token, IngredientsList ingredientsList) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .auth().oauth2(token)
                .and()
                .body(ingredientsList)
                .when()
                .post(API_ORDERS);
    }

    public static Response getOrdersList(String token) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .auth().oauth2(token)
                .get(API_ORDERS);
    }
}
