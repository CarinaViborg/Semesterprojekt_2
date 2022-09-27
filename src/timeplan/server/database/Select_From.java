package timeplan.server.database;

import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.sql.*;
import java.util.ArrayList;

public class Select_From
{
  ConnInfo connInfo = new ConnInfo();
  private final String url = connInfo.getUrl();
  private final String user = connInfo.getUser();
  private final String password = connInfo.getPassword();

  public Connection connect() throws SQLException
  {
    return DriverManager.getConnection(url, user, password);
  }

  public ArrayList<String> findEmpByID(int medarbejderid)
  {
    String SQL =
        "select * from Sep2.medarbejder WHERE medarbejderid = " + medarbejderid;
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      ArrayList<String> employee = new ArrayList<>();
      rs.next();
      String medarbejderID = "" + rs.getInt("medarbejderid");
      String tlf = "" + rs.getInt("tlf");
      employee.add(medarbejderID);
      employee.add(rs.getString("fornavn"));
      employee.add(rs.getString("efternavn"));
      employee.add(rs.getString("email"));
      employee.add(tlf);
      return employee;
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
    }
    return null;
  }

  public ArrayList<String> getEmployeesAsString()
  {
    ArrayList<String> employeesString = new ArrayList<>();
    String SQL = "select * from Sep2.medarbejder OrDeR bY medarbejderid";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        String string = String.join(" - ", rs.getString("medarbejderid"),
            rs.getString("fornavn"));
        string = String.join(" ", string, rs.getString("efternavn"));
        employeesString.add(string);
      }
      return employeesString;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    return null;
  }

  public ArrayList<String> getAllShiftsAsString()
  {
    ArrayList<String> shiftsString = new ArrayList<>();
    String SQL = "select * from Sep2.vagt ORDER BY dato";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        String string = String.join(" - ", rs.getString("medarbejderid"),
            rs.getDate("dato").toString());
        string = String.join(" ", string, rs.getString("starttid"));
        shiftsString.add(string);
      }
      return shiftsString;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public int lastEmp()
  {
    String SQL = "select medarbejderid from Sep2.medarbejder order by medarbejderid desc limit 1 ";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      return rs.getInt("medarbejderid");
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    return 0;
  }

  public String getPassword(String medarbejderID)
  {
    String SQL =
        "select kodeord from Sep2.login WHERE medarbejderid = " + medarbejderID;
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      return rs.getString("kodeord");
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
    }
    return null;
  }

  public boolean getWorkerStatus(String medarbejderID)
  {
    String SQL = "select leder from Sep2.medarbejder WHERE medarbejderid = "
        + medarbejderID;
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      return rs.getBoolean("leder");
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    return false;
  }

  public ArrayList<Employee> getAllEmployees()
  {
    ArrayList<Employee> employees = new ArrayList<>();
    String SQL = "select * from Sep2.medarbejder order by medarbejderid";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        employees.add(new Employee(rs.getInt("medarbejderid"), rs.getString("fornavn"),
            rs.getString("efternavn"), rs.getDate("dob"), rs.getString("email"),
            rs.getInt("tlf"), null));
      }
      return employees;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    return null;
  }

  public ArrayList<Shift> getAllShiftsByID(int id)
  {
    ArrayList<Shift> shifts = new ArrayList<>();
    String SQL = "select * from Sep2.vagt where medarbejderid = " + id;
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        shifts.add(new Shift(Integer.parseInt(rs.getString("medarbejderid")),
            rs.getDate("dato"), rs.getTime("starttid"), rs.getTime("sluttid")));
      }
      return shifts;
    }
    catch (SQLException throwables)
    {
      throwables.printStackTrace();
    }
    return null;
  }

  public boolean isCheckedIn(String currentUser)
  {
    String SQL = "select * from Sep2.tidsregistrering where medarbejderid = " + Integer.parseInt(currentUser);
    try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      ResultSet rs = pstmt.executeQuery();
      while(rs.next())
      {
        if(rs.getTime("tjekindtid")!=null && rs.getTime("tjekudtid")==null){
          return true;
        }
      }
    }
    catch(SQLException throwables){
      throwables.printStackTrace();
    }
    return false;
  }

  public ArrayList<Timestamp> getAllTimestampsByID(int id)
  {
    ArrayList<Timestamp> timestamps = new ArrayList<>();
    String SQL = "select * from Sep2.tidsregistrering where medarbejderid = " + id + " AND tjekudtid is not null";
    try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)){
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        timestamps.add(new Timestamp(id, rs.getDate("dato"), rs.getTime("tjekindtid"), rs.getTime("tjekudtid")));
      }
    } catch(SQLException e){
      e.printStackTrace();
    }
    return timestamps;
  }

  public ArrayList<Shift> getAllShifts()
  {
    ArrayList<Shift> shifts = new ArrayList<>();
    String SQL = "select * from Sep2.vagt";
    try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)){
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
       shifts.add(new Shift(rs.getInt("medarbejderid"), rs.getDate("dato"), rs.getTime("starttid"), rs.getTime("sluttid")));
      }
    } catch(SQLException e){
      e.printStackTrace();
    }
    return shifts;
  }
}