package timeplan.server.database;

import timeplan.shared.transferobjects.Shift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;

public class Edit_Row
{
  ConnInfo connInfo = new ConnInfo();
  private final String url = connInfo.getUrl();
  private final String user = connInfo.getUser();
  private final String password = connInfo.getPassword();

  public Connection connect() throws SQLException
  {
    return DriverManager.getConnection(url, user, password);
  }

  public void editEmp(int id, String firstname, String lastname, String email,
      int tlf, String password)
  {
    String SQL =
        "UPDATE Sep2.medarbejder SET fornavn = ?, efternavn = ?, email = ? , tlf = ? WHERE medarbejderid = ?";
    String SQLpassword = "UPDATE Sep2.login SET kodeord = ? WHERE medarbejderid = ?";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      pstmt.setString(1, firstname);
      pstmt.setString(2, lastname);
      pstmt.setString(3, email);
      pstmt.setInt(4, tlf);
      pstmt.setInt(5, id);
      pstmt.executeUpdate();
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    if(!(password==null))
    {
      if (!password.isBlank())
      {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQLpassword))
        {
          pstmt.setString(1, password);
          pstmt.setInt(2, id);
          pstmt.executeUpdate();
        }
        catch (SQLException ex)
        {
          ex.printStackTrace();
        }
      }
    }
  }

  public void empEditUser(String mail, String tlf, String currentUser)
  {
    String SQL = "UPDATE Sep2.medarbejder SET email = ?, tlf = ? WHERE medarbejderid = ?";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      pstmt.setString(1, mail);
      pstmt.setInt(2, Integer.parseInt(tlf));
      pstmt.setInt(3, Integer.parseInt(currentUser));
      pstmt.executeUpdate();
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  public void editShift(Shift shift)
  {
    String SQL = "UPDATE Sep2.vagt SET dato = ?, starttid = ?, sluttid = ? where medarbejderid = ?";
    try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL))
    {
      pstmt.setDate(1, shift.getDate());
      pstmt.setTime(2, shift.getTimeStart());
      pstmt.setTime(3, shift.getTimeEnd());
      pstmt.setInt(4, shift.getId());
      pstmt.executeUpdate();
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  public void tjekud( int id) {
    String SQL = "UPDATE Sep2.tidsregistrering SET tjekudtid = ? WHERE medarbejderid = ? and tjekudtid is null";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL)) {
      pstmt.setObject(1, LocalTime.now());
      pstmt.setInt(2, id);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
