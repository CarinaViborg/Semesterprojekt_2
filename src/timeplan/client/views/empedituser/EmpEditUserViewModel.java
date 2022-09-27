package timeplan.client.views.empedituser;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EmpEditUserViewModel
{
  private final TimeplanModel model;

  private final StringProperty tlfText = new SimpleStringProperty();

  private final StringProperty mailText = new SimpleStringProperty();

  private final StringProperty exceptionLabel = new SimpleStringProperty();


  public EmpEditUserViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
  }


  public void empEditUser(String mail, String tlf) throws RemoteException
  {
    model.empEditUser(mail, tlf);
  }

  public ArrayList<String> getCurrentUser() throws RemoteException
  {
    return model.getCurrentEmployee();
  }

  public StringProperty tlfTextProperty()
{
  return tlfText;
}

  public StringProperty mailTextProperty()
  {
    return mailText;
  }

  public StringProperty exceptionLabelProperty(){return exceptionLabel;}

  public boolean phoneVali(String phone) {
    try {
      int numberInt = Integer.parseInt(phone);

      if (phone.isEmpty() ) {
        exceptionLabel.set("Fejl i telefonnummer: Feltet er tomt");
        return false;
      }
      else if (numberInt < 99999999 && numberInt > 10000000)
        return true;
      else {
        exceptionLabel.set("Fejl i telefonnummer: nummer er ugyldigt");
        return false;
      }
    } catch (NumberFormatException e) {
      exceptionLabel.set("Fejl i telefonnummer: Kun numre er tilladt");
      return false;
    }
  }

  public boolean emailValid (String mail) {
    if (mail.isEmpty()) {
      exceptionLabel.set("Fejl i email : Ingen emailadresse angivet");
      return false;
    }
    String  emailRegex = "^[a-zA-Z0-9ÆØÅæøå]+(?:\\." +"[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (pattern.matcher(mail).matches()) {
      return true;
    }
    else
      exceptionLabel.set("Fejl i email: emailadressen er ikke gyldig");
      return false;
  }

  public boolean validateAll ()
  {
    return emailValid(mailText.getValue()) && phoneVali(tlfText.getValue());
  }
}
