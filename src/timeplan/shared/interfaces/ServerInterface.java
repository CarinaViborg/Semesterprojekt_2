package timeplan.shared.interfaces;

import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

public interface ServerInterface extends Remote
{
  void registerClient(ClientInterface client) throws RemoteException;
  void createUser(UserInterface user) throws RemoteException;
  void login(String username, String password, ClientInterface client) throws RemoteException;
  ArrayList<String> getEmployeesAsString() throws RemoteException;
  ArrayList<String> getEmployeeById(String id) throws RemoteException;
  void editUser(int id, String fornavn, String efternavn, String mail, int tlf, String adgangskode) throws RemoteException;
  void deleteEmployee(int id) throws RemoteException;
  void empEditUser(String mail, String tlf, String currentUser) throws RemoteException;
  void tjekInd(int id) throws RemoteException;
  void tjekUd(int id) throws RemoteException;
  void createShift(Shift shift) throws RemoteException;
  ArrayList<String> getAllShiftsAsString() throws RemoteException;
  void editShift(Shift shift) throws RemoteException;
  void removeShift(int id, Date date) throws RemoteException;
  ArrayList<Shift> getAllShiftsByID(int id) throws RemoteException;
  ArrayList<Employee> getAllEmployees() throws RemoteException;
  boolean isCheckedIn(String currentUser) throws RemoteException;
  ArrayList<Timestamp> getAllTimestampsByID(int id) throws RemoteException;
  ArrayList<Shift> getAllShifts() throws RemoteException;
}
