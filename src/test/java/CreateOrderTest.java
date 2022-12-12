import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.UserAuth;
import pojo.UserCredentials;
import services.OrderGenerator;
import services.OrderRequests;
import services.UserGenerator;
import services.UserRequests;

public class CreateOrderTest {
    UserCredentials userCredentials = UserGenerator.randomUser();
    UserRequests userRequests = new UserRequests();
    OrderRequests orderRequests = new OrderRequests();

    @Test
    @DisplayName("Positive check of creating new order with auth")
    @Description("Check correct status code (200) and not null order owner email when creating new order with auth")
    public void createOrderWithAuth() throws InterruptedException {
        Thread.sleep(500);
        UserAuth userAuth = userRequests.createUser(userCredentials)
                .getBody().as(UserAuth.class);
        Thread.sleep(500);
        String actual = orderRequests.createOrder(userAuth.getAccessToken(), OrderGenerator.getRandomIngredientsList())
                .then().statusCode(200).extract().path("order.owner.email").toString();
        Assert.assertEquals("Order owner email should be not null and should equals the user credentials", userCredentials.getEmail().toLowerCase(), actual);;
    }

    @Test
    @DisplayName("Positive check of creating new order without auth")
    @Description("Check correct status code (200) and not null order number when creating new order without auth")
    public void createOrderWithoutAuth() throws InterruptedException {
        Thread.sleep(500);
        String actual = orderRequests.createOrder("",OrderGenerator.getRandomIngredientsList())
                .then().statusCode(200).extract().path("order.number").toString();
        Assert.assertNotNull("Order number should be not null", actual);;
    }

    @Test
    @DisplayName("Positive check of creating new order with all ingredients")
    @Description("Check correct status code (200) and not null order number when creating new order with all ingredients")
    public void createOrderWithAllIngredients() throws InterruptedException {
        Thread.sleep(500);
        String actual = orderRequests.createOrder("",OrderGenerator.getAllIngredientsList())
                .then().statusCode(200).extract().path("order.number").toString();
        Assert.assertNotNull("Order number should be not null", actual);;
    }

    @Test
    @DisplayName("Negative check of creating new order without ingredients")
    @Description("Check correct status code (400) and correct error message when creating new order without ingredients")
    public void createOrderWithoutIngredients() throws InterruptedException {
        Thread.sleep(500);
        String actual = orderRequests.createOrder("",OrderGenerator.getNullIngredientList())
                .then().statusCode(400).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message","Ingredient ids must be provided", actual);;
    }

    @Test
    @DisplayName("Negative check of creating new order with wrong ingredient's hashes")
    @Description("Check correct status code (500) when creating new order with wrong ingredient's hashes")
    public void createOrderWithWrongIngredients() throws InterruptedException {
        Thread.sleep(500);
        orderRequests.createOrder("",OrderGenerator.getWrongIngredientsList())
                .then().statusCode(500);
    }

    @After
    public void deleteData () throws InterruptedException {
        try {
            Thread.sleep(500);
            userRequests.deleteUser(userCredentials);
        }
        catch (Exception e) {
        }
    }
}
