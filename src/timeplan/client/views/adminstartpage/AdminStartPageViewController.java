package timeplan.client.views.adminstartpage;

import javafx.application.Platform;
import timeplan.client.core.ViewHandler;
import timeplan.client.util.ViewController;

public class AdminStartPageViewController implements ViewController
{
  private ViewHandler viewHandler;

  public void init()
  {
    viewHandler = ViewHandler.getViewHandler();
  }

  public void onOpretMedarbejderButton(){
    Platform.runLater(() -> viewHandler.openCreateUser());
  }

  public void onRedigerMedarbejderButton(){
    Platform.runLater(() -> viewHandler.openEditUser());
  }

  public void onAfdelingsplanButton(){
    Platform.runLater(() -> viewHandler.openAdminDepartmentPlan());
  }

  public void onProfilButton(){
    Platform.runLater(() ->viewHandler.openAdminProfile());
  }

  public void onLogUdButton(){
    Platform.runLater(() -> viewHandler.openLogin());
  }

}
