import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));

    public static void request( RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto(user.getLogin(),user.getPassword(), user.status))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
    public static String generateLogin() {
        String login = faker.name().username();
        return login;
    }
    public static String generatePassword() {
        String password = faker.internet().password();
        return password;
    }
    @NotNull
    public static RegistrationDto getUser(String status) {
        RegistrationDto user = new RegistrationDto(generateLogin(),generatePassword(), status);
        return user;
    }
    @NotNull
    public static RegistrationDto getRegisteredUser(String status) {
        RegistrationDto registeredUser = getUser(status);
        request(registeredUser);
        return registeredUser;
    }

}
