package timeplan.client.views.departmentplan;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import timeplan.client.core.ViewHandler;
import timeplan.client.core.ViewModelFactory;
import timeplan.client.util.ViewController;
import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DepartmentPlanViewController implements ViewController
{
  private DepartmentPlanViewModel departmentPlanViewModel;
  private ViewHandler viewHandler;

  @FXML
  private GridPane employeeGridPane;

  @FXML
  private Label mondayLabel;

  @FXML
  private Label tuesdayLabel;

  @FXML
  private Label wednesdayLabel;

  @FXML
  private Label thursdayLabel;

  @FXML
  private Label fridayLabel;

  @FXML
  private Label saturdayLabel;

  @FXML
  private Label sundayLabel;

  @FXML
  private Label medarbejderLabel;

  @FXML
  private Label yearLabel;

  @FXML
  private Label weekLabel;

  private String mondayDate, tuesdayDate, wednesdayDate, thursdayDate, fridayDate, saturdayDate, sundayDate;

  ArrayList<Label> topLabels;

  public void init()
  {
    departmentPlanViewModel = ViewModelFactory.getViewModelFactory().getDepartmentPlanViewModel();
    viewHandler = ViewHandler.getViewHandler();
    weekLabel.setText(String.valueOf(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)));
    populateDates();
    populateEmployees();
    topLabels = new ArrayList<>();
    topLabels.add(mondayLabel);
    topLabels.add(tuesdayLabel);
    topLabels.add(wednesdayLabel);
    topLabels.add(thursdayLabel);
    topLabels.add(fridayLabel);
    topLabels.add(saturdayLabel);
    topLabels.add(sundayLabel);
  }

  public void onPrevWeekButton (ActionEvent actionEvent)
  {
    if(Integer.parseInt(weekLabel.getText())>1)
    {
      weekLabel.setText(String.valueOf(Integer.parseInt(weekLabel.getText()) - 1));
    } else if(Integer.parseInt(weekLabel.getText())==1){
      yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())-1));
      weekLabel.setText("52");
    }
    clearGridView();
    populateDates();
    populateEmployees();

  }

  public void onNextWeekButton (ActionEvent actionEvent)
  {
    if(Integer.parseInt(weekLabel.getText())<52)
    {
      weekLabel.setText(String.valueOf(Integer.parseInt(weekLabel.getText()) + 1));
    } else if(Integer.parseInt(weekLabel.getText())==52){
      weekLabel.setText("1");
      yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())+1));
    }
    clearGridView();
    populateDates();
    populateEmployees();
  }

  public void onPrevYearButton(ActionEvent actionEvent){
    yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())-1));
    clearGridView();
    populateDates();
    populateEmployees();
  }

  public void onNextYearButton(ActionEvent actionEvent){
    yearLabel.setText(String.valueOf(Integer.parseInt(yearLabel.getText())+1));
    clearGridView();
    populateDates();
    populateEmployees();
  }

  public void onBackButton(ActionEvent actionEvent){
    Platform.runLater(() -> viewHandler.openStartPage());
  }

  public void onAdminBackButton(ActionEvent actionEvent){
    Platform.runLater(() -> viewHandler.openAdminStartPage());
  }

  public void populateDates(){
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    calendar.setWeekDate(Integer.parseInt(yearLabel.getText()), Integer.parseInt(weekLabel.getText()), Calendar.MONDAY);
    mondayDate = sdf.format(calendar.getTime());
    mondayLabel.setText("Mandag " + sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    tuesdayDate = sdf.format(calendar.getTime());
    tuesdayLabel.setText("Tirsdag " + sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    wednesdayDate = sdf.format(calendar.getTime());
    wednesdayLabel.setText("Onsdag " + sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    thursdayDate = sdf.format(calendar.getTime());
    thursdayLabel.setText("Torsdag " + sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    fridayDate = sdf.format(calendar.getTime());
    fridayLabel.setText("Fredag " + sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    saturdayDate = sdf.format(calendar.getTime());
    saturdayLabel.setText("Lørdag " + sdf.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 1);
    sundayDate = sdf.format(calendar.getTime());
    sundayLabel.setText("Søndag " + sdf.format(calendar.getTime()));
  }

  public void clearGridView(){
    employeeGridPane.setGridLinesVisible(false);
    this.employeeGridPane.getChildren().retainAll();
    this.employeeGridPane.addRow(0, medarbejderLabel, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel, sundayLabel);
    employeeGridPane.setGridLinesVisible(true);
  }

  public void populateEmployees(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<Employee> employees = new ArrayList<>();
    try
    {
      employees = departmentPlanViewModel.getAllEmployees();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    ArrayList<Shift> shifts = null;
    try
    {
      shifts = departmentPlanViewModel.getAllShifts();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    for (int i = 0; i < employees.size(); i++)
    {
      Label empLabel = new Label();
      Label monday = new Label();
      Label tuesday = new Label();
      Label wednesday = new Label();
      Label thursday = new Label();
      Label friday = new Label();
      Label saturday = new Label();
      Label sunday = new Label();

      ArrayList<Shift> shiftsForEmp = new ArrayList<>();
      assert shifts != null;
      for(Shift shift: shifts){
        if(shift.getId()==employees.get(i).getEmployeeID()){
          shiftsForEmp.add(shift);
        }
      }
      for(Shift shift:shiftsForEmp){
        try
        {
          if(shift.getDate().equals(sdf.parse(mondayDate)))
          {
            monday.setText(
                String.join(" - ", shift.getTimeStart().toString(),
                    shift.getTimeEnd().toString()));
          }  else if(shift.getDate().equals(sdf.parse(tuesdayDate))){
            tuesday.setText(String.join(" - ", shift.getTimeStart().toString(),
                shift.getTimeEnd().toString()));
          } else if(shift.getDate().equals(sdf.parse(wednesdayDate))){
            wednesday.setText(String.join(" - ", shift.getTimeStart().toString(),
                shift.getTimeEnd().toString()));
          } else if(shift.getDate().equals(sdf.parse(thursdayDate))){
            thursday.setText(String.join(" - ", shift.getTimeStart().toString(),
                shift.getTimeEnd().toString()));
          } else if(shift.getDate().equals(sdf.parse(fridayDate))){
            friday.setText(String.join(" - ", shift.getTimeStart().toString(),
                shift.getTimeEnd().toString()));
          } else if(shift.getDate().equals(sdf.parse(saturdayDate))){
            saturday.setText(String.join(" - ", shift.getTimeStart().toString(),
                shift.getTimeEnd().toString()));
          } else if(shift.getDate().equals(sdf.parse(sundayDate))){
            sunday.setText(String.join(" - ", shift.getTimeStart().toString(),
                shift.getTimeEnd().toString()));
          }
        }
        catch (ParseException e)
        {
          e.printStackTrace();
        }
      }
      empLabel.setText(employees.get(i).getEmployeeID() + " " + employees.get(i).getFirstName() + " " + employees.get(i).getLastName());
      employeeGridPane.addRow(i+1, empLabel, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
      shiftsForEmp.clear();
    }
  }

  public void onOpretVagtButton(){
    Platform.runLater(() ->viewHandler.openCreateShift());
  }

  public void onRedigerVagtButton(){
    Platform.runLater(() -> viewHandler.openEditShift());
  }
}
