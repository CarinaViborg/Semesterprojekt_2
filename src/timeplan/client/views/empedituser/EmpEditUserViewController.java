package timeplan.client.views.empedituser;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EmpEditUserViewController implements ViewController
{
  private EmpEditUserViewModel empEditUserViewModel;
  private ViewHandler viewHandler;

  @FXML
  private TextField mailText;

  @FXML
  private TextField tlfText;

  @FXML
  private Label exceptionLabel;

  public void init()
  {
    empEditUserViewModel = ViewModelFactory.getViewModelFactory().getEmpEditUserViewModel();
    viewHandler = ViewHandler.getViewHandler();
    mailText.textProperty().bindBidirectional(empEditUserViewModel.mailTextProperty());
    tlfText.textProperty().bindBidirectional(empEditUserViewModel.tlfTextProperty());
    exceptionLabel.textProperty().bindBidirectional(empEditUserViewModel.exceptionLabelProperty());
    try
    {
      fillTextField();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  public void fillTextField() throws RemoteException
  {
    ArrayList<String> user = empEditUserViewModel.getCurrentUser();
    mailText.setText(user.get(3));
    tlfText.setText(user.get(4));
  }

  public void onGodkendButton(ActionEvent actionEvent) throws RemoteException
  {
    if(empEditUserViewModel.validateAll())
    {
      empEditUserViewModel.empEditUser(mailText.getText(), tlfText.getText());
      Platform.runLater(() -> viewHandler.openStartPage());
    }
  }

  public void onAfbrydButton(ActionEvent actionEvent)
  {
    Platform.runLater(() -> viewHandler.openStartPage());
  }

  public void onGodkendAdminButton(ActionEvent actionEvent) throws RemoteException
  {
    if(empEditUserViewModel.validateAll())
    {
      empEditUserViewModel.empEditUser(mailText.getText(), tlfText.getText());
      Platform.runLater(() -> viewHandler.openAdminStartPage());
    }
  }

  public void onAfbrydAdminButton(ActionEvent actionEvent)
  {
    Platform.runLater(() -> viewHandler.openAdminStartPage());
  }

}
