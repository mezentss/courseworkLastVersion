package ru.mezentss.ui.task4;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.mezentss.ui.managers.DriverManager;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));


    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected WebElement waitUntilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driverManager.getDriver();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void moveToElement(WebElement element) {
        Actions action = new Actions(driverManager.getDriver());
        action.moveToElement(element).build().perform();
    }

    public WebElement findLanguage(String languageName){
        WebElement language = null;

        List<WebElement> languages = driverManager.getDriver().findElements(By.xpath("//div[@class='Q1bVje'][.//span[@data-value='" + languageName + "']]"));
        for (WebElement lang : languages){
            if (lang.isDisplayed()){
                language = lang;
                break;
            }
        }
        return language != null ? language : driverManager.getDriver().findElement(By.xpath("//div[@class='Q1bVje'][.//span[@data-value='" + languageName + "']]"));
    }

}

