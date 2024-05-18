package ru.mezentss.ui.task4;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;

import ru.mezentss.ui.managers.DriverManager;
import ru.mezentss.ui.managers.InitManager;

public class GoogleSearchTest {

    private final DriverManager driverManager = DriverManager.getInstance();
    private StartPage googleSearch;

    @BeforeClass
    public static void beforeClass(){InitManager.initFramework();}

    @Before
    public void before(){
        googleSearch = new StartPage();
    }

    @Test
    @DisplayName("Тестирование поиска 'Selenium' в Google")
    public void GoogleSearchTest(){
        googleSearch.openPage()
                .enterQuery("Selenium")
                .clickSearchButton()
                .checkResults()
                .openNews()
                .checkNews();
    }

    @AfterClass
    public static void after(){InitManager.quitFramework();}
}