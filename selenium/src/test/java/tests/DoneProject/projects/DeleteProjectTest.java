package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.ProjectsPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DeleteProjectTest extends BaseTest {

    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
    }

    @Test(priority = 1)
    public void deleteExistingProject() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        // Setup: Add a project to delete
        String projectToDelete = "To Be Deleted " + System.currentTimeMillis();
        projectsPage.addProject(projectToDelete);

        // Wait for the creation toast to disappear before attempting to delete
        projectsPage.waitForToastToDisappear();

        // Act: Delete the project
        projectsPage.deleteProject(projectToDelete);

        // Assert: Verify deletion was successful
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Project deletion failed!");
    }

    @Test(priority = 2, expectedExceptions = {TimeoutException.class, NoSuchElementException.class, Exception.class})
    public void deleteNonExistingProject() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        // Act: Attempt to delete a project that doesn't exist
        // This is expected to throw an exception because the element won't be found.
        String nonExistingProject = "Non Existing Project " + System.currentTimeMillis();
        projectsPage.deleteProject(nonExistingProject);
    }
}