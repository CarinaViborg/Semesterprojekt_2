package timeplan.client.views.personalplan;

import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;
import timeplan.shared.transferobjects.Shift;
import timeplan.shared.transferobjects.Timestamp;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PersonalPlanViewModel
{
  private final TimeplanModel model;

  public PersonalPlanViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
  }

  public ArrayList<Shift> getAllShiftsCurrentUser() throws RemoteException
  {
    return model.getAllShiftsCurrentUser();
  }

  public ArrayList<Timestamp> getAllTimestampsCurrentUser()
      throws RemoteException
  {
    return model.getAllTimestampsCurrentUser();
  }
}
