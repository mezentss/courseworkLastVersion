package ru.mezentss.ui.task3;


public class PageManager {

    private static PageManager INSTANCE = null;
    private StartPage startPage;
    private HDDPage hddPage;

    private PageManager() {
    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public HDDPage getHDDPage() {
        if (hddPage == null) {
            hddPage = new HDDPage();
        }
        return hddPage;
    }

}

