package controlador;

import Estructuras.ListaDoblementeLigada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import modelo.*;
import vista.*;

/**
 * Clase controladora para la gestión del sistema de transporte Metro.
 * Se encarga de coordinar la comunicación entre la vista y el modelo, controlando la lectura y el procesamiento de archivos de configuración para inicializar y construir la estructura del grafo del metro.
 * 
 * @date 27-Mayo-2026
 * @author Fernando Chablé Alonso, Pablo de Jesús Peréz Megchun y Emmanuel Quirino Roman
 */
public class ControladorMetro {
    
    /** Vista encargada de la interfaz de usuario del sistema de metro. */
    private VistaMetro vista;
    
    /** Modelo del sistema representado mediante una gráfica implementada con listas. */
    private GraficaLista<Estacion> modelo;

    /**
     * Constructor de la clase ControladorMetro.
     * Inicializa los componentes principales del patrón MVC para el sistema.
     * 
     * @param modelo La gráfica que almacena las estaciones y sus conexiones.
     * @param vista  La interfaz gráfica o de usuario asociada.
     */
    public ControladorMetro(VistaMetro vista) {
        this.modelo = new GraficaLista<Estacion>();
        this.vista = vista;
    }

    /**
     * Método que lee un archivo de texto con los datos de las estaciones y las registra en el modelo.
     * El archivo debe contener líneas con el formato "Nombre;EstadoCerrado". 
     * Las líneas vacías o que comiencen con "//" son ignoradas durante la lectura.
     * 
     * @param archivo Ruta o nombre del archivo de texto que contiene las estaciones.
     */
    public void cargarEstaciones(String archivo) {
        try {
            // Inicialización de los flujos de lectura para procesar el archivo
            FileReader fr = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(fr);

            String linea;

            // Lectura del archivo línea por línea hasta alcanzar el final del documento
            while ((linea = bf.readLine()) != null) {
                
                // Remueve espacios en blanco al inicio y al final de la línea actual
                linea = linea.trim();

                // Verifica que la línea no esté vacía y no sea un comentario
                if(!linea.isEmpty() && !linea.startsWith("//")) {
                    // Descompone la línea en un arreglo utilizando el delimitador ";"
                    String[] datosEstacion = linea.split(";");

                    String nombreEstacion = datosEstacion[0].trim();
                    Boolean estaCerrada = Boolean.parseBoolean(datosEstacion[1].trim());

                    // 1. Intentamos buscar si la estación YA FUE CREADA antes
                    Estacion estacionExistente = modelo.buscarPorNombre(nombreEstacion);

                    if (estacionExistente == null) {
                        // Si NO existe, creamos la estación y la agregamos como vértice normal
                        Estacion nuevaEstacion = new Estacion(nombreEstacion, estaCerrada);
                        modelo.agregarVertice(nuevaEstacion);
                    } else {
                        // Si YA existe, no la agregamos a la gráfica para evitar el Exception.
                        System.out.println("DEBUG: Saltando estación duplicada (transbordo) -> " + nombreEstacion);
                    }
                }
                
            }

            // Cierra el flujo del BufferedReader para liberar los recursos del sistema
            bf.close();
        } catch (IOException e) {
            // Notificación en consola en caso de ocurrir un error de Entrada/Salida
            System.out.println("ERROR! " + e);
        }
    }

    /**
     * Método que lee un archivo de texto con la información de las conexiones (tramos) entre estaciones.
     * Procesa datos de origen, destino, peso y estado de operación de cada tramo en el archivo, omitiendo líneas vacías y comentarios.
     * 
     * @param archivo Ruta o nombre del archivo de texto que contiene las uniones o tramos.
     */
    public void cargarTramos(String archivo) {
        try {
            // Inicialización de los flujos de lectura para procesar el archivo de conexiones
            FileReader fr = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(fr);

            String linea;

            // Ciclo de lectura para iterar sobre todas las líneas del archivo
            while((linea = bf.readLine()) != null) {

                // Remueve los espacios en blanco sobrantes en los extremos de la línea
                linea = linea.trim();

                // Valida que la línea contenga datos válidos y no corresponda a un comentario
                if(!linea.isEmpty() && !linea.startsWith("//")) {
                    // Divide la cadena de texto de la línea por medio del separador ";"
                    String[] datosTramo = linea.split(";");

                    // Recupera e interpreta cada una de las variables que definen el tramo de la vía
                    String estacion1 = datosTramo[0].trim();
                    String estacion2 = datosTramo[1].trim();
                    int peso = Integer.parseInt(datosTramo[2].trim());
                    Boolean estaCerrada = Boolean.parseBoolean(datosTramo[3].trim());

                    Estacion e1 = modelo.buscarPorNombre(estacion1);
                    Estacion e2 = modelo.buscarPorNombre(estacion2);

                    if(e1 != null && e2 != null && estaCerrada != true && e1.estaCerrada != true && e2.estaCerrada != true) {
                        modelo.agregarAristaPonderada(e1, e2, peso);
                    } else {
                        System.out.println("DEBUG: Omitiendo tramo de " + estacion1 + " con " + estacion2);
                    }
                    
                }

            }

            bf.close();
        } catch (IOException e) {
            System.out.println("ERROR! No se pudieron cargar los tramos: " + e);
        }
    }

    public void iniciarGrafica() {
        System.out.println("Iniciando...");

        String archivoEstaciones = "Estaciones.txt";
        System.out.println("Cargando estaciones desde " + archivoEstaciones);
        this.cargarEstaciones(archivoEstaciones);

        String archivoTramos = "Tramos.txt";
        System.out.println("Cargando tramos desde " + archivoTramos);
        this.cargarTramos(archivoTramos);        
    }

    public void iniciar() {
        boolean continuar = true;

        while (continuar == true) {

            int eleccion = vista.devolverEleccion();

            switch (eleccion) {
                case 1:
                
                    String estacionInicioNombre = vista.solicitarEstacion("Por favor, introduzca la estación de inicio (no ponga acentos)");
                    String estacionDestinoNombre = vista.solicitarEstacion("Por favor, introduzca la estación destino (no ponga acentos)");

                    Estacion estInicio = new Estacion(estacionInicioNombre, false);
                    Estacion estDestino = new Estacion(estacionDestinoNombre, false);

                    ListaDoblementeLigada<Estacion> rutaMasCortaNoPonderada = modelo.devolverRutaMasCortaNoPonderada(estInicio, estDestino);

                    System.out.println("La ruta que debe seguir es la siguiente: " + rutaMasCortaNoPonderada.toString());

                    break;
                case 2:

                    String estInicioNombre = vista.solicitarEstacion("Por favor, introduzca la estación de inicio (no ponga acentos)");
                    String estDestinoNombre = vista.solicitarEstacion("Por favor, introduzca la estación destino (no ponga acentos)");

                    Estacion estIni = new Estacion(estInicioNombre, false);
                    Estacion estDest = new Estacion(estDestinoNombre, false);

                    ListaDoblementeLigada<Estacion> rutaMasCortaPonderada = modelo.rutaMasCortaPonderada(estIni, estDest);

                    System.out.println("La ruta que debe seguir es la siguiente: " + rutaMasCortaPonderada.toString());
        
                    break;

                case 3:
                    System.out.println("Gracias por usar el sistema del metro!");
                    continuar = false;
                    break;

                default:

                    System.out.println("Opción inválida, intente de nuevo.");
                    break;
            }
        }
        
    }

}