package timeplan.client.views.startpage;

import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;

import java.rmi.RemoteException;

public class StartPageViewModel
{
  private final TimeplanModel model;

  public StartPageViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
  }

  public void tjekInd() throws RemoteException
  {
    model.tjekInd();
  }

  public void tjekUd() throws RemoteException
  {
    model.tjekUd();
  }

  public boolean isCheckedIn() throws RemoteException
  {
    return model.isCheckedIn();
  }
}
