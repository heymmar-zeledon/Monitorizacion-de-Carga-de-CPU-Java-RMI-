/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
/**
 *
 * @author eliazith
 */
public class Coordinador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        System.out.println("Bienvenido");
        try
        {
            Implementacion_Metodos_Remotos Implementacion = new Implementacion_Metodos_Remotos();
            Registry y = java.rmi.registry.LocateRegistry.createRegistry(1099);
            y.rebind("Mi_coordinador",Implementacion);
            JOptionPane.showMessageDialog(null, "->Servidor conectado");
        }
        catch(Exception e)
        {
            System.out.println("Coordinador no conectado" +e);
        }
    }
    
}
