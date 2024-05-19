package ru.mezentss.ui.task4;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class TranslatePage extends BasePage {
    private static final Logger logger = Logger.getLogger(TranslatePage.class);

    @FindBy(xpath = "//*[@id='APjFqb']")
    private WebElement inputSearch;

    @FindBy(xpath = "//*[@id='tw-source-text-ta']")
    private WebElement inputText;

    @FindBy(xpath = "//span[@title='Обратный перевод']")
    private WebElement buttonChangeLanguages;

    @FindBy(xpath = "//*[@id='tw-target-text']")
    private WebElement outputText;

    @Step("Открытие главной страницы")
    public TranslatePage openPage(){
        driverManager.getDriver().get("https://www.google.com/");
        logger.info("Открытие главной страницы");
        return this;
    }

    @Step("Ввод запроса")
    public TranslatePage enterQuery(String query){
        waitUntilElementToBeVisible(inputSearch);
        inputSearch.click();
        inputSearch.sendKeys(query);
        inputSearch.sendKeys(Keys.ENTER);
        logger.info("Ввод запроса '" + query + "' в строку поиска");
        return this;
    }

    @Step("Нажатие на кнопку смены языков")
    public TranslatePage clickChangeLanguagesButton(){
        waitUntilElementToBeVisible(buttonChangeLanguages);
        buttonChangeLanguages.click();
        logger.info("Нажатие на кнопку смены языков");
        return this;
    }

    @Step("Ввод текста")
    public TranslatePage enterText(String text){
        waitUntilElementToBeVisible(inputText);
        inputText.click();
        inputText.sendKeys(text);
        logger.info("Ввод текста '" + text + "' в поле");
        return this;
    }

    @Step("Проверка перевода текста")
    public synchronized TranslatePage checkTranslation(String text) throws InterruptedException {
        wait(2000);
        waitUntilElementToBeVisible(outputText);
        System.out.println(outputText.getText());
        Assert.assertTrue("Перевод не соответствует", outputText.getText().contains(text));
        logger.info("Проверка того, что перевод верный");
        return this;
    }
}
