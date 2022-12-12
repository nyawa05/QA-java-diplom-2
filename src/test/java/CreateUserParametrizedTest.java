import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.UserCredentials;
import services.UserGenerator;
import services.UserRequests;

@RunWith(Parameterized.class)
public class CreateUserParametrizedTest {
    private final String email;
    private final String password;
    private final String name;

    UserCredentials user;
    UserRequests userRequests = new UserRequests();

    public CreateUserParametrizedTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                { null, UserGenerator.randomPassword(), UserGenerator.randomName()},
                {UserGenerator.randomEmail(), null, UserGenerator.randomName()},
                {UserGenerator.randomEmail(), UserGenerator.randomPassword(), null},
                {null, null, null},
        };
    }

    @Test
    @DisplayName("Negative check when creating new user")
    @Description("Check correct status code (403) and error message when create new user without one or more of required fields")
    public void createUserNegativeTestNotEnoughValues() throws InterruptedException {
        user = new UserCredentials(email, password, name);
        Thread.sleep(500);
        String actual = userRequests.createUser(user)
                .then().statusCode(403).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message","Email, password and name are required fields", actual);
    }

    @After
    public void deleteData () {
        try {
            Thread.sleep(500);
            userRequests.deleteUser(user);
        }
        catch (Exception e) {
        }
    }
}
