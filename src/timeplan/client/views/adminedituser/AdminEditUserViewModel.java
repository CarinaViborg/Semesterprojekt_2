package timeplan.client.views.adminedituser;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdminEditUserViewModel
{
  private final TimeplanModel model;

  private final StringProperty exceptionLabel = new SimpleStringProperty();

  private final StringProperty fornavnLabel = new SimpleStringProperty();
  private final StringProperty efternavnLabel = new SimpleStringProperty();

  private final StringProperty mailLabel = new SimpleStringProperty();

  private final StringProperty kodeLabel = new SimpleStringProperty();

  private final StringProperty phoneLabel = new SimpleStringProperty();

  public AdminEditUserViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
    model.addListener("Deleted current user", this::deleteCurrentUser);
  }

  private void deleteCurrentUser(PropertyChangeEvent propertyChangeEvent)
  {
    exceptionLabel.set("Du kan ikke slette den nuværende bruger!");
  }

  public ArrayList<String> getEmployees() throws RemoteException
  {
    return model.getEmployeesAsString();
  }

  public ArrayList<String> getEmployeeByID(String id) throws RemoteException
  {
    return model.getEmployeeById(id);
  }

  public void editUser(int id, String fornavn, String efternavn, String mail,
      int tlf, String adgangskode)
      throws RemoteException
  {
    model.editUser(id, fornavn, efternavn, mail, tlf, adgangskode);
  }

  public boolean deleteEmployee(int id) throws RemoteException
  {
    return model.deleteEmployee(id);
  }

  public boolean validateFornavn (String name)
  {
    if (name.isEmpty())
    {
      return false;
    }
    char[] chars = name.toCharArray();

    for (char c : chars) {
      if(!(Character.isLetter(c) || Character.isWhitespace(c))) {
        exceptionLabel.set("Fejl i Fornavn: Kun bogstaver må anvendes");
        return false;
      }
    }
    return true;
  }

  public boolean validateEfternavn (String efternavn)
  {
    if (efternavn.isEmpty())
    {
      return false;
    }

    char[] chars = efternavn.toCharArray();
    for (char c : chars) {
      if(!Character.isLetter(c)) {
        exceptionLabel.set("Fejl i Efternavn: Kun bogstaver må anvendes");
        return false;
      }
    }
    return true;
  }


  public boolean validateMail (String mail)
  {
    if (mail.isEmpty()) {
      exceptionLabel.set("Fejl i email : Ingen emailadresse angivet");
      return false;
    }
    String emailRegex = "^[a-zA-Z0-9ÆØÅæøå]+(?:\\." +"[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (pattern.matcher(mail).matches()) {
      return true;
    }
    else
      exceptionLabel.set("Fejl i email: emailadressen er ikke gyldig");
    return false;
  }

  public boolean phoneVali(String phoneNumber) {
    try {
      if (phoneNumber.isEmpty() ) {
        exceptionLabel.set("Fejl i telefonnummer: Feltet er tomt");
        return false;
      }
      int numberInt = Integer.parseInt(phoneNumber);
      if (numberInt < 99999999 && numberInt > 10000000)
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

  public StringProperty exceptionStringProperty()
  {
    return exceptionLabel;
  }

  public StringProperty fornavnLabelProperty()
  {
    return fornavnLabel;
  }

  public StringProperty efternavnLabelProperty()
  {
    return efternavnLabel;
  }

  public StringProperty mailLabelProperty()
  {
    return mailLabel;
  }

  public StringProperty kodeLabelProperty()
  {
    return kodeLabel;
  }

  public StringProperty phoneLabelProperty()
  {
    return phoneLabel;
  }

  public boolean validateAll()
  {
    return validateFornavn(fornavnLabel.getValue()) && validateEfternavn(
        efternavnLabel.getValue()) && validateMail(mailLabel.getValue())
        && phoneVali(phoneLabel.getValue());
  }
}
