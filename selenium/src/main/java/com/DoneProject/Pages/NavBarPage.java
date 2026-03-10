package com.DoneProject.Pages;

import org.openqa.selenium.By;

public class NavBarPage extends BasePage {

    // ===== Dropdown button =====
    private final By notificationDropdown = By.id("notifictions"); // الزرار اللي يفتح القائمة

    // ===== Menu Items =====
    private final By homeMenu = By.xpath("//a[@routerlink='/home-admin']");
    private final By sectorsMenu = By.cssSelector(".menu-item[routerlink='/adminstration']"); // اسمها صح
    private final By tasksBoard = By.xpath("//a[@routerlink='/tasksboard']");
    private final By roles = By.xpath("//a[@routerlink='/rolls']");
    private final By chat = By.xpath("//a[@routerlink='/chat']");
    private final By settings = By.xpath("//a[@routerlink='/setting']");
    private final By fileManager = By.xpath("//a[@routerlink='/file-manager']");
    private final By usersMenu = By.xpath("//a[@routerlink='/users']");

    // ===== Helper: click dropdown then item (stable for Angular) =====
    private void clickDropdownItem(By dropdownBtn, By menuItem) {
        // Click dropdown button using JS click for stability
        jsClick(dropdownBtn);

        // Scroll to menu item and JS click for stability
        scrollTo(menuItem);
        jsClick(menuItem);

        waitForPageToBeReady();
    }

    // ===== Navigation methods =====
    public void goToHome() {
        clickDropdownItem(notificationDropdown, homeMenu);
    }

    public void goToSectors() {
        clickDropdownItem(notificationDropdown, sectorsMenu);
    }

    public void goToTasksBoard() {
        clickDropdownItem(notificationDropdown, tasksBoard);
    }

    public void goToRoles() {
        clickDropdownItem(notificationDropdown, roles);
    }

    public void goToChat() {
        clickDropdownItem(notificationDropdown, chat);
    }

    public void goToSettings() {
        clickDropdownItem(notificationDropdown, settings);
    }

    public void goToFileManager() {
        clickDropdownItem(notificationDropdown, fileManager);
    }

    public void goToUsers() {
        clickDropdownItem(notificationDropdown, usersMenu);
    }
}
