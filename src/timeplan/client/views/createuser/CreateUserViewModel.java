package timeplan.client.views.createuser;

import javafx.beans.property.*;
import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.regex.Pattern;

public class CreateUserViewModel
{
  private final StringProperty exceptionProperty;
  LocalDateTime now = LocalDateTime.now();
  private final StringProperty fornavnText = new SimpleStringProperty();
  private final StringProperty efternavnText = new SimpleStringProperty();
  private final StringProperty tlfText = new SimpleStringProperty();
  private final StringProperty mailText = new SimpleStringProperty();
  private final StringProperty kodeText = new SimpleStringProperty();
  private final ObjectProperty datepickerValue = new SimpleObjectProperty();



  private final TimeplanModel model;

  public CreateUserViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
    exceptionProperty = new SimpleStringProperty();
  }

  public void createUser(String name, String lastName, Date date,
      String mail, String phone, String password,
      boolean isAdmin) throws RemoteException
  {
    int phoneNumber = Integer.parseInt(phone);
    model.createUser(name, lastName, date, mail, phoneNumber, password, isAdmin);
  }

  public StringProperty fornavnTextProperty()
  {
    return fornavnText;
  }

  public StringProperty efternavnTextProperty()
  {
    return efternavnText;
  }

  public StringProperty tlfTextProperty()
  {
    return tlfText;
  }

  public StringProperty mailTextProperty()
  {
    return mailText;
  }

  public StringProperty kodeTextProperty()
  {
    return kodeText;
  }

  public ObjectProperty datepickerValueProperty()
  {
    return datepickerValue;
  }

  public StringProperty exceptionProperty(){
    return exceptionProperty;
  }

  public boolean dateValidation(LocalDate date) {
    if (date.isAfter(ChronoLocalDate.from(now))){
      exceptionProperty.set("Dato er efter dags dato");
      return false;
    }
    return true;
  }

  public boolean nameVali(String name)
  {
    if (name.isEmpty())
    {
      exceptionProperty.set("Fornavn er tom");
      return false;
    }
    char[] chars = name.toCharArray();

    for (char c : chars) {
      if(!Character.isLetter(c) || Character.isWhitespace(c)) {
        exceptionProperty.set("Fejl i fornavn: Kun bogstaver må anvendes");
        return false;
      }
    }
    return true;
  }

  public boolean lNameVali(String efternavn)
  {
    if (efternavn.isEmpty())
    {
      exceptionProperty.set("Efternavn er tom");
      return false;
    }
    char[] chars = efternavn.toCharArray();

    for (char c : chars) {
      if(!Character.isLetter(c)) {
        exceptionProperty.set("Fejl i efternavn: Kun bogstaver må anvendes");
        return false;
      }
    }
    return true;
  }

  public boolean phoneVali(String phone) {
    try {
      int numberInt = Integer.parseInt(phone);
      if (phone.isEmpty() ) {
        exceptionProperty.set("Fejl i telefonnummer: Feltet er tomt");
        return false;
      }
      else if (numberInt < 99999999 && numberInt > 10000000)
        return true;
      else {
        exceptionProperty.set("Fejl i telefonnummer: nummer er ugyldigt");
        return false;
      }
    } catch (NumberFormatException e) {
      exceptionProperty.set("Fejl i telefonnummer: Kun numre er tilladt");
      return false;
    }
  }

  public boolean emailValid (String mail) {
    if (mail.isEmpty()) {
      exceptionProperty.set("Fejl i email : Ingen emailadresse angivet");
      return false;
    }
    String  emailRegex = "^[a-zA-Z0-9ÆØÅæøå]+(?:\\." +"[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (pattern.matcher(mail).matches()) {
      return true;
    }
    else
      exceptionProperty.set("Fejl i email: emailadressen er ikke gyldig");
    return false;
  }

  public boolean validateAll()
  {
    return nameVali(fornavnText.getValue()) && lNameVali(
        efternavnText.getValue()) && emailValid(mailText.getValue())
        && phoneVali(tlfText.getValue()) && dateValidation(
        (LocalDate) datepickerValue.getValue());
  }
}
