package timeplan.client.views.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;

public class LoginViewController implements ViewController
{
  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label exceptionLabel;

  private LoginViewModel loginViewModel;
  private ViewHandler viewHandler;

  public void init()
  {
    loginViewModel = ViewModelFactory.getViewModelFactory().getLoginViewModel();
    viewHandler = ViewHandler.getViewHandler();
    loginViewModel.addListener("Login State", this::handleLogin);
  }

  public void onLoginButton(ActionEvent actionEvent) throws RemoteException
  {
    loginViewModel.login(usernameField.getText(), passwordField.getText());
  }

  private void handleLogin(PropertyChangeEvent propertyChangeEvent)
  {
    if(propertyChangeEvent.getNewValue().equals("admin")){
      Platform.runLater(() -> viewHandler.openAdminStartPage());
    } else if(propertyChangeEvent.getNewValue().equals("employee")){
      Platform.runLater(() -> viewHandler.openStartPage());
    } else{
      Platform.runLater(() -> exceptionLabel.setText("Loginoplysninger er forkerte"));
    }
  }
}
