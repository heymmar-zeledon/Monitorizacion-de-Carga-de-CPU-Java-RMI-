/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eliazith
 */
public class Monitor {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String Direccion_Ip = null;
        try {
            Direccion_Ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">>Direccion ip del monitor: "+Direccion_Ip);
        System.out.println("Ingrese el tiempo de envio de carga de la CPU");
        int Segundos = sc.nextInt();
        sc.nextLine();
        int Puerto = 1099;
        int cont = 0;
        try
        {
            System.out.println("Ingrese la direccion Ip del coordinador: ");
            String  Ip_coordinador = sc.nextLine();
            Registry Registro = LocateRegistry.getRegistry(Ip_coordinador,Puerto);
            Metodos_remotos method = (Metodos_remotos) Naming.lookup("//"+Ip_coordinador+"/Mi_coordinador");
            method.ini_monitor(Direccion_Ip);
            System.out.println("ini_monitor iniciado");
            System.out.println("Tiempo de carga CPU: "+Segundos*1000);
            while(true)
            {
                cont++;
                System.out.println("---leyendo carga #"+cont);
                BufferedReader lectura = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/loadavg")));
                String archivoRead = lectura.readLine();
                if(archivoRead == null)
                {
                    archivoRead="proc/loadavg no se pudo leer\n";
                }
                String Res = method.load_monitor(archivoRead, Direccion_Ip);
                System.out.println(Res);
                System.out.println("---------------");
                Thread.sleep(Segundos * 1000);
            }
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        
    }    
}
