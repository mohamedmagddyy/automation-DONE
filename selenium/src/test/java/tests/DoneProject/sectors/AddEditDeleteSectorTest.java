package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddEditDeleteSectorTest extends BaseTest {

    @Test(priority = 1)
    public void addSectorSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage  navBar      = new NavBarPage();
        SectorsPage sectorsPage = new SectorsPage();

        navBar.goToSectors();
        sectorsPage.addSector("Automation Sector 3");
        sectorsPage.selectManagerByName("اسماعيل");

        Assert.assertEquals(sectorsPage.getToastMessage(), "Add Done", "❌ Add toast خاطئ");
    }

    @Test(priority = 2)
    public void editSectorSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage  navBar      = new NavBarPage();
        SectorsPage sectorsPage = new SectorsPage();

        navBar.goToSectors();
        sectorsPage.editSector("Automation Sector 3", "Automation Sector Updated 3");

        Assert.assertEquals(sectorsPage.getToastMessage(), "EditDone", "❌ Edit toast خاطئ");
    }

    @Test(priority = 3)
    public void deleteSectorSuccessfully() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage  navBar      = new NavBarPage();
        SectorsPage sectorsPage = new SectorsPage();

        navBar.goToSectors();
        sectorsPage.deleteSector("Automation Sector Updated 3");

        Assert.assertEquals(sectorsPage.getToastMessage(), "Delete Done", "❌ Delete toast خاطئ");
    }
}