package timeplan.client.views.personalplan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PersonalPlanViewController implements ViewController
{
  private PersonalPlanViewModel personalPlanViewModel;
  private ViewHandler viewHandler;

  @FXML
  private Label weekLabel;

  @FXML
  private Label yearLabel;

  @FXML
  private TextField mondayDateText;

  @FXML
  private TextField tuesdayDateText;

  @FXML
  private TextField wednesdayDateText;

  @FXML
  private TextField thursdayDateText;

  @FXML
  private TextField fridayDateText;

  @FXML
  private TextField saturdayDateText;

  @FXML
  private TextField sundayDateText;

  @FXML
  private TextArea mondayTimeText;

  @FXML
  private TextArea tuesdayTimeText;

  @FXML
  private TextArea wednesdayTimeText;

  @FXML
  private TextArea thursdayTimeText;

  @FXML
  private TextArea fridayTimeText;

  @FXML
  private TextArea saturdayTimeText;

  @FXML
  private TextArea sundayTimeText;


  public void init()
  {
    personalPlanViewModel = ViewModelFactory.getViewModelFactory().getPersonalPlanViewModel();
    viewHandler = ViewHandler.getViewHandler();
    weekLabel.setText(String.valueOf(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)));
    populateDates();
    clearShifts();
    populateShifts();
  }

  public void onBackButton(ActionEvent actionEvent){
    viewHandler.openStartPage();
  }

  public void onPrevWeekButton(ActionEvent actionEvent)
  {
    if(Integer.parseInt(weekLabel.getText())>1)
    {
      weekLabel.setText(String.valueOf(Integer.parseInt(weekLabel.getText()) - 1));
    } else if(Integer.parseInt(weekLabel.getText())==1){
      yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())-1));
      weekLabel.setText("52");
    }
    populateDates();
    clearShifts();
    populateShifts();
  }

  public void onNextWeekButton(ActionEvent actionEvent){
    if(Integer.parseInt(weekLabel.getText())<52)
    {
      weekLabel.setText(String.valueOf(Integer.parseInt(weekLabel.getText()) + 1));
    } else if(Integer.parseInt(weekLabel.getText())==52){
      weekLabel.setText("1");
      yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())+1));
    }
    populateDates();
    clearShifts();
    populateShifts();
  }

  public void onPrevYearButton(ActionEvent actionEvent){
    yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())-1));
    populateDates();
    clearShifts();
    populateShifts();
  }

  public void onNextYearButton(ActionEvent actionEvent){
    yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())+1));
    populateDates();
    clearShifts();
    populateShifts();
  }

  public void populateDates(){
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    calendar.setWeekDate(Integer.parseInt(yearLabel.getText()), Integer.parseInt(weekLabel.getText()), Calendar.MONDAY);
    mondayDateText.setText(sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    tuesdayDateText.setText(sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    wednesdayDateText.setText(sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    thursdayDateText.setText(sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    fridayDateText.setText(sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    saturdayDateText.setText(sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    sundayDateText.setText(sdf.format(calendar.getTime()));
  }

  public void clearShifts(){
    mondayTimeText.clear();
    tuesdayTimeText.clear();
    wednesdayTimeText.clear();
    thursdayTimeText.clear();
    fridayTimeText.clear();
    saturdayTimeText.clear();
    sundayTimeText.clear();
  }

  public void populateShifts()
  {
    ArrayList<Shift> shifts = new ArrayList<>();
    ArrayList<Timestamp> timestamps = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    try
    {
      shifts = personalPlanViewModel.getAllShiftsCurrentUser();
      timestamps = personalPlanViewModel.getAllTimestampsCurrentUser();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    for(Shift shift : shifts){
      try
      {
        if(shift.getDate().equals(sdf.parse(mondayDateText.getText())))
        {
          mondayTimeText.setText(
              String.join(" - ", shift.getTimeStart().toString(),
                  shift.getTimeEnd().toString()));
        }  else if(shift.getDate().equals(sdf.parse(tuesdayDateText.getText()))){
          tuesdayTimeText.setText(String.join(" - ", shift.getTimeStart().toString(),
              shift.getTimeEnd().toString()));
        } else if(shift.getDate().equals(sdf.parse(wednesdayDateText.getText()))){
          wednesdayTimeText.setText(String.join(" - ", shift.getTimeStart().toString(),
              shift.getTimeEnd().toString()));
        } else if(shift.getDate().equals(sdf.parse(thursdayDateText.getText()))){
          thursdayTimeText.setText(String.join(" - ", shift.getTimeStart().toString(),
              shift.getTimeEnd().toString()));
        } else if(shift.getDate().equals(sdf.parse(fridayDateText.getText()))){
          fridayTimeText.setText(String.join(" - ", shift.getTimeStart().toString(),
              shift.getTimeEnd().toString()));
        } else if(shift.getDate().equals(sdf.parse(saturdayDateText.getText()))){
          saturdayTimeText.setText(String.join(" - ", shift.getTimeStart().toString(),
              shift.getTimeEnd().toString()));
        } else if(shift.getDate().equals(sdf.parse(sundayDateText.getText()))){
          sundayTimeText.setText(String.join(" - ", shift.getTimeStart().toString(),
              shift.getTimeEnd().toString()));
        }
      }
      catch (ParseException e)
      {
        e.printStackTrace();
      }
    }
    for(Timestamp t : timestamps){
      try{
        if(t.getDate().equals(sdf.parse(mondayDateText.getText())))
        {
          mondayTimeText.setText(
              String.join(" \n ", mondayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        }  else if(t.getDate().equals(sdf.parse(tuesdayDateText.getText()))){
          tuesdayTimeText.setText(String.join(" \n ", tuesdayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        } else if(t.getDate().equals(sdf.parse(wednesdayDateText.getText()))){
          wednesdayTimeText.setText(String.join(" \n ", wednesdayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        } else if(t.getDate().equals(sdf.parse(thursdayDateText.getText()))){
          thursdayTimeText.setText(String.join(" \n ", thursdayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        } else if(t.getDate().equals(sdf.parse(fridayDateText.getText()))){
          fridayTimeText.setText(String.join(" \n ", fridayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        } else if(t.getDate().equals(sdf.parse(saturdayDateText.getText()))){
          saturdayTimeText.setText(String.join(" \n ", saturdayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        } else if(t.getDate().equals(sdf.parse(sundayDateText.getText()))){
          sundayTimeText.setText(String.join(" \n ", sundayTimeText.getText(), "Terminal tid: " + t.getInTime().toString() + " - " + t.getOutTime().toString()));
        }
      } catch(ParseException e){
        e.printStackTrace();
      }
    }
  }
}
