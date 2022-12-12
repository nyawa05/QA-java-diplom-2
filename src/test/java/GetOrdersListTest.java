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

public class GetOrdersListTest {
    UserCredentials userCredentials = UserGenerator.randomUser();
    UserRequests userRequests = new UserRequests();
    OrderRequests orderRequests = new OrderRequests();
    OrderGenerator orderGenerator = new OrderGenerator();

    @Test
    @DisplayName("Positive check of get user's orders list")
    @Description("Check correct status code (200) and correct count of orders for user. Positive test.")
    public void getOrdersListPositiveTest() throws InterruptedException {
        Thread.sleep(500);
        UserAuth userAuth = userRequests.createUser(userCredentials)
                .getBody().as(UserAuth.class);
        int expected = orderGenerator.createRandomOrders(userAuth.getAccessToken());
        Thread.sleep(500);
        String[] actual = orderRequests.getOrdersList(userAuth.getAccessToken())
                        .then().statusCode(200).extract().path("orders._id").toString().split(",");
        Assert.assertEquals("Orders count should equals to the generated orders for user", expected, actual.length);
    }

    @Test
    @DisplayName("Positive check of get user's orders list")
    @Description("Check correct status code (200) and null count of orders for user. Positive test.")
    public void getOrdersListNullOrdersPositiveTest() throws InterruptedException {
        Thread.sleep(500);
        UserAuth userAuth = userRequests.createUser(userCredentials)
                .getBody().as(UserAuth.class);
        Thread.sleep(500);
        String actual = orderRequests.getOrdersList(userAuth.getAccessToken())
                .then().statusCode(200).extract().path("orders").toString();
        Assert.assertEquals("Orders count should equals null massive", "[]", actual);
    }

    @Test
    @DisplayName("Negative check of get user's orders list")
    @Description("Check correct status code (401) and correct error message when trying get orders list without authorization. Negative test.")
    public void getOrdersListNegativeTest() throws InterruptedException {
        Thread.sleep(500);
        String actual = orderRequests.getOrdersList("")
                .then().statusCode(401).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message", "You should be authorised", actual);
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
