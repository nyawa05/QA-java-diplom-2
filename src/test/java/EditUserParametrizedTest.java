import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.UserAuth;
import pojo.UserAuthEdit;
import pojo.UserCredentials;
import services.UserGenerator;
import services.UserRequests;

@RunWith(Parameterized.class)
public class EditUserParametrizedTest {
    static UserCredentials userCredentials = UserGenerator.randomUser();
    UserCredentials newUserCredentials;
    static String randomEmail = UserGenerator.randomEmail();
    static String randomPassword = UserGenerator.randomPassword();
    static String randomName = UserGenerator.randomName();
    UserRequests userRequests = new UserRequests();
    private final String email;
    private final String password;
    private final String name;
    private final String json;

    public EditUserParametrizedTest(String email, String password, String name, String json) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.json = json;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                { randomEmail, userCredentials.getPassword(), userCredentials.getName(), String.format("{\"email\": \"%s\"}", randomEmail) },
                { userCredentials.getEmail(), randomPassword, userCredentials.getName(), String.format("{\"password\": \"%s\"}", randomPassword) },
                { userCredentials.getEmail(), userCredentials.getPassword(), randomName, String.format("{\"name\": \"%s\"}", randomName) },
                { randomEmail, randomPassword, randomName, String.format("{\"email\": \"%s\",\"password\": \"%s\",\"name\": \"%s\"}", randomEmail, randomPassword, randomName) }
        };
    }

    @Test
    @DisplayName("Positive check when edit user field with authorization")
    @Description("Check correct status code (200) and not null response body when edit user's field with authorization")
    public void editUserPositiveTest() throws InterruptedException {
        Thread.sleep(500);
        UserAuth userAuth = userRequests.createUser(userCredentials)
                .getBody().as(UserAuth.class);
        Thread.sleep(500);
        UserAuthEdit userAuthEdit = userRequests.editUser(userAuth.getAccessToken(),json)
                        .then().statusCode(200).extract().body().as(UserAuthEdit.class);
        Assert.assertNotNull("Should be not null response body after successful editing user's field", userAuthEdit);
        newUserCredentials = new UserCredentials(email,password,name);
    }

    @Test
    @DisplayName("Negative check when edit user field without authorization")
    @Description("Check correct status code (401) and correct error message when edit user's field without authorization")
    public void editUserNegativeTest() throws InterruptedException {
        Thread.sleep(500);
        userRequests.createUser(userCredentials);
        Thread.sleep(500);
        String actual = userRequests.editUser("",json)
                .then().statusCode(401).extract().path("message").toString();
        Assert.assertEquals("Incorrect error message", "You should be authorised", actual);
        newUserCredentials = new UserCredentials(userCredentials.getEmail(),userCredentials.getPassword(),userCredentials.getName());
    }

    @After
    public void deleteData () throws InterruptedException {
        try {
            Thread.sleep(500);
            userRequests.deleteUser(userCredentials);
        }
        catch (Exception e) {
            Thread.sleep(500);
            userRequests.deleteUser(newUserCredentials);
        }
    }
}
