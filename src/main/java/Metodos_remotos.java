/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author eliazith
 */
public interface Metodos_remotos extends Remote {
    public int ini_monitor(String Registro)throws RemoteException;
    public String load_monitor(String Proc_load, String Ip)throws RemoteException;
    public int ini_client()throws RemoteException;
    public String getLoadAvg(int index)throws RemoteException;
}
