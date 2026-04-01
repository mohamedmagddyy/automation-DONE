package com.DoneProject.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavBarPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(NavBarPage.class);

    // --- Locators (Private & Stable) ---
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

    // --- Navigation Helper ---
    private void navigateTo(By menuItem, String pageName) {
        logger.info("Navigating to: {}", pageName);
        // Sometimes the notification dropdown or other overlays block the navbar
        jsClick(notificationDropdown); 
        scrollTo(menuItem);
        click(menuItem);
        waitForPageToBeReady();
    }

    // --- Public Navigation Actions ---

    public DashboardPage goToDashboard() {
        navigateTo(homeMenu, "Dashboard");
        return new DashboardPage();
    }

    public SectorsPage goToSectors() {
        navigateTo(sectorsMenu, "Sectors Management");
        return new SectorsPage();
    }

    public TasksPage goToTasksBoard() {
        navigateTo(tasksBoardMenu, "Tasks Board");
        return new TasksPage();
    }

    public RolesPage goToRoles() {
        navigateTo(rolesMenu, "Roles Management");
        return new RolesPage();
    }

    public ChatPage goToChat() {
        navigateTo(chatMenu, "Chat");
        return new ChatPage();
    }

    public SettingsPage goToSettings() {
        navigateTo(settingsMenu, "Settings");
        return new SettingsPage();
    }

    public FileManagerPage goToFileManager() {
        navigateTo(fileManagerMenu, "File Manager");
        return new FileManagerPage();
    }

    public UsersPage goToUsers() {
        navigateTo(usersMenu, "Users Management");
        return new UsersPage();
    }

    public RecurringTaskPage goToRecurringTasks() {
        navigateTo(recurringTaskMenu, "Recurring Tasks");
        return new RecurringTaskPage();
    }

    /**
     * Specialized flow: Navigates to Sectors then opens a specific sector.
     * @param sectorName Name of the sector to open.
     * @return ProjectsPage for the selected sector.
     */
    public ProjectsPage goToProjects(String sectorName) {
        logger.info("Navigation flow: NavBar -> Sectors -> Projects for sector: {}", sectorName);
        // goToSectors() returns a SectorsPage (updated from previous ProjectsPage return)
        // However, looking at the previous implementation, goToSectors returned ProjectsPage.
        // Let's stick to returning SectorsPage if the menu leads there, 
        // OR if the menu actually leads to a page called "Sectors/Projects", 
        // we use ProjectsPage.openSectorByName.
        
        // Based on the file list, SectorsPage handles adding/editing sectors.
        // ProjectsPage handles sector links.
        
        navigateTo(sectorsMenu, "Sectors Page");
        return new ProjectsPage().openSectorByName(sectorName);
    }
}
