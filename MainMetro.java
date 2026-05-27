import Estructuras.ListaDoblementeLigada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainMetro {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("Estaciones.txt");
            BufferedReader bf = new BufferedReader(fr);

            String linea;

            while((linea = bf.readLine()) != null) {
                linea = linea.trim();

                if(!linea.isEmpty() && !linea.startsWith("//")) {
                    String[] datosEstacion = linea.split("; ");
                    
                    System.out.println(datosEstacion[0] + ", " + datosEstacion[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR! " + e);
        }
    }
}
