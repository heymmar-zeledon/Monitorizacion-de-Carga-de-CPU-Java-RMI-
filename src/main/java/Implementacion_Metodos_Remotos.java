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
/**
 *
 * @author eliazith
 */
public class Implementacion_Metodos_Remotos extends UnicastRemoteObject implements Metodos_remotos
{
    public String Registros[];
    public String RegistrosCPU[];
    int TiempoCPU;
    int cont;
    public Implementacion_Metodos_Remotos() throws RemoteException
    {
        super();
        Registros = new String[5];
        for(int z = 0; z < 5; z++)
        {
            Registros[z] = "";
        }
        RegistrosCPU = new String[5];
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el tiempo con que se mandara la carga de la CPU: ");
        TiempoCPU = sc.nextInt();
    }
    @Override
    public int ini_monitor(String Registro) throws RemoteException {
        int indice = cont;
        int x = 0;
        if(cont != 0)
        {
            indice = cont - 1;
        }
        if(indice > 4)
        {
            System.out.print("!! ->> No se pueden agregar mas monitores\n");
        }
        else
        {
            for(x = 0; x <= indice ; x++)
            {
                if(Registros[x] == "")
                {
                    Registros[indice] = Registro;
                    System.out.println("Nuevo monitor: #"+ indice +"->"+Registros[indice]);
                    cont++;
                }
            }
        }
        return TiempoCPU;
    }

    @Override
    public String load_monitor(String Proc_load, String Ip) throws RemoteException {
        String result = "";
        for(int i=0;i < Registros.length;i++)
        {
            if(Ip.equals(Registros[i]))
            {
                RegistrosCPU[i]= Proc_load;
                result = "->"+Registros[i] +" ->"+RegistrosCPU[i];
                break;
            }
        }
        return result;
    }

    @Override
    public int ini_client() throws RemoteException {
        String line = null;
        String ping_r = "";
        boolean status = false;
        for(int j= 0; j < Registros.length;j++)
        {
            String ipAddress = Registros[j];
            System.out.println("--------------");
            System.out.println(">> Registro "+j);
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
                    if(ping_r.indexOf(val) >-1)
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
                Registros[j]= "";
                RegistrosCPU[j]= "";
                cont--;
            }
        }
        return cont;
        
    }

    @Override
    public String getLoadAvg(int index) throws RemoteException {
      
        String Carga = "Direccion Ip del Monitor: "+ Registros[index-1]+"\n"+"Carga de la CPU: "+ RegistrosCPU[index-1] +"\n";
        return Carga;
    }
    
}
