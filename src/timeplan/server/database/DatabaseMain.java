package timeplan.server.database;

import timeplan.shared.interfaces.UserInterface;
import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class DatabaseMain
{
  private final Queue<Thread> queue;
  private int readers;
  private int writers;
  private final AddToTable addToTable;
  private final Drop_Row dropRow;
  private final Edit_Row editRow;
  private final Select_From selectFrom;

  public DatabaseMain()
  {
    addToTable = new AddToTable();
    dropRow = new Drop_Row();
    editRow = new Edit_Row();
    selectFrom = new Select_From();
    queue = new ArrayDeque<>();
    readers = 0;
    writers = 0;
  }

  public synchronized void acquireReadAccess() {
    queue.offer(Thread.currentThread());
    while(queue.peek() != Thread.currentThread()){
      try
      {
        wait();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    while(writers > 0){
      try
      {
        wait();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    readers++;
    queue.remove();
    System.out.println("Acquired read access");
    notify();
  }

  public synchronized void acquireWriteAccess() {
    queue.offer(Thread.currentThread());
    while(queue.peek() != Thread.currentThread()){
      try
      {
        wait();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    while(writers > 0){
      try
      {
        wait();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    writers++;
    queue.remove();
    System.out.println("Acquired write access");
    notifyAll();
  }

  public synchronized void releaseReadAccess() {
    readers--;
    if(readers==0){
      notifyAll();
    }
    System.out.println("Released read access");
  }

  public synchronized void releaseWriteAccess() {
    writers--;
    if(writers==0){
      notifyAll();
    }
    System.out.println("Released write access");
  }


  public void addEmployee(UserInterface user)
  {
    System.out.println("User created, rows affected: " + addToTable.addEmp(user.getFirstName(),
        user.getLastName(), user.getBirthDate(), user.getMail(),
        user.getPhoneNumber(), user.getAdmin()));
    System.out.println("Password added, rows affected: " + addToTable.addLogin(
        user.getPassword(), selectFrom.lastEmp()));
  }

  public String login(String medarbejderID, String password)
  {
    if(password.equals(selectFrom.getPassword(medarbejderID))){
      if(selectFrom.getWorkerStatus(medarbejderID)){
        return "admin";
      }
      return "employee";
    }
    return null;
  }

  public ArrayList<String> getEmployeesAsString(){
    return selectFrom.getEmployeesAsString();
  }

  public ArrayList<String> getEmployeeById(String id)
  {
    return selectFrom.findEmpByID(Integer.parseInt(id));
  }

  public void editEmp(int id, String fornavn, String efternavn, String mail, int tlf,
      String adgangskode)
  {
    editRow.editEmp(id, fornavn, efternavn, mail, tlf, adgangskode);
  }

  public void deleteEmployee(int id)
  {
    dropRow.deleteEmployee(id);
  }

  public void empEditUser(String mail, String tlf, String currentUser)
  {
    editRow.empEditUser(mail, tlf, currentUser);
  }

  public void tjekInd(int id)
  {
    addToTable.tjekind(id);
  }

  public void tjekUd(int id){
    editRow.tjekud(id);
  }

  public void createShift(Shift shift)
  {
    addToTable.opretvagt(shift.getId(), shift.getDate(), shift.getTimeStart(), shift.getTimeEnd());
  }

  public ArrayList<String> getAllShiftsAsString()
  {
    return selectFrom.getAllShiftsAsString();
  }

  public void editShift(Shift shift)
  {
    editRow.editShift(shift);
  }

  public void removeShift(int id, Date date)
  {
    dropRow.removeShift(id, date);
  }

  public ArrayList<Shift> getAllShiftsByID(int id)
  {
    return selectFrom.getAllShiftsByID(id);
  }

  public ArrayList<Employee> getAllEmployees()
  {
    return selectFrom.getAllEmployees();
  }

  public boolean isCheckedIn(String currentUser)
  {
    return selectFrom.isCheckedIn(currentUser);
  }

  public ArrayList<Timestamp> getAllTimestampsByID(int id)
  {
    return selectFrom.getAllTimestampsByID(id);
  }

  public ArrayList<Shift> getAllShifts()
  {
    return selectFrom.getAllShifts();
  }
}
