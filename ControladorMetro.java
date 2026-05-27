import Estructuras.ListaDoblementeLigada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import modelo.*;

public class ControladorMetro {
    
    private VistaMetro vista;
    private GraficaLista<Estacion> modelo;

    public ControladorMetro(GraficaLista<Estacion> modelo, VistaMetro vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void cargarEstaciones(String archivo) {
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(fr);

            String linea;

            while ((linea = bf.readLine()) != null) {
                
                linea = linea.trim();

                if(!linea.isEmpty() && !linea.startsWith("//")) {
                    String[] datosEstacion = linea.split(";");

                    String nombreEstacion = datosEstacion[0].trim();
                    Boolean estaCerrada = Boolean.parseBoolean(datosEstacion[1].trim());

                    Estacion estacion = new Estacion(nombreEstacion, estaCerrada);

                    modelo.agregarVertice(estacion);
                }
                
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("ERROR! " + e);
        }
    }

    public void cargarTramos(String archivo) {
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(fr);

            String linea;

            while((linea = bf.readLine()) != null) {

                linea = linea.trim();

                if(!linea.isEmpty() && !linea.startsWith("//")) {
                    String[] datosTramo = linea.split(";");

                    String estacion1 = datosTramo[0].trim();
                    String estacion2 = datosTramo[1].trim();
                    int peso = Integer.parseInt(datosTramo[2].trim());
                    Boolean estaCerrada = Boolean.parseBoolean(datosTramo[3].trim());

                    
                    
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }



}
