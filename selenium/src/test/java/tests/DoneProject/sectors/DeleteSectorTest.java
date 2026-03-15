package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteSectorTest extends BaseTest {

    @Test
    public void deleteSectorSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage  navBar      = new NavBarPage();
        SectorsPage sectorsPage = new SectorsPage();

        navBar.goToSectors();
        sectorsPage.deleteSector("Automation Sector 2");

        Assert.assertEquals(sectorsPage.getToastMessage(), "Delete Done", "❌ Wrong toast");
    }
}