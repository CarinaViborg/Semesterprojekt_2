package timeplan.client.views.adminedituser;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AdminEditUserViewController implements ViewController
{
  private AdminEditUserViewModel adminEditUserViewModel;
  private ViewHandler viewHandler;

  @FXML private ComboBox medarbejderComboBox;

  @FXML private TextField fornavnText;

  @FXML private TextField efternavnText;

  @FXML private TextField mailText;

  @FXML private TextField tlfText;

  @FXML private Label exceptionLabel;

  @FXML private TextField adgangskodeText;

  public void init()
  {
    adminEditUserViewModel = ViewModelFactory.getViewModelFactory()
        .getEditUserViewModel();
    viewHandler = ViewHandler.getViewHandler();
    fornavnText.textProperty()
        .bindBidirectional(adminEditUserViewModel.fornavnLabelProperty());
    efternavnText.textProperty()
        .bindBidirectional(adminEditUserViewModel.efternavnLabelProperty());
    mailText.textProperty()
        .bindBidirectional(adminEditUserViewModel.mailLabelProperty());
    tlfText.textProperty()
        .bindBidirectional(adminEditUserViewModel.phoneLabelProperty());
    adgangskodeText.textProperty()
        .bindBidirectional(adminEditUserViewModel.kodeLabelProperty());
    exceptionLabel.textProperty()
        .bindBidirectional(adminEditUserViewModel.exceptionStringProperty());
    try
    {
      medarbejderComboBox.getItems()
          .addAll(adminEditUserViewModel.getEmployees());
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  public void onGodkendButton(ActionEvent actionEvent) throws RemoteException
  {
    String id = getIDFromComboBox();
    if (adminEditUserViewModel.validateAll())
    {
      adminEditUserViewModel.editUser(Integer.parseInt(id),
          fornavnText.getText().trim(), efternavnText.getText().trim(),
          mailText.getText().trim(), Integer.parseInt(tlfText.getText()),
          adgangskodeText.getText());
      clear();
      Platform.runLater(() -> viewHandler.openAdminStartPage());
    }
  }

  public void clear()
  {
    fornavnText.clear();
    efternavnText.clear();
    mailText.clear();
    tlfText.clear();
    exceptionLabel.setText("");
    adgangskodeText.clear();
  }

  public void onAfbrydButton(ActionEvent actionEvent)
  {
    Platform.runLater(() -> viewHandler.openAdminStartPage());
    clear();
  }

  public void onSletButton(ActionEvent actionEvent) throws RemoteException
  {
    String id = getIDFromComboBox();
    if (adminEditUserViewModel.deleteEmployee(Integer.parseInt(id)))
    {
      Platform.runLater(() -> viewHandler.openAdminStartPage());
      clear();
    }
  }

  public void onComboBox(ActionEvent actionEvent) throws RemoteException
  {
    String id = getIDFromComboBox();
    ArrayList<String> employee = adminEditUserViewModel.getEmployeeByID(id);
    fornavnText.setText(employee.get(1));
    efternavnText.setText(employee.get(2));
    mailText.setText(employee.get(3));
    tlfText.setText(employee.get(4));
  }

  public String getIDFromComboBox()
  {
    String string;
    string = (String) medarbejderComboBox.getValue();
    String[] strings = string.split(" - ", 2);
    string = strings[0];
    return string;
  }
}
