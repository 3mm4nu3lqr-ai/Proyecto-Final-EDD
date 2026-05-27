package vista;
import java.util.Scanner;

import Estructuras.ListaDoblementeLigada;

public class VistaMetro {
    private Scanner in;

    public VistaMetro() {
        this.in = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("╔═════════════════════════════════╗ \n" + 
        "║ SISTEMA DE TRANSPORTE COLECTIVO ║ \n" +
        "║        METRO DE LA CDMX         ║ \n" +
        "╚═════════════════════════════════╝ \n" +
        "\n" +
        "¡BIENVENIDO AL METRO DE LA CIUDAD DE MÉXICO! \n" +
        "\n" +
        "╠═ 1. Mostrar ruta mas corta (menor número de estaciones). \n" +
        "╠═ 2. Mostrar ruta más rápida (menor cantidad de tiempo). \n" +
        "╠═ 3. Salir."
        );
    }

    public int devolverEleccion() {
        while(!in.hasNextInt()) {
            System.out.println("Por favor, introduzca un número válido.");
            in.next();
        }

        int eleccion = in.nextInt();
        in.nextLine();
        return eleccion;
    }

    public String solicitarEstacion(String msg) {
        System.out.println(msg);
        String estUsuario = in.nextLine();
        return estUsuario;
    }

    public void mostrarRuta(ListaDoblementeLigada<?> ruta) {
        if(ruta == null || ruta.devolverLongitud() == 0) {
            System.out.println("ERROR! No se encontró una ruta disponible :(.");
            return;
        }

        System.out.println("══════ RUTA ENCONTRADA ══════");
        System.out.println(ruta.toString());
    }
}
