package ru.mezentss.ui.task3;

import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.mezentss.ui.managers.*;

public class YandexMarketTest {

    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){
        InitManager.initFramework();
    }

    @Before
    public void before(){
        driverManager.getDriver().get("https://market.yandex.ru/");

    }
    @AfterClass
    public static void after(){
        InitManager.quitFramework();
    }


    @Test
    @DisplayName("Яндекс Маркет: проверка сортировки товаров по цене")
    public void test(){
        StartPage startPage  = new StartPage();
        startPage.checkOpenPage()
                .clickOnCatalog()
                .moveToCategory("Ноутбуки и компьютеры")
                .clickOnMenuItem("Внутренние жесткие диски")
                .logProducts()
                .setSortingLowPrice()
                .checkPricesOrder();
    }
}
