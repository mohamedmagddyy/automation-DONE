package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import com.DoneProject.utils.DriverManager;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * ============================================
 * اختبار تعديل الأدوار والصلاحيات
 * ============================================
 */
public class EditRoleTest {

    // ================= متغيرات الاختبار =================
    WebDriver driver;
    LoginPage loginPage;
    RolesPage rolesPage;
    NavBarPage navBar;

    // ================= إعداد الاختبار =================
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        // احصل على WebDriver
        driver = DriverManager.getDriver();

        // أنشئ كائنات الصفحات
        loginPage = new LoginPage();
        rolesPage = new RolesPage();
        navBar = new NavBarPage();

        // افتح صفحة تسجيل الدخول
        driver.get(Urls.BASE_URL + "/login");

        // سجل الدخول بحساب الإدارة
        loginPage.login("ismealadmin", "123456");
    }

    // ================= اختبار تعديل الدور =================
    @Test
    public void testEditRole() {
        // اذهب إلى صفحة إدارة الأدوار
        navBar.goToRoles();

        // افتح صفحة تعديل الدور المطلوب
        rolesPage.openEditRoleByName("Automation RoleEe");

        // اختر الصلاحيات المطلوبة وحركها للجانب الأيمن
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

        // احفظ التغييرات على الدور
        rolesPage.clickSave();
    }

    // ================= تنظيف الاختبار =================
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // أغلق المتصفح وحرر جميع الموارد
        DriverManager.quitDriver();
    }
}
