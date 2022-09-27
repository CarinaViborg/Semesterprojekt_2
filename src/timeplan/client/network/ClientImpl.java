package timeplan.client.network;

import timeplan.client.util.PropertyChangeSubject;
import timeplan.shared.interfaces.ClientInterface;
import timeplan.shared.interfaces.ServerInterface;
import timeplan.shared.interfaces.UserInterface;
import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;

public class ClientImpl implements ClientInterface, PropertyChangeSubject
{
  private final ServerInterface server;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  public ClientImpl() throws RemoteException, NotBoundException
  {
    UnicastRemoteObject.exportObject(this, 0);
    Registry registry = LocateRegistry.getRegistry("localhost" , 1099);
    server = (ServerInterface) registry.lookup("BroadcastServer");
    server.registerClient(this);
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

  @Override public void createUser(UserInterface user) throws RemoteException
  {
    server.createUser(user);
  }

  @Override public void loginSuccessAdmin(){
    support.firePropertyChange("Login State", null, "admin");
  }

  @Override public void loginSuccessEmployee()
  {
    support.firePropertyChange("Login State", null, "employee");
  }

  @Override public void editUser(int id, String fornavn, String efternavn, String mail,
      int tlf, String adgangskode) throws RemoteException
  {
    server.editUser(id, fornavn, efternavn, mail, tlf, adgangskode);
  }

  @Override public void deleteEmployee(int id) throws RemoteException
  {
    server.deleteEmployee(id);
  }

  @Override public void empEditUser(String mail, String tlf, String currentUser)
      throws RemoteException
  {
    server.empEditUser(mail, tlf, currentUser);
  }

  @Override public void tjekInd(int id) throws RemoteException
  {
    server.tjekInd(id);
  }

  @Override public void tjekUd(int id) throws RemoteException
  {
    server.tjekUd(id);
  }

  @Override public void createShift(Shift shift) throws RemoteException
  {
    server.createShift(shift);
  }

  @Override public void editShift(Shift shift) throws RemoteException
  {
    server.editShift(shift);
  }

  @Override public void removeShift(int id, Date date) throws RemoteException
  {
    server.removeShift(id, date);
  }

  @Override public void loginFailure()
  {
    support.firePropertyChange("Login State", null, false);
  }

  public void login(String username, String password) throws RemoteException
  {
    server.login(username, password, this);
  }

  public ArrayList<String> getEmployeesAsString() throws RemoteException
  {
    return server.getEmployeesAsString();
  }

  public ArrayList<String> getEmployeeById(String id) throws RemoteException
  {
    return server.getEmployeeById(id);
  }

  public ArrayList<String> getAllShiftsAsString() throws RemoteException
  {
    return server.getAllShiftsAsString();
  }

  @Override public ArrayList<Shift> getAllShiftsByID(int id) throws RemoteException
  {
    return server.getAllShiftsByID(id);
  }

  @Override public ArrayList<Employee> getAllEmployees() throws RemoteException
  {
    return server.getAllEmployees();
  }

  @Override public boolean isCheckedIn(String currentUser) throws RemoteException
  {
    return server.isCheckedIn(currentUser);
  }

  @Override public ArrayList<Timestamp> getAllTimestampsByID(int id)
      throws RemoteException
  {
    return server.getAllTimestampsByID(id);
  }

  @Override public ArrayList<Shift> getAllShifts() throws RemoteException
  {
    return server.getAllShifts();
  }
}
