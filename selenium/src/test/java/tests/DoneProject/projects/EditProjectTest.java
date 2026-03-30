package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.ProjectsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class EditProjectTest extends BaseTest {

    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
    }

    @Test(priority = 1)
    public void editProjectNameSuccessfully() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        // Setup: Create a base project to edit
        long timestamp = System.currentTimeMillis();
        String oldName = "Old Project " + timestamp;
        projectsPage.addProject(oldName);
        projectsPage.waitForToastToDisappear();

        // Act: Edit the project's name
        String newName = "Updated Project " + timestamp;
        projectsPage.editProject(oldName, newName);

        // Assert
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Edit project name failed!");
    }

    @Test(priority = 2)
    public void editProjectWithSpecialCharacters() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        // Setup: Create a base project to edit
        String oldName = "Base Special " + System.currentTimeMillis();
        projectsPage.addProject(oldName);
        projectsPage.waitForToastToDisappear();

        // Act: Edit the project's name to include special characters
        String specialName = "Updated @ # $ % ^ & * " + System.currentTimeMillis();
        projectsPage.editProject(oldName, specialName);

        // Assert
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Edit project with special characters failed!");
    }

    @Test(priority = 3)
    public void editProjectWithLongName() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        // Setup: Create a base project to edit
        String oldName = "Base Long " + System.currentTimeMillis();
        projectsPage.addProject(oldName);
        projectsPage.waitForToastToDisappear();

        // Act: Edit the project's name to an extremely long string
        String longName = "This is a very long updated project name for testing purposes " + System.currentTimeMillis();
        projectsPage.editProject(oldName, longName);

        // Assert
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Edit project with long name failed!");
    }
}