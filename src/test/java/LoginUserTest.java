import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.UserAuth;
import pojo.UserCredentials;
import services.UserGenerator;
import services.UserRequests;

public class LoginUserTest {
    UserCredentials userCredentials = UserGenerator.randomUser();
    UserRequests userRequests = new UserRequests();

    @Test
    @DisplayName("Positive check of login existed user")
    @Description("Check correct status code (200) and not null response body when login existed user. Positive test.")
    public void loginUserPositiveTest() throws InterruptedException {
        Thread.sleep(500);
        userRequests.createUser(userCredentials);
        Thread.sleep(500);
        UserAuth userAuth = userRequests.authUser(userCredentials)
                .then().statusCode(200).extract().body().as(UserAuth.class);
        Assert.assertNotNull("Should be not null response body after successful login user", userAuth);
    }

    @Test
    @DisplayName("Negative check of login existed user")
    @Description("Check correct status code (401) and error message when login existed user with incorrect password. Negative test.")
    public void loginUserIncorrectPasswordTest() throws InterruptedException {
        Thread.sleep(500);
        userRequests.createUser(userCredentials);
        UserCredentials newUserCredentials = new UserCredentials(userCredentials.getEmail(), UserGenerator.randomPassword(), userCredentials.getName());
        Thread.sleep(500);
        String actual = userRequests.authUser(newUserCredentials)
                .then().statusCode(401).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message", "email or password are incorrect", actual);
    }

    @Test
    @DisplayName("Negative check of login not existed user")
    @Description("Check correct status code (401) and error message when login not existed user. Negative test.")
    public void loginUserIncorrectEmailTest() throws InterruptedException {
        Thread.sleep(500);
        userRequests.createUser(userCredentials);
        UserCredentials newUserCredentials = new UserCredentials(UserGenerator.randomEmail(), userCredentials.getPassword(), userCredentials.getName());
        Thread.sleep(500);
        String actual = userRequests.authUser(newUserCredentials)
                .then().statusCode(401).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message", "email or password are incorrect", actual);
    }

    @After
    public void deleteData () throws InterruptedException {
        Thread.sleep(500);
        userRequests.deleteUser(userCredentials);
    }
}
