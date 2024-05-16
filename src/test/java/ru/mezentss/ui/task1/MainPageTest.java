package ru.mezentss.ui.task1;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

import ru.mezentss.ui.managers.DriverManager;
import ru.mezentss.ui.managers.InitManager;

public class MainPageTest {

    private final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){InitManager.initFramework();}

    @Before
    public void before(){driverManager.getDriver().get("https://lambdatest.github.io/sample-todo-app/");}

    @Test
    @DisplayName("Тестирование списка дел \"LambdaTest Sample App\"")
    public void test(){
        StartPage startPage  = new StartPage();
        startPage.checkText()
                .checkItem("First Item")
                .checkboxByName("First Item")
                .checkItem("Second Item")
                .checkboxByName("Second Item")
                .checkItem("Third Item")
                .checkboxByName("Third Item")
                .checkItem("Fourth Item")
                .checkboxByName("Fourth Item")
                .checkItem("Fifth Item")
                .checkboxByName("Fifth Item")
                .addNewItem("Sixth Item")
                .checkboxByName("Sixth Item");
    }

    @AfterClass
    public static void after(){InitManager.quitFramework();}
}
