/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author eliazith
 */
public class Implementacion_Metodos_Remotos extends UnicastRemoteObject implements Metodos_remotos
{
    public ArrayList<String> Registros;
    public ArrayList<String> RegistrosCPU;
    
    int cont;
    public Implementacion_Metodos_Remotos() throws RemoteException
    {
        super();
        Registros = new ArrayList<String>();
        
        RegistrosCPU = new ArrayList<String>();
    }
    @Override
    public void ini_monitor(String Registro) throws RemoteException {
       if(Registros.indexOf(Registro) == -1){
           Registros.add(Registro);
           cont++;
       }else{
           System.out.println("El registro ya se encuentra");
       }
    }

    @Override
    public String load_monitor(String Proc_load, String Ip) throws RemoteException {
        boolean enc = false;

        String result = "";
        for (String search : Registros) {
            if (search.equals(Ip)) {
                RegistrosCPU.add(Registros.indexOf(search), Proc_load);
                result = "-> " + search + " -> " + Proc_load;
                enc = true;
                break;
            }
        }
        if(enc){
            return result;
        }else{
            return "El registro no ha sido encontrado";
        }
        
    }

    @Override
    public int ini_client() throws RemoteException {
        String line = null;
        String ping_r = "";
        boolean status = false;
        for(String search : Registros){
            String ipAddress = search;
            System.out.println("--------------");
            System.out.println(">> Registro "+search);
            try {
                Process pro = Runtime.getRuntime().exec("ping -c 3 " + ipAddress);
                BufferedReader buf = new BufferedReader(new InputStreamReader(
                pro.getInputStream()));
                System.out.println("--------------------------\n");
                while((line = buf.readLine()) != null)
                {
                    System.out.println("Ping: "+line);
                    ping_r = line;
                    String val = "0% packet loss";
                    if(-1 <= ping_r.indexOf(val))
                    {
                        status = true;
                        System.out.println("Status: "+status);
                    }
                }
                Thread.sleep(1 * 1000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            if(status = false)
            {
                Registros.remove(search);
                RegistrosCPU.remove(search);
                cont--;
            }
            
        }
        return cont;
    }

    @Override
    public String getLoadAvg(int index) throws RemoteException {
      
        String Carga = "Direccion Ip del Monitor: "+ Registros.get(index)+"\n"+"Carga de la CPU: "+ RegistrosCPU.get(index) +"\n";
        return Carga;
    }
    
}
