package ru.mezentss.ui.task4;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import ru.mezentss.ui.task4.BasePage;

import java.util.List;

public class SettingsPage extends BasePage {

    private static final Logger logger = Logger.getLogger(StartPage.class);

    @FindBy(xpath = "//div[@jsname='LgbsSe']")
    private WebElement settingsButton;

    @FindBy(xpath = "//a[contains(@href, 'preferences')]")
    private WebElement settingsLink;

    @FindBy(xpath = "//div[@class='sjVJQd WL9owf'][.//span[contains(@class, 'DET5Lc')]]")
    private WebElement searchSettings;

    @FindBy(xpath = "/html/body/div[2]/div[1]/div[2]/div[2]/div[1]/div/div/div/div/span[2]/span")
    private WebElement chooseLanguage;

    @FindBy(xpath = "//div[@class='Q1bVje'][.//span[@data-value='de']]")
    private WebElement language;

    @FindBy(xpath = "/html/body/div[6]/div/div[2]/span/div/g-menu/g-menu-item[1]/div/span")
    private WebElement submitLanguage;

    @Step("Переход на главную страницу")
    public SettingsPage openPage(){
        driverManager.getDriver().get("https://www.google.com/");
        logger.info("Открытие главной страницы Google");
        return this;
    }

    @Step("Открытие настроек")
    public SettingsPage openSettings(){
        waitUntilElementToBeVisible(settingsButton);
        settingsButton.click();
        settingsLink.click();
        logger.info("Открытие настроек");
        return this;
    }

    @Step("Переход к настройкам поиска")
    public SettingsPage openSearchSettings(){
        waitUntilElementToBeVisible(searchSettings);
        searchSettings.click();
        logger.info("Переход к настройкам поиска");
        return this;
    }

    @Step("Выбор языка")
    public SettingsPage chooseLanguage(){
        waitUntilElementToBeVisible(chooseLanguage);
        chooseLanguage.click();
        waitUntilElementToBeVisible(findLanguage("de"));
        findLanguage("de").click();
        logger.info("Выбор языка");
        return this;
    }

    @Step("Проверка выбора языка")
    public SettingsPage validateLanguage() {
        waitUntilElementToBeVisible(language);
        Assert.assertTrue(language.getAttribute("data-value").equals("de"));
        logger.info("Проверка выбора языка");
        return this;
    }
}