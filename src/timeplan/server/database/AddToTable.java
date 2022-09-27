package timeplan.server.database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class AddToTable
{
    ConnInfo connInfo =new ConnInfo();
    private final String url = connInfo.getUrl();
    private final String user = connInfo.getUser();
    private final String password = connInfo.getPassword();


    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int addEmp(String firstName, String lastName,
                      Date birthDate, String mail, int phoneNumber, Boolean isAdmin) {
        String SQL = "insert into Sep2.medarbejder (fornavn, efternavn, dob, email, tlf, leder) values  (?,?,?,?,?,?)";
        int affectedrows = 0;

        try (Connection conn = connect())
        {
            try (PreparedStatement pstmt = conn.prepareStatement(SQL))
            {
                pstmt.setString (1, firstName);
                pstmt.setString (2, lastName);
                pstmt.setObject (3, birthDate);
                pstmt.setString (4, mail);
                pstmt.setInt    (5, phoneNumber);
                pstmt.setBoolean(6, isAdmin);
                affectedrows = pstmt.executeUpdate();

            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return affectedrows;
    }

    public int addLogin( String password, int employeeID) {
        String SQL = "INSERT INTO Sep2.login (kodeord, medarbejderid) values (?,?)";
        int affectedrows = 0;

        try (Connection conn = connect()) {
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setString(1, password);
                pstmt.setInt(2,employeeID);
                affectedrows = pstmt.executeUpdate();

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

    public void opretvagt(int medarbejderid, java.sql.Date dato,
        Time startTime, Time slutTime) {
        String SQL = "insert into Sep2.vagt (dato, starttid, sluttid, medarbejderid) values (?,?,?,?)";
        try (Connection conn = connect())
        {
            try (PreparedStatement pstmt = conn.prepareStatement(SQL))
            {
                pstmt.setObject(1, dato);
                pstmt.setTime(2, startTime);
                pstmt.setTime(3, slutTime);
                pstmt.setInt(4,medarbejderid);
                pstmt.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void tjekind (int medarbejderid) {
        String SQL = "INSERT INTO Sep2.tidsregistrering (medarbejderid, dato, tjekindtid ,tjekudtid) VALUES  (?,?,?,?)";
        System.out.println(""+ SQL);
        try (Connection conn = connect()) {
            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setInt(1, medarbejderid);
                pstmt.setObject(2, LocalDate.now());
                pstmt.setObject(3, LocalTime.now());
                pstmt.setTime(4, null);
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
