package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavBarPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(NavBarPage.class);

    private final By notificationDropdown = By.id("notifictions");
    private final By homeMenu             = By.xpath("//a[@routerlink='/home-admin']");
    private final By sectorsMenu          = By.xpath("//a[@routerlink='/adminstration']");
    private final By tasksBoardMenu       = By.xpath("//a[@routerlink='/tasksboard']");
    private final By rolesMenu            = By.xpath("//a[@routerlink='/rolls']");
    private final By chatMenu             = By.xpath("//a[@routerlink='/chat']");
    private final By settingsMenu         = By.xpath("//a[@routerlink='/setting']");
    private final By fileManagerMenu      = By.xpath("//a[@routerlink='/file-manager']");
    private final By usersMenu            = By.xpath("//a[@routerlink='/users']");
    private final By recurringTaskMenu    = By.xpath("//a[@routerlink='/recurring-task']");

    public NavBarPage() {
        super();
    }

    private void navigateTo(By menuItem, String pageName) {
        logger.info("Navigating to: {}", pageName);
        jsClick(notificationDropdown);
        scrollTo(menuItem);
        jsClick(menuItem);
        waitForPageToBeReady();
    }

    public void goToHome() {
        navigateTo(homeMenu, "Home");
    }

    public ProjectsPage goToSectors() {
        navigateTo(sectorsMenu, "Sectors/Projects");
        return new ProjectsPage();
    }

    public TasksPage goToTasksBoard() {
        navigateTo(tasksBoardMenu, "Tasks Board");
        return new TasksPage();
    }

    public RolesPage goToRoles() {
        navigateTo(rolesMenu, "Roles Management");
        return new RolesPage();
    }

    public void goToChat() {
        navigateTo(chatMenu, "Chat");
    }

    public void goToSettings() {
        navigateTo(settingsMenu, "Settings");
    }

    public FileManagerPage goToFileManager() {
        navigateTo(fileManagerMenu, "File Manager");
        return new FileManagerPage();
    }

    public UsersPage goToUsers() {
        navigateTo(usersMenu, "Users Management");
        return new UsersPage();
    }

    public RecurringTaskPage goToRecurringTask() {
        navigateTo(recurringTaskMenu, "Recurring Task");
        return new RecurringTaskPage();
    }
}