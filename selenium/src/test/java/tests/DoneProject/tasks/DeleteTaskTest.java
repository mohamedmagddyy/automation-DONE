package tests.DoneProject.tasks;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.TasksPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class DeleteTaskTest extends BaseTest {

    @Test
    public void testDeleteTask() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage navBar    = new NavBarPage();
        TasksPage  tasksPage = new TasksPage();

        navBar.goToTasksBoard();
        tasksPage.deleteFirstTask();
    }
}