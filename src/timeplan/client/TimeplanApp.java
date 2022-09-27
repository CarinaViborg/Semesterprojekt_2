package timeplan.client;

import timeplan.client.core.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class TimeplanApp extends Application
{
  @Override public void start(Stage stage) throws Exception
  {
    ViewHandler viewHandler = ViewHandler.getViewHandler();
    viewHandler.start();
  }
}
