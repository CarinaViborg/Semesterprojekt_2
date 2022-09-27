package timeplan.client.core;

import timeplan.client.model.TimeplanModel;
import timeplan.client.network.ClientImpl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ModelFactory
{
  private TimeplanModel timeplanModel;
  private ClientImpl client;

  {
    try{
      client = new ClientImpl();
    } catch(RemoteException | NotBoundException e){
      e.printStackTrace();
    }
  }

  private static ModelFactory mf;

  private ModelFactory(){}

  public static ModelFactory getModelFactory(){
    if(mf == null){
      mf = new ModelFactory();
    }
    return mf;
  }

  public TimeplanModel getTimeplanModel(){
    if(timeplanModel == null){
      this.timeplanModel = new TimeplanModel(client);
    }
    return timeplanModel;
  }

}
