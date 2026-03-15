package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavBarPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(NavBarPage.class);

    // ===== Dropdown Button =====
    private final By notificationDropdown = By.id("notifictions");

    // ===== Menu Items =====
    private final By homeMenu    = By.xpath("//a[@routerlink='/home-admin']");
    private final By sectorsMenu = By.xpath("//a[@routerlink='/adminstration']");
    private final By tasksBoard  = By.xpath("//a[@routerlink='/tasksboard']");
    private final By roles       = By.xpath("//a[@routerlink='/rolls']");
    private final By chat        = By.xpath("//a[@routerlink='/chat']");
    private final By settings    = By.xpath("//a[@routerlink='/setting']");
    private final By fileManager = By.xpath("//a[@routerlink='/file-manager']");
    private final By usersMenu   = By.xpath("//a[@routerlink='/users']");

    public NavBarPage() {
        super();
    }

    // ===== Helper =====
    private void clickDropdownItem(By menuItem) {
        jsClick(notificationDropdown);
        scrollTo(menuItem);
        jsClick(menuItem);
        waitForPageToBeReady();
        logger.info("✅ تم الانتقال بنجاح إلى: {}", menuItem);
    }

    // ===== Navigation Methods =====
    public void goToHome() {
        clickDropdownItem(homeMenu);
    }

    public void goToSectors() {
        clickDropdownItem(sectorsMenu);
    }

    public void goToTasksBoard() {
        clickDropdownItem(tasksBoard);
    }

    public void goToRoles() {
        clickDropdownItem(roles);
    }

    public void goToChat() {
        clickDropdownItem(chat);
    }

    public void goToSettings() {
        clickDropdownItem(settings);
    }

    public void goToFileManager() {
        clickDropdownItem(fileManager);
    }

    public void goToUsers() {
        clickDropdownItem(usersMenu);
    }
}