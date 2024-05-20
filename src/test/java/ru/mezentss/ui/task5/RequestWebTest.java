package ru.mezentss.ui.task5;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.mezentss.ui.managers.DriverManager;
import ru.mezentss.ui.managers.InitManager;

public class RequestWebTest {
    private static final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){
        InitManager.initFramework();
        driverManager.getDriver().get("https://reqres.in/");
    }

    @Test
    @DisplayName("Проверка того, что при нажатии на кнопку отправки образца запроса результат такой же как и через API")
    public void test() {
        StartPage startPage = new StartPage();
        startPage.checkOpenPage()
                .clickOnButtonAndCheckAPI("List users", "get")
                .clickOnButtonAndCheckAPI("Single user", "get")
                .clickOnButtonAndCheckAPI("Single user not found", "get")
                .clickOnButtonAndCheckAPI("List <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource> not found", "get")
                .clickOnButtonAndCheckAPI("Create", "post")
                .clickOnButtonAndCheckAPI("Update", "put")
                .clickOnButtonAndCheckAPI("Update", "patch")
                .clickOnButtonAndCheckAPI("Delete", "delete")
                .clickOnButtonAndCheckAPI("Register - successful", "post")
                .clickOnButtonAndCheckAPI("Register - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Login - successful", "post")
                .clickOnButtonAndCheckAPI("Login - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Delayed response", "get");
    }
    @AfterClass
    public static void after() {
        InitManager.quitFramework();
    }

}
