package modelo;

/**
 * Representa una estación dentro del sistema de transporte Metro.
 * Almacena la información del nombre de la estación y su estado actual de operación.
 * 
 * @date 27-Mayo-2026
 * @author Fernando Chablé Alonso, Pablo de Jesús Peréz Megchun y Emmanuel Quirino Roman
 */
public class Estacion {
    
    /** El nombre identificador de la estación. */
    public String nombre;
    
    /** Estado de operación de la estación (true si está cerrada, false si está abierta). */
    public boolean estaCerrada;

    /**
     * Constructor de la clase Estacion.
     * Inicializa una nueva estación con su nombre y estado de operación correspondientes.
     * 
     * @param nombre      El nombre de la estación.
     * @param estaCerrada El estado de apertura de la estación.
     */
    public Estacion(String nombre, boolean estaCerrada) {
        this.nombre = nombre;
        this.estaCerrada = estaCerrada;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}