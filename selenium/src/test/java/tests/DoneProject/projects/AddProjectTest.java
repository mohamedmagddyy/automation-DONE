package tests.DoneProject.projects;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.ProjectsPage;
import com.DoneProject.utils.DriverManager;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddProjectTest {

    WebDriver driver;
    ProjectsPage projectsPage;


    @BeforeClass
    public void setUp() {
        driver = DriverManager.getDriver();
        projectsPage = new ProjectsPage();

        driver.get(Urls.BASE_URL + "/login");
        new LoginPage().login("ismealadmin", "123456");
    }

    @Test
    public void addProjectSuccessfully() {
        String sectorName = "Automation Sector Updated";
        String projectName = "Automation Project newwww";

        projectsPage.openSectorByName(sectorName);
        projectsPage.addProject(projectName);
        driver.navigate().back();  // Navigate Back


    }
}