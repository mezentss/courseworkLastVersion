package ru.mezentss.ui.task4;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import ru.mezentss.ui.task4.BasePage;

import java.util.List;

public class StartPage extends BasePage {

    private static final Logger logger = Logger.getLogger(StartPage.class);

    @FindBy(xpath = "//*[@id='APjFqb']")
    private WebElement inputSearch;

    @FindBy(xpath = "//h3")
    private List<WebElement> results;

    @FindBy(xpath = "/html/body/div[4]/div/div[5]/div/div[1]/div/div/div[1]/div/div/div/div[1]/div/div[4]/a/div/span")
    private WebElement newsTab;

    @FindBy(xpath = "//div[@data-hveid='CAoQAA']")
    private List<WebElement> news;

    @Step("Переход на главную страницу")
    public StartPage openPage(){
        driverManager.getDriver().get("https://www.google.com/");
        logger.info("Открытие главной страницы Google");
        return this;
    }

    @Step("Ввод запроса")
    public StartPage enterQuery(String query){
        waitUntilElementToBeVisible(inputSearch);
        inputSearch.click();
        inputSearch.sendKeys(query);
        logger.info("Ввод запроса '" + query + "' в строку поиска");
        return this;
    }

    @Step("Нажатие на кнопку поиска")
    public StartPage clickSearchButton(){
        inputSearch.sendKeys(Keys.ENTER);
        logger.info("Нажатие на кнопку поиска");
        return this;
    }

    @Step("Проверка выведенных результатов")
    public StartPage checkResults(){
        Assert.assertTrue("Запросов нет ",
                results.size() > 0);
        logger.info("Проверка открытия страницы");
        return this;
    }

    @Step("Переход во вкладку 'Новости'")
    public StartPage openNews(){
        newsTab.click();
        logger.info("Переход на вкладку 'Новости'");
        return this;
    }

    @Step("Проверка наличия новостей")
    public StartPage checkNews(){
        waitUntilElementToBeVisible(inputSearch);
        Assert.assertTrue("Нет новостей, соответствующих запросу", news.size() > 0);
        logger.info("Проверка того, что есть хотя бы одно новостное сообщение");
        return this;
    }
}