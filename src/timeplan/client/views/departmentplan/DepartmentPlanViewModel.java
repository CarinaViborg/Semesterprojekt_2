package timeplan.client.views.departmentplan;

import timeplan.client.core.ModelFactory;
import timeplan.client.model.TimeplanModel;
import timeplan.shared.transferobjects.Employee;
import timeplan.shared.transferobjects.Shift;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class DepartmentPlanViewModel
{
  private final TimeplanModel model;

  public DepartmentPlanViewModel(){
    model = ModelFactory.getModelFactory().getTimeplanModel();
  }

  public ArrayList<Employee> getAllEmployees() throws RemoteException
  {
    return model.getAllEmployees();
  }

  public ArrayList<Shift> getAllShifts() throws RemoteException
  {
    return model.getAllShifts();
  }
}
