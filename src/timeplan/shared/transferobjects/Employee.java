package timeplan.shared.transferobjects;

import timeplan.shared.interfaces.UserInterface;

import java.io.Serializable;
import java.util.Date;

public class Employee implements UserInterface, Serializable
{
  private final String firstName;
  private final String lastName;
  private final Date birthDate;
  private final String mail;
  private final int phoneNumber;
  private final String password;
  private int employeeID;

  public Employee(String firstName, String lastName, Date birthDate,
      String mail, int phoneNumber, String password){
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.mail = mail;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public Employee(int employeeID, String firstName, String lastName, Date birthDate,
      String mail, int phoneNumber, String password){
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.mail = mail;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public int getEmployeeID()
  {
    return employeeID;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public Date getBirthDate()
  {
    return birthDate;
  }

  public String getMail()
  {
    return mail;
  }

  public int getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getPassword()
  {
    return password;
  }

  public boolean getAdmin()
  {
    return false;
  }
}
