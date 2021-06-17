/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
/**
 *
 * @author eliazith
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la direccion Ip del coordinador: ");
        String  Ip_coordinador = sc.nextLine();
        int puerto = 1099;
        try
        {
            Registry Registro = LocateRegistry.getRegistry(Ip_coordinador,puerto);
            Metodos_remotos method = (Metodos_remotos) Naming.lookup("//"+Ip_coordinador+"/Mi_coordinador");
            while(true)
            {
                int cont = 0;
                int monitores = method.ini_client();
                System.out.println("Numero de Monitores activos: "+monitores+"\n");
                System.out.println("Ingrese que monitor quiere consultar su carga CPU: (0 para salir)");
                int res = sc.nextInt();
                System.out.println("Numero de cargas que desea ver: ");
                int cargas = sc.nextInt();
                if(res == 0)
                {
                    System.out.println("Finalizado\n");
                    break;
                }
                else
                {
                    while(cont != cargas)
                    {
                        System.out.println("-------------------\n");
                        String carga = method.getLoadAvg(res-1);
                        System.out.println(carga);
                        Thread.sleep(3*1000);
                        cont++;
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        
    }
    
}
