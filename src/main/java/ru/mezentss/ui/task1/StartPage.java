package ru.mezentss.ui.task1;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.util.List;

public class StartPage extends BasePage{
    private static final Logger logger = Logger.getLogger(StartPage.class);

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement numberOfRemainingItems;

    @FindBy(xpath = "//input[@type='checkbox']")
    private List<WebElement> listOfCheckboxes;

    @FindBy(xpath = "//input[@type='text']")
    private WebElement inputAddElement;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement buttonAddElement;

    private int remaining = 5;
    private int total = 5;

    @Step("Проверка наличия текста")
    public StartPage checkText(){
        waitUntilElementToBeVisible(numberOfRemainingItems);
        String text = String.format("%s of %s remaining", remaining, total);
        Assert.assertEquals("Текст " + text + " не присутствует на странице", text, numberOfRemainingItems.getText().trim());
        logger.info("Проверка наличия текста '5 of 5 remaining'");
        return this;
    }

    @Step("Проверка счетчика оставшихся задач")
    public StartPage checkItem(String nameOfItem){
        for (WebElement item: listOfCheckboxes){
            WebElement checkbox = item.findElement(By.xpath("./..//span"));
            if (checkbox.getText().trim().equals(nameOfItem)) {
                Assert.assertEquals("Элемент списка " + nameOfItem + " зачеркнут","done-false", checkbox.getAttribute("class"));
                logger.info("Проверка того, что элемент списка '" + nameOfItem + "' не зачеркнут");
                return this;
            }
        }
        Assert.fail("Элемент '" + nameOfItem + "' не присутствует на странице");
        return this;
    }

    @Step("Проверка выбора и отметки задач как выполненных")
    public StartPage checkboxByName(String nameOfItem){
        for (WebElement checkbox: listOfCheckboxes){
            WebElement item = checkbox.findElement(By.xpath("./..//span"));
            if (item.getText().trim().equals(nameOfItem)) {
                checkbox.click();
                remaining -= 1;
                String text = String.format("%s of %s remaining", remaining, total);
                Assert.assertEquals("Элемент " + nameOfItem + " не зачеркнут","done-true", item.getAttribute("class"));
                Assert.assertEquals("Число оставшихся элементов не уменьшилось на 1", text, numberOfRemainingItems.getText());
                logger.info("Поставить галочку у элемента '" + nameOfItem + "'");
                return this;
            }
        }
        Assert.fail("Элемент '" + nameOfItem + "' не присутствует на странице");
        return this;
    }

    @Step("Добавление нового элемента")
    public StartPage addNewItem(String nameOfItem){
        inputAddElement.click();
        inputAddElement.sendKeys(nameOfItem);
        buttonAddElement.click();
        total += 1;
        remaining += 1;
        for (WebElement item: listOfCheckboxes){
            WebElement checkbox = item.findElement(By.xpath("./..//span"));
            if (checkbox.getText().trim().equals(nameOfItem)) {
                Assert.assertEquals("Элемент " + nameOfItem + " зачеркнут","done-false", checkbox.getAttribute("class"));
            }
        }
        String text = String.format("%s of %s remaining", remaining, total);
        Assert.assertEquals("Число элементов не увеличилось на 1", text, numberOfRemainingItems.getText());
        logger.info("Добавление нового элемента '" + nameOfItem + "'");
        return this;
    }

}
