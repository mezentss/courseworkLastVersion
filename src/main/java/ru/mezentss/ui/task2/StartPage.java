package ru.mezentss.ui.task2;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StartPage extends BasePage {
    private static final Logger logger = Logger.getLogger(StartPage.class);

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//button[@class='hamburger']")
    private WebElement hamburgerMenu;

    @FindBy(xpath = "//a[contains(@class, 'main-nav')]")
    private List<WebElement> menuItemList;

    @Step("Проверка заголовка")
    public StartPage checkOpenPage(){
        Assert.assertEquals("Заголовок не соответствует ",
                "Московский Политех",
                title.getText());
        logger.info("Проверка открытия страницы");
        return this;
    }

    @Step("Проверка нажатия кнопки меню")
    public StartPage clickOnHamburgerMenu() {
        waitUntilElementToBeClickable(hamburgerMenu).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Открытие меню");
        return this;
    }

    @Step("Проверка нажатия на пункт меню")
    public StartPage mouseOnMenuItem(String nameMenuItem){
        for (WebElement element: menuItemList) {
            if (element.getAttribute("title").equalsIgnoreCase(nameMenuItem)) {
                waitUntilElementToBeVisible(element);
                moveToElement(element);
                logger.info("Навести на пункт меню " + nameMenuItem);
                return this;
            }
        }
        Assert.fail("Ошибка");
        return this;
    }

    @Step("Нажатия по пункту меню '{nameMenuItem}'")
    public SchedulePage clickOnMenuItem(String nameMenuItem){
        for (WebElement element: menuItemList) {
            if (element.getAttribute("title").equalsIgnoreCase(nameMenuItem)) {
                waitUntilElementToBeClickable(element).click();
                logger.info("Переход на страницу с расписанием");
                return pageManager.getSchedulePage();
            }
        }
        Assert.fail("Ошибка");
        return pageManager.getSchedulePage();
    }
}

