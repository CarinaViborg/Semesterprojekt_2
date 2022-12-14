package timeplan.client.util;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSubject
{
  void addListener(String eventName, PropertyChangeListener listener);
  void removeListener(String eventName, PropertyChangeListener listener);
}
