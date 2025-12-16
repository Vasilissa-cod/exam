import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

public class FatsecretSelenide {

    @BeforeEach
    void setUp() {
        Configuration.timeout = 10000;
        Configuration.pageLoadStrategy = "eager";
    }

    /*
     * Тест: Проверка ошибки при пустых полях
     *
     * На странице https://foods.fatsecret.com/
     * 1. Нажать Sign In
     * 2. Очистить поля
     * 3. Нажать Sign In
     * 4. Проверить, что получено сообщение об ошибке
     */
    @Test
    public void test01EmptyLoginError() {
        open("https://foods.fatsecret.com/");

        $x("//a[text()='Sign In']").click();

        $(By.id("ctl11_Logincontrol1_Name")).clear();
        $(By.id("ctl11_Logincontrol1_Password")).clear();

        $(By.id("ctl11_Logincontrol1_Login")).click();

        //Проверяем alert или сообщение на странице
        boolean errorFound = false;

        try {
            String alertText = switchTo().alert().getText();
            System.out.println("Получен alert: " + alertText);
            switchTo().alert().accept();
            errorFound = true;
        } catch (NoAlertPresentException e) {
            if ($(By.id("ctl11_Logincontrol1_RequiredFieldValidator1")).isDisplayed()) {
                System.out.println("Получено сообщение об ошибке на странице");
                errorFound = true;
            }
        }

        if (errorFound) {
            System.out.println("Тест пройден: сообщение об ошибке получено!");
        } else {
            System.out.println("Тест не пройден");
        }
    }
}
