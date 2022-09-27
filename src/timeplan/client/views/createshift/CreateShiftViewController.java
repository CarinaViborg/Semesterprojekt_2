package timeplan.client.views.createshift;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;

public class CreateShiftViewController implements ViewController
{
    private CreateShiftViewModel createShiftViewModel;
    private ViewHandler viewHandler;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField startText;

    @FXML
    private TextField slutText;

    @FXML
    private Label exceptionLabel;

    @FXML
    private ComboBox medarbejderComboBox;

    public void init()
    {
      createShiftViewModel = ViewModelFactory.getViewModelFactory().getCreateShiftViewModel();
      viewHandler = ViewHandler.getViewHandler();
        startText.textProperty().bindBidirectional(createShiftViewModel.startTextProperty());
        slutText.textProperty().bindBidirectional(createShiftViewModel.slutTextProperty());
        exceptionLabel.textProperty().bindBidirectional(createShiftViewModel.exceptionTextProperty());
        datePicker.valueProperty().bindBidirectional(createShiftViewModel.datePropProperty());
        try
        {
            medarbejderComboBox.getItems().addAll(createShiftViewModel.getEmployees());
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void onGodkendButton(ActionEvent actionEvent) throws RemoteException
    {
        if(createShiftViewModel.validateAll()){
        String startTimeText = startText.getText();
        String slutTimeText = slutText.getText();
        String[] startTimeSplit = startTimeText.split(":", 2);
        Time startTime = new Time(Integer.parseInt(startTimeSplit[0]), Integer.parseInt(startTimeSplit[1]), 0);
        String[] slutTimeSplit = slutTimeText.split(":", 2);
        Time slutTime = new Time(Integer.parseInt(slutTimeSplit[0]), Integer.parseInt(slutTimeSplit[1]), 0);
        int id = Integer.parseInt(getIDFromComboBox());
        Date date = Date.valueOf(datePicker.getValue());
        createShiftViewModel.createShift(id, date, startTime, slutTime);
        clear();
        Platform.runLater(() -> viewHandler.openAdminDepartmentPlan());
        }
    }

  private void clear()
  {
    datePicker.setValue(null);
    startText.clear();
    slutText.clear();
    exceptionLabel.setText("");
  }

  public void onAfbrydButton(ActionEvent actionEvent){
      clear();
      Platform.runLater(() -> viewHandler.openAdminDepartmentPlan());
    }

    public String getIDFromComboBox(){
        String string;
        string = (String) medarbejderComboBox.getValue();
        String[] strings = string.split(" - ", 2);
        string = strings[0];
        return string;
    }
  }


