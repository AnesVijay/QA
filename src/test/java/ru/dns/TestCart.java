package ru.dns;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestCart {
    public ChromeDriver ch_driver;

    @Test
    public void testAddToCart() throws InterruptedException {
        //ch_driver = new ChromeDriver();

        ch_driver.get("https://www.dns-shop.ru/");

        // вводим "ноутбук игровой" в строку поиска
        ch_driver.findElement(By.xpath("(//input[@name='q'])[2]"))
                .sendKeys("ноутбук игровой");

        // проводим поиск нужного товара
        ch_driver.findElement(By.cssSelector(".ui-input-search__buttons:nth-child(3) > .ui-input-search__icon_search"))
                .click();

        // заходим в карточку первого товара в выдаче
        ch_driver.findElement(By.cssSelector(".catalog-product:nth-child(1) > .catalog-product__name > span")).click();

        // ждём 5 секунд, пока появится кнопка "Купить"
        WebDriverWait wait = new WebDriverWait(ch_driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".buy-btn:nth-child(3)")));

        //  добавляем товар в корзину
        ch_driver.findElement(By.cssSelector(".buy-btn:nth-child(3)"))
                .click();

        System.out.println("1");

        // проверяем наличие добавленного товара
        WebDriverWait new_wait = new WebDriverWait(ch_driver, Duration.ofSeconds(5));
        new_wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//nav[@id='header-search']/div/div/div[2]/a[3]/span")));
        WebElement element = ch_driver.findElement(By.xpath("//nav[@id='header-search']/div/div/div[2]/a[3]/span"));
        String count = element.getText();
        String msg = String.format("Получили %s, Ожидалось %s", count, "1");
        Assert.assertTrue(msg, count.equals("1"));
    }

    @Test
     public void testDeleteGoodInCart(){
        //ch_driver = new ChromeDriver();

        ch_driver.get("https://www.dns-shop.ru/");

        // вводим "ноутбук игровой" в строку поиска
        ch_driver.findElement(By.xpath("(//input[@name='q'])[2]"))
                .sendKeys("ноутбук игровой");

        // проводим поиск нужного товара
        ch_driver.findElement(By.cssSelector(".ui-input-search__buttons:nth-child(3) > .ui-input-search__icon_search"))
                .click();

        // заходим в карточку первого товара в выдаче
        ch_driver.findElement(By.cssSelector(".catalog-product:nth-child(1) > .catalog-product__name > span")).click();

        // ждём 5 секунд, пока появится кнопка "Купить"
        WebDriverWait wait = new WebDriverWait(ch_driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".buy-btn:nth-child(3)")));

        //  добавляем товар в корзину
        ch_driver.findElement(By.cssSelector(".buy-btn:nth-child(3)"))
                .click();

        // ждём, пока товар прогрузится в корзине
        WebDriverWait new_wait = new WebDriverWait(ch_driver, Duration.ofSeconds(5));
        new_wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//nav[@id='header-search']/div/div/div[2]/a[3]/span")));
        // и переходим в корзину
        ch_driver.get("https://www.dns-shop.ru/cart/");

        // нажимаем на кнопку "Удалить"
        ch_driver.findElement(By.cssSelector(".remove-button__title")).click();

        //проверка удаления
        //new_wait = new WebDriverWait(ch_driver, Duration.ofSeconds(5));
        new_wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-message__title-empty-cart")));
        WebElement el = ch_driver.findElement(By.cssSelector(".empty-message__title-empty-cart"));
        String isEmplty = el.getText();
        Assert.assertTrue(isEmplty.equals("Корзина пуста"));

    }

    @After
    public void CloseChrome(){
        ch_driver.quit();
    }
    @Before
    public void Start(){ch_driver = new ChromeDriver();}

}
