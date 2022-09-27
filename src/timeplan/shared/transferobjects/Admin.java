package timeplan.shared.transferobjects;

import timeplan.shared.interfaces.UserInterface;

import java.io.Serializable;
import java.util.Date;

public class Admin implements UserInterface, Serializable
{
  String firstName;
  String lastName;
  Date birthDate;
  String mail;
  int phoneNumber;
  String password;

  public Admin(String firstName, String lastName, Date birthDate,
      String mail, int phoneNumber, String password){
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.mail = mail;
    this.phoneNumber = phoneNumber;
    this.password = password;
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
    return true;
  }
}
