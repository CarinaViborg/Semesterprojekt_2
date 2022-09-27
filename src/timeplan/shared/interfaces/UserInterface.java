package timeplan.shared.interfaces;

import java.util.Date;

public interface UserInterface
{
  String getFirstName();
  String getLastName();
  Date getBirthDate();
  String getMail();
  int getPhoneNumber();
  String getPassword();
  boolean getAdmin();
}
