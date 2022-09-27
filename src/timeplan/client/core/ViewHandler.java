package timeplan.client.core;

import timeplan.client.util.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewHandler
{
  private Stage stage;
  private static ViewHandler instance;


  private ViewHandler(){}

  public void start(){
    stage = new Stage();
    openLogin();
  }

  public static ViewHandler getViewHandler(){
    if(instance == null){
      instance = new ViewHandler();
    }
    return instance;
  }

  public void openStartPage(){
      try{
        Parent root = loadFXML("../views/startpage/StartPage.fxml");
        stage.setTitle("Startside");
        Scene startPageScene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(startPageScene);
        stage.show();
      } catch(IOException e){e.printStackTrace();}
  }

  public void openPersonalPlan(){
      try {
        Parent root = loadFXML("../views/personalplan/PersonalPlan.fxml");
        stage.setTitle("Mine vagter");
        Scene personalPlanScene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(personalPlanScene);
        stage.show();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
  }

  public void openLogin(){
    try{
      Parent root = loadFXML("../views/login/Login.fxml");
      Scene loginScene = new Scene(root);
      stage.setTitle("Login");
      stage.setResizable(false);
      stage.setScene(loginScene);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openEditUser(){
    try{
      Parent root = loadFXML("../views/adminedituser/AdminEditUser.fxml");
      Scene editUserScene = new Scene(root);
      stage.setTitle("Rediger bruger");
      stage.setResizable(false);
      stage.setScene(editUserScene);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openEmpEditUser(){
    try{
      Parent root = loadFXML("../views/empedituser/EmpEditUser.fxml");
      Scene empEditUserScene = new Scene(root);
      stage.setTitle("Rediger profil");
      stage.setResizable(false);
      stage.setScene(empEditUserScene);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openAdminProfile(){
    try{
      Parent root = loadFXML("../views/adminprofile/AdminProfile.fxml");
      Scene adminProfile = new Scene(root);
      stage.setTitle("Rediger profil");
      stage.setResizable(false);
      stage.setScene(adminProfile);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openEditShift(){
    try{
      Parent root = loadFXML("../views/editshift/EditShift.fxml");
      Scene editShift = new Scene(root);
      stage.setTitle("Rediger vagt");
      stage.setResizable(false);
      stage.setScene(editShift);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openDepartmentPlan(){
    try{
      Parent root = loadFXML("../views/departmentplan/DepartmentPlan.fxml");
      Scene departmentPlan = new Scene(root);
      stage.setTitle("Afdelingsplan");
      stage.setResizable(false);
      stage.setScene(departmentPlan);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openCreateUser(){
    try{
      Parent root = loadFXML("../views/createuser/CreateUser.fxml");
      Scene createUser = new Scene(root);
      stage.setTitle("Opret bruger");
      stage.setResizable(false);
      stage.setScene(createUser);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openCreateShift(){
    try{
      Parent root = loadFXML("../views/createshift/CreateShift.fxml");
      Scene createShiftScene = new Scene(root);
      stage.setTitle("Opret vagt");
      stage.setResizable(false);
      stage.setScene(createShiftScene);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  public void openAdminStartPage(){
      try {
        Parent root = loadFXML("../views/adminstartpage/AdminStartPage.fxml");
        Scene adminStartPageScene = new Scene(root);
        stage.setTitle("Startside");
        stage.setResizable(false);
        stage.setScene(adminStartPageScene);
        stage.show();
      } catch (IOException e) {e.printStackTrace();}
  }

  public void openAdminDepartmentPlan()
  {
    try{
      Parent root = loadFXML("../views/admindepartmentplan/AdminDepartmentPlan.fxml");
      Scene adminDepartmentPlanScene = new Scene(root);
      stage.setTitle("Afdelingsplan");
      stage.setResizable(false);
      stage.setScene(adminDepartmentPlanScene);
      stage.show();
    }catch(IOException e){e.printStackTrace();}
  }

  private Parent loadFXML(String path) throws IOException
  {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource(path));
    Parent root = loader.load();

    ViewController controller = loader.getController();
    controller.init();
    return root;
  }
}
