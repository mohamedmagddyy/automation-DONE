package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddSectorTest extends BaseTest {

    @Test
    public void addSectorSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage  navBar      = new NavBarPage();
        SectorsPage sectorsPage = new SectorsPage();

        navBar.goToSectors();
        sectorsPage.addSector("Automation Sector 2");
        sectorsPage.selectManagerByName("اسماعيل");

        Assert.assertEquals(sectorsPage.getToastMessage(), "Add Done", "❌ Wrong toast");
    }
}