package timeplan.client.views.login;

import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;
import timeplan.client.util.PropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class LoginViewModel implements PropertyChangeSubject
{
  private final TimeplanModel model;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  public LoginViewModel()
  {
    model = ModelFactory.getModelFactory().getTimeplanModel();
    model.addListener("Login State", this::handleLogin);
  }

  public void login(String username, String password) throws RemoteException
  {
    model.login(username, password);
  }

  @Override public void addListener(String eventName,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(eventName, listener);
  }

  @Override public void removeListener(String eventName,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(eventName, listener);
  }

  private void handleLogin(PropertyChangeEvent propertyChangeEvent)
  {
    support.firePropertyChange(propertyChangeEvent.getPropertyName(),
        propertyChangeEvent.getOldValue(), propertyChangeEvent.getNewValue());
  }
}
