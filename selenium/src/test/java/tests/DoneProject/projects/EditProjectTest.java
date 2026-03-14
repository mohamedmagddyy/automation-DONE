package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.ProjectsPage;
import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditProjectTest {

    WebDriver driver;
    ProjectsPage projectsPage;

    @BeforeClass
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        projectsPage = new ProjectsPage();

        driver.get(Urls.BASE_URL + "/login");
        new LoginPage().login("ismealadmin", "123456");
    }

    @Test
    public void editProjectSuccessfully() {

        String sectorName = "Automation Sector Updated";
        String oldName = "Automation Project 1";
        String newName = "Automation Project Updated";

        projectsPage.openSectorByName(sectorName);
        projectsPage.editProject(oldName, newName);


    }
}