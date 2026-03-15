package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.ProjectsPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class EditProjectTest extends BaseTest {

    @Test
    public void editProjectSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage   navBar       = new NavBarPage();
        ProjectsPage projectsPage = new ProjectsPage();

        navBar.goToSectors();
        projectsPage.openSectorByName("Automation Sector Updated");
        projectsPage.editProject("Automation Project 1", "Automation Project Updated");
    }
}