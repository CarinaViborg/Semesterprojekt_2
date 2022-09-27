package timeplan.client.model;

import timeplan.client.network.ClientImpl;
import timeplan.client.util.PropertyChangeSubject;
import timeplan.shared.interfaces.ClientInterface;
import timeplan.shared.transferobjects.Admin;
import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class TimeplanModel implements PropertyChangeSubject
{
  private final ClientInterface client;
  private final PropertyChangeSupport support;
  private String currentUser = null;

  public TimeplanModel(ClientImpl client)
  {
    this.client = client;
    support = new PropertyChangeSupport(this);
    client.addListener("Login State", this::handleLogin);
  }

  public void createUser(String firstName, String lastName, Date birthDate,
      String mail, int phoneNumber, String password, Boolean isAdmin)
      throws RemoteException
  {
    if (isAdmin)
    {
      Admin admin = new Admin(firstName, lastName, birthDate, mail, phoneNumber,
          password);
      client.createUser(admin);
    }
    else
    {
      Employee employee = new Employee(firstName, lastName, birthDate, mail,
          phoneNumber, password);
      client.createUser(employee);
    }
  }

  @Override public void addListener(String eventName,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(eventName, listener);
  }

  @Override public void removeListener(String eventName,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(eventName, listener);
  }

  public void login(String username, String password) throws RemoteException
  {
    currentUser = username;
    client.login(username, password);
  }

  private void handleLogin(PropertyChangeEvent propertyChangeEvent)
  {
    support.firePropertyChange(propertyChangeEvent.getPropertyName(),
        propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
  }

  public ArrayList<String> getEmployeesAsString() throws RemoteException
  {
    return client.getEmployeesAsString();
  }

  public ArrayList<String> getCurrentEmployee() throws RemoteException
  {
    return client.getEmployeeById(currentUser);
  }

  public ArrayList<String> getEmployeeById(String id) throws RemoteException
  {
    return client.getEmployeeById(id);
  }

  public void editUser(int id, String fornavn, String efternavn, String mail,
      int tlf, String adgangskode)
      throws RemoteException
  {
    client.editUser(id, fornavn, efternavn, mail, tlf, adgangskode);
  }

  public boolean deleteEmployee(int id) throws RemoteException
  {
    if(Integer.parseInt(currentUser)==id){
      support.firePropertyChange("Deleted current user", null, null);
      return false;
    } else client.deleteEmployee(id); return true;
  }

  public void empEditUser(String mail, String tlf) throws RemoteException
  {
    client.empEditUser(mail, tlf, currentUser);
  }

  public void tjekInd() throws RemoteException
  {
    client.tjekInd(Integer.parseInt(currentUser));
  }

  public void tjekUd() throws RemoteException
  {
    client.tjekUd(Integer.parseInt(currentUser));
  }

  public void createShift(int idFromComboBox, java.sql.Date date, Time startTime, Time slutTime)
      throws RemoteException
  {
    Shift shift = new Shift(idFromComboBox, date, startTime, slutTime);
    client.createShift(shift);
  }

  public ArrayList<String> getAllShiftsAsString() throws RemoteException
  {
    return client.getAllShiftsAsString();
  }

  public ArrayList<Shift> getAllShiftsCurrentUser() throws RemoteException
  {
    return client.getAllShiftsByID(Integer.parseInt(currentUser));
  }

  public void editShift(int id, java.sql.Date date, Time startText, Time slutText)
      throws RemoteException
  {
    Shift shift = new Shift(id, date, startText, slutText);
    client.editShift(shift);
  }

  public void removeShift(int id, java.sql.Date date) throws RemoteException
  {
    client.removeShift(id, date);
  }

  public ArrayList<Employee> getAllEmployees() throws RemoteException
  {
    return client.getAllEmployees();
  }

  public boolean isCheckedIn() throws RemoteException
  {
    return client.isCheckedIn(currentUser);
  }

  public ArrayList<Timestamp> getAllTimestampsCurrentUser()
      throws RemoteException
  {
    return client.getAllTimestampsByID(Integer.parseInt(currentUser));
  }

  public ArrayList<Shift> getAllShifts() throws RemoteException
  {
    return client.getAllShifts();
  }
}
