import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.UserCredentials;
import services.UserGenerator;
import services.UserRequests;

public class CreateUserTest {
    UserCredentials userCredentials = UserGenerator.randomUser();
    UserRequests userRequests = new UserRequests();

    @Test
    @DisplayName("Negative check of creating user")
    @Description("Check correct status code (403) and error message when create new user with existing email")
    public void createUserNegativeTestDoubleValues() throws InterruptedException {
        Thread.sleep(500);
        userRequests.createUser(userCredentials)
                .then().statusCode(200);
        Thread.sleep(500);
        String actual = userRequests.createUser(userCredentials)
                .then().statusCode(403).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message","User already exists", actual);;
    }

    @Test
    @DisplayName("Positive check of creating new user")
    @Description("Check correct status code (200) and successful message when create new user. Positive test.")
    public void createUserPositiveTest() throws InterruptedException {
        Thread.sleep(500);
        String actual = userRequests.createUser(userCredentials)
                .then().statusCode(200).extract().path("success").toString();
        Assert.assertEquals("Incorrect successful message","true", actual);
    }

    @After
    public void deleteData () throws InterruptedException {
        Thread.sleep(500);
        userRequests.deleteUser(userCredentials);
    }
}
