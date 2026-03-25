package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.ProjectsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AddProjectTest extends BaseTest {

    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);
    }

    @Test(priority = 1)
    public void addProjectSuccessfully() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        String projectName = "Automation Project " + System.currentTimeMillis();
        projectsPage.addProject(projectName);

        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Project creation failed!");
    }

    @Test(priority = 2)
    public void addProjectWithSpecialChars() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        String specialName = "Proj @ # $ % ^ & * " + System.currentTimeMillis();
        projectsPage.addProject(specialName);

        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Special characters project failed!");
    }

    @Test(priority = 3)
    public void addProjectWithLongName() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        String longName = "This is an extremely long project name added for testing purposes " + System.currentTimeMillis();
        projectsPage.addProject(longName);

        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Long name project failed!");
    }

    @Test(priority = 4)
    public void addMultipleProjectsSequential() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        long timestamp = System.currentTimeMillis();
        for (int i = 1; i <= 3; i++) {
            String projectName = "Sequential Project " + i + " - " + timestamp;
            projectsPage.addProject(projectName);
            
            List<String> messages = projectsPage.getAllToastMessages();
            Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Sequential project " + i + " failed!");
            
            projectsPage.waitForToastToDisappear();
        }
    }

    @Test(priority = 5)
    public void addAndDeleteProjectLifecycle() {
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        ProjectsPage projectsPage = new ProjectsPage();
        projectsPage.openSectorByName("Automation Sector Updated");

        String tempProject = "Lifecycle Project " + System.currentTimeMillis();
        projectsPage.addProject(tempProject);

        List<String> addMessages = projectsPage.getAllToastMessages();
        Assert.assertTrue(addMessages.stream().anyMatch(m -> m.contains("successfully")), "Lifecycle Add project failed!");
        
        projectsPage.waitForToastToDisappear();

        projectsPage.deleteProject(tempProject);
        
        List<String> deleteMessages = projectsPage.getAllToastMessages();
        Assert.assertTrue(deleteMessages.stream().anyMatch(m -> m.contains("successfully")), "Lifecycle Delete project failed!");
    }
}