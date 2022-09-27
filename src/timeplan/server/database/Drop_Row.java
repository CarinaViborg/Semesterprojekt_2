package timeplan.server.database;

import java.sql.*;

public class Drop_Row
{
  ConnInfo connInfo =new ConnInfo();
  private final String url = connInfo.getUrl();
  private final String user = connInfo.getUser();
  private final String password = connInfo.getPassword();


  public Connection connect() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }


  public void deleteEmployee(int medarbejderid) {
    String SQLlogin = "DELETE FROM Sep2.login WHERE medarbejderid = ?";

    String SQLvagt = "DELETE FROM Sep2.vagt WHERE medarbejderid = ?";

    String SQLtid= "DELETE FROM Sep2.tidsregistrering where medarbejderid = ?";

    String SQL = "DELETE FROM Sep2.medarbejder WHERE medarbejderid = ?";

    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQLvagt)) {
      pstmt.setInt(1, medarbejderid);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    try (Connection conn = connect();
      PreparedStatement pstmt = conn.prepareStatement(SQLlogin)) {
      pstmt.setInt(1, medarbejderid);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQLtid)) {
      pstmt.setInt(1, medarbejderid);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL)) {
      pstmt.setInt(1, medarbejderid);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public void removeShift(int id, Date date)
  {
    String SQL = "DELETE FROM Sep2.vagt WHERE medarbejderid = ? and dato = ?";
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL)) {
      pstmt.setInt(1, id);
      pstmt.setDate(2, date);
      pstmt.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}