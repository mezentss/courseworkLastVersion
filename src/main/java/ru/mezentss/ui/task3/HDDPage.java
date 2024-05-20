package ru.mezentss.ui.task3;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HDDPage extends BasePage{

    private static final Logger logger = Logger.getLogger(HDDPage.class);

    @FindBy(xpath = "//div[@data-auto-themename='listDetailed']")
    private List<WebElement> productList;

    @FindBy(xpath = "//button[@data-autotest-id='aprice']")
    private WebElement sort;

    @Step("Ввод в лог первых 5 найденных товаров")
    public HDDPage logProducts() {
        for (int i = 0; i < 5; i++) {
            WebElement product = productList.get(i);
            moveToElement(product);

            String title = product.findElement
                    (By.xpath(".//h3"))
                    .getText();
            String price = product.findElement
                            (By.xpath(".//span[@data-auto='snippet-price-current']/span[1]"))
                    .getText();
            logger.info("Название: " + title + ". Цена: " + price);
        }
        return this;
    }

    @Step("Установить сортировку: подешевле")
    public HDDPage setSortingLowPrice() {
        sort.click();
        return this;
    }

    @Step("Проверить, что товары отсортированы по возрастанию цены")
    public synchronized HDDPage verifyProductsSortedByPrice() throws InterruptedException{
        wait(3000);
        List<WebElement> productListSorted = driverManager.getDriver().findElements(By.xpath("//div[@data-auto-themename='listDetailed']"));
        boolean isSorted = true;
        for (int i = 1; i < 11; i++) {
            String currentPriceStr = productListSorted.get(i).findElement(By.xpath
                            (".//span[@data-auto='snippet-price-current']/span[1]"))
                    .getText().replaceAll("[^0-9]", "");
            String previousPriceStr = productListSorted.get(i - 1).findElement
                            (By.xpath(".//span[@data-auto='snippet-price-current']/span[1]"))
                    .getText().replaceAll("[^0-9]", "");

            int currentPrice = Integer.parseInt(currentPriceStr);
            int previousPrice = Integer.parseInt(previousPriceStr);

            if (currentPrice < previousPrice) {
                isSorted = false;
                break;
            }
        }
        Assert.assertTrue("Товары не отсортированы по возрастанию цены", isSorted);
        logger.info("Товары отсортированы по возрастанию");
        return this;
    }
}
