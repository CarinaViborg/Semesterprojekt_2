package timeplan.client.views.createshift;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;
import timeplan.shared.transferobjects.Shift;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;

public class CreateShiftViewModel
{
  private final TimeplanModel model;
  LocalDateTime now = LocalDateTime.now();
  private final StringProperty startText = new SimpleStringProperty();
  private final StringProperty slutText = new SimpleStringProperty();
  private final StringProperty exceptionText = new SimpleStringProperty();
  private final ObjectProperty dateProp = new SimpleObjectProperty();



  public CreateShiftViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
  }

  public StringProperty startTextProperty()
  {
    return startText;
  }

  public StringProperty slutTextProperty()
  {
    return slutText;
  }

  public StringProperty exceptionTextProperty()
  {
    return exceptionText;
  }

  public ObjectProperty datePropProperty()
  {
    return dateProp;
  }

  public ArrayList<String> getEmployees() throws RemoteException
  {
    return model.getEmployeesAsString();
  }

  public void createShift(int idFromComboBox, Date date, Time startTime, Time slutTime)
      throws RemoteException
  {
    model.createShift(idFromComboBox, date, startTime, slutTime);
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
      return Integer.parseInt(slutTimeSplit[1]) > Integer.parseInt(
          startTimeSplit[1]) && Integer.parseInt(slutTimeSplit[0]) < 24
          && Integer.parseInt(slutTimeSplit[1]) < 60
          && Integer.parseInt(startTimeSplit[0]) < 24
          && Integer.parseInt(startTimeSplit[1]) < 60;
    }
    exceptionText.set("Fejl: Sluttid skal være efter starttid");
    return false;
  }

  public boolean dateValidation(LocalDate date) {
    if (date==null)
    {
      exceptionText.set("Dato er ikke valgt");
      return false;
    }
    ArrayList<Shift> shifts = new ArrayList<>();
    try
    {
      shifts = model.getAllShifts();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    for(Shift s:shifts){
      if(s.getDate().toLocalDate().equals(date)){
        exceptionText.set("Der er allerede en vagt på den dato for den medarbejder");
        return false;
      }
    }
    if (date.isAfter(ChronoLocalDate.from(now))) {
      return true;
    }
    exceptionText.set("Dato er før dags dato");
    return false;
  }

  public boolean validateAll ()
  {
    return dateValidation((LocalDate) dateProp.getValue()) && validateTime(
        startText.getValue(), slutText.getValue());
  }
}
