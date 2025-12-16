import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public  class simple {

    private static final String URL = "https://online.top-academy.ru";
    /**Метод setUp() выполняется ПЕРЕД КАЖДЫМ тестом.
     * Здесь мы настраиваем Selenide.
     *
     * @BeforeEach - аннотация JUnit, которая говорит:
     * "Выполни этот метод перед каждым тестом**/
    @BeforeEach
    void setUp() {
        Configuration.timeout = 10000;
        Configuration.pageLoadStrategy = "eager"; // Не ждать полной загрузки страницы
    }

    @Test
    @DisplayName("Тест 1: Проверка заголовка страницы")//Смысл теста: Убедиться, что страница загрузилась и имеет заголовок.
    Если <title> пустой — это может указывать на ошибку сервера или некорректную вёрстку.
    void testPageTitle() {
        open(URL);
        String pageTitle = title(); // Получает заголовок страницы (то, что в <title>)
        System.out.println("Заголовок страницы: " + pageTitle); //метод вывода текста
        assertFalse(pageTitle.isEmpty(), "Заголовок страницы не должен быть пустым");// Проверяет, что заголовок НЕ пустой
        System.out.println("✓ Тест 1 пройден: Заголовок страницы получен!");
    }

    @Test
    @DisplayName("Тест 2: Проверка загрузки страницы")//Смысл теста: Проверить, что страница физически загрузилась и отображается пользователю.
    Если <body> не появился за 10 секунд (настроено в Configuration.timeout) — тест упадёт.
    void testPageLoads() {
        open(URL);
        $("body").shouldBe(visible); // Ждёт, пока элемент <body> станет видимым
        System.out.println("✓ Тест 2 пройден: Страница загрузилась успешно!");
    }
}

//> Эти тесты выполняют базовую проверку доступности и работоспособности веб-сайта.
    Первый тест проверяет наличие заголовка страницы, второй — что страница вообще загружается. 
    Это минимальный набор smoke-тестов, который гарантирует, что сервер отвечает и отдаёт корректный HTML.
