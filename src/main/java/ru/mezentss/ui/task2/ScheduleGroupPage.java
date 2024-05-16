package ru.mezentss.ui.task2;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

public class ScheduleGroupPage extends BasePage{

    private static final Logger logger = Logger.getLogger(ScheduleGroupPage.class);

    @FindBy(tagName = "h4")
    private WebElement title;

    @FindBy(xpath = "//input[@placeholder='группа ...']")
    private WebElement inputGroup;

    @FindBy(xpath = "//div[contains(@class, 'found-groups')]/*")
    private List<WebElement> groupsList;

    @FindBy(xpath = "//div[contains(@class, 'schedule-day_today')]/div[contains(@class, 'title')]")
    private WebElement dayToday;

    @Step("Проверка открытия страницы 'Расписание занятий'")
    public ScheduleGroupPage checkOpenPage(){
        Assert.assertEquals("Заголовок не соответствует ",
                "Расписание занятий",
                title.getText());
        logger.info("Проверка открытия страницы");
        return this;
    }

    @Step("Ввод группы {numberOfGroup}")
    public ScheduleGroupPage inputGroupNumber(String numberOfGroup){
        waitUntilElementToBeClickable(inputGroup).click();
        inputGroup.sendKeys(numberOfGroup);
        inputGroup.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals("В результатах поиска отображается не только искомая группа", 1, groupsList.size());
        Assert.assertEquals("В результатах поиска не отображается искомая группа", numberOfGroup, findGroup(groupsList, numberOfGroup));
        logger.info("Ввод группы " + numberOfGroup);
        return this;
    }

    @Step("Выбор расписания для группы {numberOfGroup}")
    public ScheduleGroupPage clickOnGroup(String numberOfGroup) {
        for (WebElement group: groupsList) {
            if (group.getAttribute("id").equals(numberOfGroup)) {
                group.click();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals("Расписание не открылось ", "Расписание " + numberOfGroup,driverManager.getDriver().getTitle());
                if (!Objects.equals(getCurrentDayOfWeek(), "Воскресенье")) {
                    Assert.assertEquals("Текущий день недели не выделен цветом", getCurrentDayOfWeek(), dayToday.getText());
                }
                logger.info("Выбор расписания для группы " + numberOfGroup);
                return this;
            }
        }
        Assert.fail("Ошибка");
        return this;
    }
}
