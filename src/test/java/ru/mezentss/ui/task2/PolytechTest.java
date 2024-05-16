package ru.mezentss.ui.task2;

import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.mezentss.ui.managers.*;

public class PolytechTest {
    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){
        InitManager.initFramework();
    }

    @Before
    public void before(){
        driverManager.getDriver().get("https://mospolytech.ru/");
    }

    @Test
    @DisplayName("Тестирование страницы расписания на сайте Мосполитеха")
    public void test(){
        StartPage startPage  = new StartPage();
        startPage.checkOpenPage().
                clickOnHamburgerMenu()
                .mouseOnMenuItem("Обучающимся")
                .clickOnMenuItem("Расписания")
                .checkOpenPage()
                .clickOnButton()
                .checkOpenPage()
                .inputGroupNumber("221-361")
                .clickOnGroup("221-361");
    }

    @AfterClass
    public static void after(){
        InitManager.quitFramework();
    }
}
