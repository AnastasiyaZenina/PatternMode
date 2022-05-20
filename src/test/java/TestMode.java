
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestMode {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void userNotRegistered() {
        var userNotRegistered = DataGenerator.getUser("active");
        $("[data-test-id='login'] input").setValue(userNotRegistered.getLogin());
        $("[data-test-id='password'] input").setValue(userNotRegistered.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    void userRegistered() {
        var registeredUser =DataGenerator. getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(withText("Продолжить")).click();
        $(".heading").shouldHave(Condition.text(" Личный кабинет"));
    }
    @Test
    void shouldInvalidPassword() {
        var registeredUser =DataGenerator.getRegisteredUser("active");
        var invalidPassword =DataGenerator. generatePassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(invalidPassword);
        $(withText("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    void shouldInvalidLogin() {
        var registeredUser =DataGenerator.getRegisteredUser("active");
        var invalidLogin =DataGenerator.generateLogin();
        $("[data-test-id='login'] input").setValue(invalidLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(withText("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
    @Test
    void userBlocked() {
        var userBlocked = DataGenerator.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(userBlocked.getLogin());
        $("[data-test-id='password'] input").setValue(userBlocked.getPassword());
        $(withText("Продолжить")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Пользователь заблокирован"));
    }
}
