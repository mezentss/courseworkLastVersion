package ru.mezentss.ui.task4;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import ru.mezentss.ui.managers.DriverManager;
import ru.mezentss.ui.managers.InitManager;

public class LanguageChangeTest {

    private final DriverManager driverManager = DriverManager.getInstance();
    private SettingsPage googleSearch;

    @BeforeClass
    public static void beforeClass(){InitManager.initFramework();}

    @Before
    public void before(){
        googleSearch = new SettingsPage();
    }

    @Test
    @DisplayName("Изменение языка интерфейса")
    public void GoogleSearchTest() throws InterruptedException{
        googleSearch.openPage()
                .openSettings()
                .openSearchSettings()
                .chooseLanguage()
                .validateLanguage();
    }

    @AfterClass
    public static void after(){InitManager.quitFramework();}
}
