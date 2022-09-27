package timeplan.client.views.editshift;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;

public class EditShiftViewController implements ViewController
{
  private EditShiftViewModel editShiftViewModel;
  private ViewHandler viewHandler;

  @FXML
  private ComboBox vagtComboBox;

  @FXML
  private TextField startText;

  @FXML
  private TextField slutText;

  @FXML
  private Label exceptionLabel;


  public void init()
  {
    editShiftViewModel = ViewModelFactory.getViewModelFactory().getEditShiftViewModel();
    viewHandler = ViewHandler.getViewHandler();
    exceptionLabel.textProperty().bindBidirectional(
        editShiftViewModel.exceptionTextProperty());
    startText.textProperty().bindBidirectional(editShiftViewModel.startTextProperty());
    slutText.textProperty().bindBidirectional(editShiftViewModel.slutTextProperty());
    try
    {
      vagtComboBox.getItems().addAll(editShiftViewModel.getAllShiftsAsString());
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  public void onGodkendButton(ActionEvent actionEvent) throws RemoteException
  {
    if(editShiftViewModel.validateAll())
    {
      String[] shift = getShiftFromComboBox();
      int id = Integer.parseInt(shift[0]);
      Date date = Date.valueOf(shift[1]);
      String[] startTimeSplit = startText.getText().split(":", 2);
      Time startTime = new Time(Integer.parseInt(startTimeSplit[0]), Integer.parseInt(startTimeSplit[1]), 0);
      String[] slutTimeSplit = slutText.getText().split(":", 2);
      Time slutTime = new Time(Integer.parseInt(slutTimeSplit[0]), Integer.parseInt(slutTimeSplit[1]), 0);
      editShiftViewModel.editShift(id, date, startTime, slutTime);
      clear();
      Platform.runLater(() -> viewHandler.openAdminDepartmentPlan());
    }
  }

  private void clear()
  {
    startText.clear();
    slutText.clear();
    exceptionLabel.setText("");
  }

  public void onAfbrydButton(ActionEvent actionEvent){
    clear();
    Platform.runLater(() -> viewHandler.openAdminDepartmentPlan());
  }

  public void onFjernVagtButton(ActionEvent actionEvent){
    String[] shift = getShiftFromComboBox();
    int id = Integer.parseInt(shift[0]);
    Date date = Date.valueOf(shift[1]);
    try
    {
      editShiftViewModel.removeShift(id, date);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    clear();
    Platform.runLater(() -> viewHandler.openAdminDepartmentPlan());
  }

  public String[] getShiftFromComboBox(){
    String string;
    string = (String) vagtComboBox.getValue();
    String[] idString = string.split(" - ", 2);
    String[] datoString = idString[1].split(" ", 2);
    idString[1] = datoString[0];
    return idString;
  }
}
