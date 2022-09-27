package timeplan.server;

import timeplan.server.network.RMIServerImpl;
import timeplan.shared.interfaces.ServerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunServer
{
  public static void main(String[] args) throws RemoteException,
      AlreadyBoundException
  {
    ServerInterface server = new RMIServerImpl();
    Registry registry = LocateRegistry.createRegistry(1099);
    registry.bind("BroadcastServer", server);
  }
}
