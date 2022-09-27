package timeplan.client.views.startpage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.rmi.RemoteException;

public class StartPageViewController implements ViewController
{
  private StartPageViewModel startPageViewModel;
  private ViewHandler viewHandler;

  @FXML
  private Button tjekIndButton;

  @FXML
  private Button tjekUdButton;

  public void init()
  {
    startPageViewModel = ViewModelFactory.getViewModelFactory().getStartPageViewModel();
    viewHandler = ViewHandler.getViewHandler();
    try
    {
      if(!startPageViewModel.isCheckedIn()){
        tjekIndButton.setVisible(true);
        tjekIndButton.setDisable(false);
      } else {
        tjekIndButton.setVisible(false);
        tjekIndButton.setDisable(true);
        tjekUdButton.setVisible(true);
        tjekUdButton.setDisable(false);
      }
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  public void onMineVagterButton(ActionEvent actionEvent){
    Platform.runLater(() -> viewHandler.openPersonalPlan());
  }

  public void onAfdelingsplanButton(ActionEvent actionEvent){
    Platform.runLater(() -> viewHandler.openDepartmentPlan());
  }

  public void onTjekIndButton(ActionEvent actionEvent) throws RemoteException
  {
    tjekIndButton.setVisible(false);
    tjekIndButton.setDisable(true);
    tjekUdButton.setVisible(true);
    tjekUdButton.setDisable(false);
    startPageViewModel.tjekInd();
  }

  public void onTjekUdButton(ActionEvent actionEvent) throws RemoteException
  {
    tjekIndButton.setVisible(true);
    tjekIndButton.setDisable(false);
    tjekUdButton.setVisible(false);
    tjekUdButton.setDisable(true);
    startPageViewModel.tjekUd();
  }

  public void onProfilbutton(ActionEvent actionEvent){
    Platform.runLater(() -> viewHandler.openEmpEditUser());
  }

  public void onLogUdButton(ActionEvent actionEvent){
    Platform.runLater(() -> viewHandler.openLogin());
  }
}
