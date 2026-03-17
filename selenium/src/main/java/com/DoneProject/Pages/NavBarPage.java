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
    private void clickDropdownItem(By menuItem, String pageName) {
        logger.info("🧭 Navigating to: {}", pageName);
        jsClick(notificationDropdown);
        scrollTo(menuItem);
        jsClick(menuItem);
        waitForPageToBeReady();
    }

    // ===== Navigation Methods (Fluent) =====
    public void goToHome() {
        clickDropdownItem(homeMenu, "Home");
    }

    public ProjectsPage goToSectors() {
        clickDropdownItem(sectorsMenu, "Sectors/Projects");
        return new ProjectsPage();
    }

    public void goToTasksBoard() {
        clickDropdownItem(tasksBoard, "Tasks Board");
    }

    public RolesPage goToRoles() {
        clickDropdownItem(roles, "Roles Management");
        return new RolesPage();
    }

    public void goToChat() {
        clickDropdownItem(chat, "Chat");
    }

    public void goToSettings() {
        clickDropdownItem(settings, "Settings");
    }

    public void goToFileManager() {
        clickDropdownItem(fileManager, "File Manager");
    }

    public UsersPage goToUsers() {
        clickDropdownItem(usersMenu, "Users Management");
        return new UsersPage();
    }
}