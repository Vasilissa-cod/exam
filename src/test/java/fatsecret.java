import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class fatsecret {

    //Для навигации в браузере
    private WebDriver driver;
    private WebDriverWait wait;

    //Настройка перед каждым тестом
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void emptyLoginTest() throws InterruptedException {
        //1. Открыть страницу https://foods.fatsecret.com/
        driver.get("https://foods.fatsecret.com/");
        Thread.sleep(2000);

        //2. Нажать Sign In
        WebElement signInLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();
        Thread.sleep(2000);

        //3. Очистить поля
        WebElement usernameField = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("ctl11_Logincontrol1_Name")));
        WebElement passwordField = driver.findElement(By.id("ctl11_Logincontrol1_Password"));

        usernameField.clear();
        passwordField.clear();

        //4. Нажать Sign In
        WebElement loginButton = driver.findElement(By.id("ctl11_Logincontrol1_Login"));
        loginButton.click();
        Thread.sleep(2000);

        //5. Проверить, что получено сообщение об ошибке
        boolean errorFound = false;

        //Проверяем alert
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Получен alert: " + alertText);
            alert.accept();
            errorFound = true;
        } catch (NoAlertPresentException e) {
            //Alert не найден, проверяем сообщение на странице
            try {
                WebElement errorMessage = driver.findElement(By.id("ctl11_Logincontrol1_RequiredFieldValidator1"));
                if (errorMessage.isDisplayed()) {
                    System.out.println("Получено сообщение об ошибке на странице");
                    errorFound = true;
                }
            } catch (Exception ex) {
                //Ищем любое сообщение об ошибке
                try {
                    WebElement anyError = driver.findElement(By.className("errorText"));
                    System.out.println("Найдена ошибка: " + anyError.getText());
                    errorFound = true;
                } catch (Exception ignored) {}
            }
        }

        if (errorFound) {
            System.out.println("Тест пройден: сообщение об ошибке получено!");
        } else {
            System.out.println("Тест не пройден: сообщение об ошибке не найдено");
        }
    }

    //Закрытие браузера после каждого теста
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
