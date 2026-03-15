package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class EditRoleTest extends BaseTest {

    @Test
    public void testEditRole() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage navBar    = new NavBarPage();
        RolesPage  rolesPage = new RolesPage();

        navBar.goToRoles();
        rolesPage.openEditRoleByName("Automation RoleEe");
        rolesPage.selectPermissionsAndMove(
                "عرض المشاريع",
                "تعديل المشاريع",
                "حذف المشاريع",
                "عرض المهام",
                "اضافة المهام",
                "تعديل المهام",
                "حذف المهام",
                "تعديل مستخدم",
                "حذف مستخدم",
                "عرض المستخدمين",
                "عرض الوظائف",
                "اضافة الوظائف",
                "تعديل الوظائف",
                "حذف الوظائف"
        );
        rolesPage.clickSave();
    }
}