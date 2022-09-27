package timeplan.client.views.createuser;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.rmi.RemoteException;
import java.sql.Date;

public class CreateUserViewController implements ViewController
{
  private CreateUserViewModel createUserViewModel;
  private ViewHandler viewHandler;

  @FXML
  private TextField nameField;

  @FXML
  private TextField lastNameField;

  @FXML
  private DatePicker datePicker;

  @FXML
  private TextField mailField;

  @FXML
  private TextField phoneField;

  @FXML
  private TextField passwordField;

  @FXML
  private Label exceptionLabel;

  @FXML
  private CheckBox isAdmin;

  public void init()
  {
    createUserViewModel = ViewModelFactory.getViewModelFactory()
        .getCreateUserViewModel();
    viewHandler = ViewHandler.getViewHandler();
    exceptionLabel.textProperty().bindBidirectional(createUserViewModel.exceptionProperty());
    nameField.textProperty().bindBidirectional(createUserViewModel.fornavnTextProperty());
    lastNameField.textProperty().bindBidirectional(
        createUserViewModel.efternavnTextProperty());
    passwordField.textProperty().bindBidirectional(createUserViewModel.kodeTextProperty());
    mailField.textProperty().bindBidirectional(createUserViewModel.mailTextProperty());
    datePicker.valueProperty().bindBidirectional(
        createUserViewModel.datepickerValueProperty());
    phoneField.textProperty().bindBidirectional(createUserViewModel.tlfTextProperty());
  }

  public void onOpretButton(ActionEvent actionEvent) throws RemoteException
  {
    if(createUserViewModel.validateAll())
    {
      Date date = Date.valueOf(datePicker.getValue());
      createUserViewModel.createUser(nameField.getText().trim(), lastNameField.getText().trim(), date, mailField.getText().trim(),
          phoneField.getText(), passwordField.getText(), isAdmin.isSelected());
      clear();
      Platform.runLater(() -> viewHandler.openAdminStartPage());
    }
  }

  private void clear()
  {
    nameField.clear();
    lastNameField.clear();
    mailField.clear();
    phoneField.clear();
    passwordField.clear();
    datePicker.setValue(null);
    isAdmin.selectedProperty().setValue(false);
  }

  public void onAfbrydButton(ActionEvent actionEvent)
  {
    clear();
    Platform.runLater(() -> viewHandler.openAdminStartPage());
  }
}

