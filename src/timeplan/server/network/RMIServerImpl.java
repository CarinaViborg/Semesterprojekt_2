package timeplan.server.network;

import timeplan.server.database.DatabaseMain;
import timeplan.shared.interfaces.ClientInterface;
import timeplan.shared.interfaces.ServerInterface;
import timeplan.shared.interfaces.UserInterface;
import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RMIServerImpl implements ServerInterface
{
  private final List<ClientInterface> clientsList;
  private final DatabaseMain databaseMain;

  public RMIServerImpl() throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    clientsList = new ArrayList<>();
    databaseMain = new DatabaseMain();
  }

  @Override public void registerClient(ClientInterface client) throws RemoteException
  {
    clientsList.add(client);
    System.out.println("Number of clients " + clientsList.size());
  }

  @Override public void createUser(UserInterface user) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.addEmployee(user);
    databaseMain.releaseWriteAccess();
  }

  public void login(String username, String password,
      ClientInterface client) throws RemoteException
  {
    databaseMain.acquireReadAccess();
    System.out.println("server login");
    String loginStatus = databaseMain.login(username, password);
    if(loginStatus == null)  {
      System.out.println("Login failure");
      client.loginFailure();
    }else if(loginStatus.equals("employee")){
      client.loginSuccessEmployee();
      System.out.println("Username and password match, logged in as employee");
    } else if(loginStatus.equals("admin"))
    {
      client.loginSuccessAdmin();
      System.out.println("Username and password match, logged in as admin");
    }
    databaseMain.releaseReadAccess();
  }

  public ArrayList<String> getEmployeesAsString(){
    databaseMain.acquireReadAccess();
    ArrayList<String> strings = databaseMain.getEmployeesAsString();
    databaseMain.releaseReadAccess();
    return strings;
  }

  @Override public ArrayList<String> getEmployeeById(String id)
  {
    databaseMain.acquireReadAccess();
    ArrayList<String> strings = databaseMain.getEmployeeById(id);
    databaseMain.releaseReadAccess();
    return strings;
  }

  @Override public void editUser(int id, String fornavn, String efternavn, String mail,
      int tlf, String adgangskode)
  {
    databaseMain.acquireWriteAccess();
    databaseMain.editEmp(id, fornavn, efternavn, mail, tlf, adgangskode);
    databaseMain.releaseWriteAccess();
  }

  @Override public void deleteEmployee(int id) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.deleteEmployee(id);
    databaseMain.releaseWriteAccess();
  }

  @Override public void empEditUser(String mail, String tlf, String currentUser)
  {
    databaseMain.acquireWriteAccess();
    databaseMain.empEditUser(mail, tlf, currentUser);
    databaseMain.releaseWriteAccess();
  }

  @Override public void tjekInd(int id) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.tjekInd(id);
    databaseMain.releaseWriteAccess();
  }

  @Override public void tjekUd(int id) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.tjekUd(id);
    databaseMain.releaseWriteAccess();
  }

  @Override public void createShift(Shift shift) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.createShift(shift);
    databaseMain.releaseWriteAccess();
  }

  @Override public ArrayList<String> getAllShiftsAsString() throws RemoteException
  {
    databaseMain.acquireReadAccess();
    ArrayList<String> strings = databaseMain.getAllShiftsAsString();
    databaseMain.releaseReadAccess();
    return strings;
  }

  @Override public void editShift(Shift shift) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.editShift(shift);
    databaseMain.releaseWriteAccess();
  }

  @Override public void removeShift(int id, Date date) throws RemoteException
  {
    databaseMain.acquireWriteAccess();
    databaseMain.removeShift(id, date);
    databaseMain.releaseWriteAccess();
  }

  @Override public ArrayList<Shift> getAllShiftsByID(int id) throws RemoteException
  {
    databaseMain.acquireReadAccess();
    ArrayList<Shift> shifts = databaseMain.getAllShiftsByID(id);
    databaseMain.releaseReadAccess();
    return shifts;
  }

  @Override public ArrayList<Employee> getAllEmployees() throws RemoteException
  {
    databaseMain.acquireReadAccess();
    ArrayList<Employee> employees = databaseMain.getAllEmployees();
    databaseMain.releaseReadAccess();
    return employees;
  }

  @Override public boolean isCheckedIn(String currentUser) throws RemoteException
  {
    databaseMain.acquireReadAccess();
    boolean bool = databaseMain.isCheckedIn(currentUser);
    databaseMain.releaseReadAccess();
    return bool;
  }

  public ArrayList<Timestamp> getAllTimestampsByID(int id)
      throws RemoteException
  {
    databaseMain.acquireReadAccess();
    ArrayList<Timestamp> timestamps = databaseMain.getAllTimestampsByID(id);
    databaseMain.releaseReadAccess();
    return timestamps;
  }

  @Override public ArrayList<Shift> getAllShifts() throws RemoteException
  {
    databaseMain.acquireReadAccess();
    ArrayList<Shift> shifts = databaseMain.getAllShifts();
    databaseMain.releaseReadAccess();
    return shifts;
  }
}
