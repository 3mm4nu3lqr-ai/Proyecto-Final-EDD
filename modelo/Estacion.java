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

    /**
     * Método que compara esta estación con otra para determinar si son iguales.
     * La igualdad se define basándose únicamente en la coincidencia de los nombres.
     * 
     * @param otro La otra estación con la que se va a comparar.
     * @return true si ambos nombres son iguales; false en caso contrario.
     */
    @Override
    public boolean equals(Estacion otro) {
        if(this.nombre.equals(otro.nombre)) {
            return true;
        } else {
            return false;
        }
    }

}