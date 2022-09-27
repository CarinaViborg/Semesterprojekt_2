package timeplan.client.views.editshift;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class EditShiftViewModel
{
  private final TimeplanModel model;
  private final StringProperty exceptionText = new SimpleStringProperty();
  private final StringProperty startText = new SimpleStringProperty();
  private final StringProperty slutText = new SimpleStringProperty();

  public EditShiftViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
  }

  public ArrayList<String> getAllShiftsAsString() throws RemoteException
  {
    return model.getAllShiftsAsString();
  }

  public void editShift(int id, Date dato, Time startTime, Time slutTime)
      throws RemoteException
  {
    model.editShift(id, dato, startTime, slutTime);
  }

  public void removeShift(int id, Date date) throws RemoteException
  {
    model.removeShift(id, date);
  }

  public StringProperty exceptionTextProperty()
  {
    return exceptionText;
  }
  public StringProperty startTextProperty()
  {
    return startText;
  }
  public StringProperty slutTextProperty()
  {
    return slutText;
  }

  public boolean validateTime(String start, String slut)
  {
    if (start.isBlank() || slut.isBlank())
    {
      exceptionText.set("Starttid eller sluttid er tom");
      return false;
    }
    if (!(start.matches("^(\\d\\d:\\d\\d)") && slut.matches("^(\\d\\d:\\d\\d)")))
    {
      exceptionText.set("Tidsformat er forkert, det rigtige format er HH:MM");
      return false;
    }
    String[] startTimeSplit = start.split(":", 2);
    String[] slutTimeSplit = slut.split(":", 2);
    if (Integer.parseInt(slutTimeSplit[0]) > Integer.parseInt(startTimeSplit[0]) &&
        Integer.parseInt(slutTimeSplit[0]) < 24 && Integer.parseInt(slutTimeSplit[1]) < 60
        && Integer.parseInt(startTimeSplit[0]) < 24 && Integer.parseInt(startTimeSplit[1]) < 60)
    {
      return true;
    }
    else if (Integer.parseInt(slutTimeSplit[0]) == Integer.parseInt(startTimeSplit[0]) &&
        Integer.parseInt(slutTimeSplit[0]) < 24 && Integer.parseInt(slutTimeSplit[1]) < 60
        && Integer.parseInt(startTimeSplit[0]) < 24 && Integer.parseInt(startTimeSplit[1]) < 60)
    {
      if (Integer.parseInt(slutTimeSplit[1]) > Integer.parseInt(startTimeSplit[1]) && Integer.parseInt(slutTimeSplit[0]) < 24 && Integer.parseInt(slutTimeSplit[1]) < 60
          && Integer.parseInt(startTimeSplit[0]) < 24 && Integer.parseInt(startTimeSplit[1]) < 60)
      {
        return true;
      }
      else {
        exceptionText.set("Fejl: Sluttid skal være efter starttid");
        return false;
      }
    }
    return false;
  }

  public boolean validateAll(){
    return validateTime(startText.getValue(), slutText.getValue());
  }
}
