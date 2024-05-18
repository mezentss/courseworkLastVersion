package ru.mezentss.ui.task3;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LaptopPage extends BasePage{

    private static final Logger logger = Logger.getLogger(LaptopPage.class);

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//div[@data-auto-themename='listDetailed']")
    private List<WebElement> productList;

    @FindBy(xpath = "//button[@data-autotest-id='aprice']")
    private WebElement sort;

    @Step("Проверка открытия страницы 'Внутренние жесткие диски'")
    public LaptopPage checkOpenPage(){
        Assert.assertEquals("Заголовок не соответствует ",
                "Внутренние жесткие диски",
                title.getText());
        logger.info("Проверка открытия страницы");
        return this;
    }

    @Step("Ввод в лог первых 5 найденных товаров")
    public LaptopPage logProducts() {
        for (int i = 0; i < 5; i++) {
            WebElement product = productList.get(i);
            moveToElement(product);

            String title = product.findElement(By.xpath(".//h3")).getText();
            String price = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
            logger.info("Название: " + title + ". Цена: " + price);
        }
        return this;
    }

    @Step("Установить сортировку: подешевле")
    public LaptopPage setSortingLowPrice() {
        sort.click();
        return null;
    }

    @Step("Проверить, что каждый следующий товар стоит больше или равно предыдущему")
    public LaptopPage checkPricesOrder() {
        for (int i = 0; i < 10; i++) {
            WebElement currentProduct = productList.get(i);
            WebElement nextProduct = productList.get(i + 1);

            String currentPrice = currentProduct.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
            String nextPrice = nextProduct.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();

            double currentPriceValue = Double.parseDouble(currentPrice.replaceAll("[^0-9]", ""));
            double nextPriceValue = Double.parseDouble(nextPrice.replaceAll("[^0-9]", ""));

            if (currentPriceValue > nextPriceValue) {
                Assert.fail("Цена товара #" + (i + 2) + " меньше цены товара #" + (i + 1));
            }
        }
        logger.info("Цены каждого следующего товара больше или равны предыдущему");
        return this;
    }
}
