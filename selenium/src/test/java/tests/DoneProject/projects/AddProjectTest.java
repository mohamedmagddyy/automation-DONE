package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.ProjectsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class AddProjectTest extends BaseTest {

    @Test(priority = 1)
    public void addProjectSuccessfully() {
        ProjectsPage projectsPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToSectors()
                .openSectorByName("Automation Sector Updated")
                .addProject("Automation Project New");
        
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Project creation failed!");
    }

    @Test(priority = 2)
    public void addProjectWithSpecialChars() {
        String specialName = "Proj @ # $ % ^ & *";
        ProjectsPage projectsPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToSectors()
                .openSectorByName("Automation Sector Updated")
                .addProject(specialName);
        
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Special characters project failed!");
    }

    @Test(priority = 3)
    public void addProjectWithLongName() {
        String longName = "This is an extremely long project name " + System.currentTimeMillis();
        ProjectsPage projectsPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToSectors()
                .openSectorByName("Automation Sector Updated")
                .addProject(longName);
        
        List<String> messages = projectsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Long name project failed!");
    }

    @Test(priority = 4)
    public void addMultipleProjectsSequential() {
        ProjectsPage projectsPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToSectors()
                .openSectorByName("Automation Sector Updated");
        
        for(int i = 1; i <= 3; i++) {
            projectsPage.addProject("Sequential Project " + i);
            List<String> messages = projectsPage.getAllToastMessages();
            Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Sequential project " + i + " failed!");
            projectsPage.waitForToastToDisappear();
        }
    }

    @Test(priority = 5)
    public void addAndDeleteProjectLifecycle() {
        String tempProject = "Lifecycle Project " + System.currentTimeMillis();
        ProjectsPage projectsPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToSectors()
                .openSectorByName("Automation Sector Updated")
                .addProject(tempProject);
        
        Assert.assertTrue(projectsPage.getAllToastMessages().stream().anyMatch(m -> m.contains("successfully")));
        projectsPage.waitForToastToDisappear();

        projectsPage.deleteProject(tempProject);
        Assert.assertTrue(projectsPage.getAllToastMessages().stream().anyMatch(m -> m.contains("successfully")));
    }
}