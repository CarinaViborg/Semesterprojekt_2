package timeplan.client.core;

import timeplan.client.views.createshift.CreateShiftViewModel;
import timeplan.client.views.createuser.CreateUserViewModel;
import timeplan.client.views.departmentplan.DepartmentPlanViewModel;
import timeplan.client.views.editshift.EditShiftViewModel;
import timeplan.client.views.adminedituser.AdminEditUserViewModel;
import timeplan.client.views.empedituser.EmpEditUserViewModel;
import timeplan.client.views.login.LoginViewModel;
import timeplan.client.views.personalplan.PersonalPlanViewModel;
import timeplan.client.views.startpage.StartPageViewModel;

public class ViewModelFactory
{
  private static ViewModelFactory vmf;
  private CreateShiftViewModel createShiftViewModel;
  private CreateUserViewModel createUserViewModel;
  private DepartmentPlanViewModel departmentPlanViewModel;
  private EditShiftViewModel editShiftViewModel;
  private AdminEditUserViewModel adminEditUserViewModel;
  private LoginViewModel loginViewModel;
  private PersonalPlanViewModel personalPlanViewModel;
  private StartPageViewModel startPageViewModel;
  private EmpEditUserViewModel empEditUserViewModel;

  private ViewModelFactory(){}

  public static ViewModelFactory getViewModelFactory(){
    if(vmf == null){
      vmf = new ViewModelFactory();
    }
    return vmf;
  }

  public CreateShiftViewModel getCreateShiftViewModel()
  {
    if (createShiftViewModel == null)
    {
      createShiftViewModel = new CreateShiftViewModel();
    }
    return createShiftViewModel;
  }

  public CreateUserViewModel getCreateUserViewModel()
  {
    if (createUserViewModel == null)
    {
      createUserViewModel = new CreateUserViewModel();
    }
    return createUserViewModel;
  }

  public DepartmentPlanViewModel getDepartmentPlanViewModel()
  {
    if (departmentPlanViewModel == null)
    {
      departmentPlanViewModel = new DepartmentPlanViewModel();
    }
    return departmentPlanViewModel;
  }

  public AdminEditUserViewModel getEditUserViewModel()
  {
    if (adminEditUserViewModel == null)
    {
      adminEditUserViewModel = new AdminEditUserViewModel();
    }
    return adminEditUserViewModel;
  }

  public LoginViewModel getLoginViewModel()
  {
    if (loginViewModel == null)
    {
      loginViewModel = new LoginViewModel();
    }
    return loginViewModel;
  }

  public PersonalPlanViewModel getPersonalPlanViewModel()
  {
    if (personalPlanViewModel == null)
    {
      personalPlanViewModel = new PersonalPlanViewModel();
    }
    return personalPlanViewModel;
  }

  public StartPageViewModel getStartPageViewModel()
  {
    if (startPageViewModel == null)
    {
      startPageViewModel = new StartPageViewModel();
    }
    return startPageViewModel;
  }

  public EditShiftViewModel getEditShiftViewModel(){
    if(editShiftViewModel == null){
      editShiftViewModel = new EditShiftViewModel();
    }
    return editShiftViewModel;
  }

  public EmpEditUserViewModel getEmpEditUserViewModel()
  {
    if(empEditUserViewModel == null){
      empEditUserViewModel = new EmpEditUserViewModel();
    }
    return empEditUserViewModel;
  }
}
