package ru.mezentss.ui.task4;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

import ru.mezentss.ui.managers.DriverManager;
import ru.mezentss.ui.managers.InitManager;

public class GoogleTranslateTest {
    private final DriverManager driverManager = DriverManager.getInstance();
    private TranslatePage translatePage;

    @BeforeClass
    public static void beforeClass(){InitManager.initFramework();}

    @Before
    public void before(){
        translatePage = new TranslatePage();
    }

    @Test
    @DisplayName("Тестирование перевода фразы")
    public void GoogleTranslateTest() throws InterruptedException {
        translatePage.openPage()
                .enterQuery("Переводчик")
                .clickChangeLanguagesButton()
                .enterText("Поставьте 5, пожалуйста")
                .checkTranslation("Give it a 5 please");
    }

    @AfterClass
    public static void after(){InitManager.quitFramework();}
}