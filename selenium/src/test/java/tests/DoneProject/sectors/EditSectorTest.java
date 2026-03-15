package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditSectorTest extends BaseTest {

    @Test
    public void editSectorSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage  navBar      = new NavBarPage();
        SectorsPage sectorsPage = new SectorsPage();

        navBar.goToSectors();
        sectorsPage.editSector("Automation Sector", "Automation Sector Updated");

        Assert.assertEquals(sectorsPage.getToastMessage(), "EditDone", "❌ Wrong toast");
    }
}