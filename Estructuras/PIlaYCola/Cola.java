package Estructuras.PIlaYCola;

/**
 * Implementación de una cola genérica (FIFO: First In, First Out).
 * Permite almacenar elementos y operar sobre ellos siguiendo el orden de llegada.
 *
 * @param <T> Tipo de los elementos almacenados en la cola.
 * @author Fernando Chablé Alonzo y Emmanuel Quirino Roman.
 * @date 21-Abril-2026
 */
public class Cola<T> implements PiCoLa<T> {

    // Aquí va tu codigo
    /**
     * Clase interna que representa un nodo de la cola.
     */
    private class Nodo {
        // Elemento almacenado en el nodo.
        public T elemento;

        // Referencia al siguiente nodo.
        public Nodo siguiente;

        /**
         * Método constructor del nodo.
         *
         * @param elemento Elemento a almacenar.
         */
        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
    }

    // Referencia al primer elemento de la cola.
    protected Nodo tope;

    // Referencia al último elemento de la cola.
    protected Nodo termino;

    // Número de elementos en la cola.
    private int tamanio;

    /**
     * Método constructor que inicializa una cola vacía.
     */
    public Cola() {
        // Aquí va tu codigo 
        this.tope = null;
        this.termino = null;
        this.tamanio = 0;
    }

    /**
     * Método que inserta un elemento al final de la cola.
     *
     * @param elemento Elemento a agregar.
     */
    @Override
    public void meter(T elemento) {
        // Aquí va tu codigo 
        Nodo otroNodo = new Nodo(elemento);
        if (estaVacia()) {
            tope = otroNodo;
            termino = otroNodo;
        } else {
            termino.siguiente = otroNodo;
            termino = otroNodo;
        }
        tamanio++;
    }

    /**
     * Método que elimina y devuelve el elemento al frente de la cola.
     *
     * @return Elemento eliminado o null si la cola está vacía.
     */
    @Override
    public T sacar() {
        // Aquí va tu codigo 
        if (estaVacia()) {
            return null;
        }
        T elementoASacar = tope.elemento;
        tope = tope.siguiente;
        if (tope == null) {
            termino = null;
        }
        tamanio--;
        return elementoASacar;
    }

    /**
     * Método que devuelve el elemento al frente sin eliminarlo.
     *
     * @return Elemento al frente o null si la cola está vacía.
     */
    @Override
    public T mira() {
        // Aquí va tu codigo 
        if (estaVacia()) {
            return null;
        }
        return tope.elemento;
    }

    /**
     * Método que verifica si la cola está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario.
     */
    @Override
    public boolean estaVacia() {
        // Aquí va tu codigo 
        return tamanio == 0;
    }

    /**
     * Método que devuelve el número de elementos en la cola.
     *
     * @return Tamaño de la cola.
     */
    @Override
    public int devolverTamanio() {
        // Aquí va tu codigo 
        return tamanio;
    }

    /**
     * Método que compara esta cola con otro objeto para verificar si son iguales.
     * Dos colas son iguales si contienen los mismos elementos en el mismo orden.
     *
     * @param o Objeto a comparar.
     * @return true si las colas son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        @SuppressWarnings("unchecked") Cola<T> m = (Cola<T>)o;
        Nodo n1 = this.tope;
        Nodo n2 = m.tope;
        while (n1 !=null && n2 != null){
            if (!n1.elemento.equals(n2.elemento))
                return false;
            n1 = n1.siguiente;
            n2 = n2.siguiente;
        }
        return (n1 == null && n2 == null);
    }

    /**
     * Método que devuelve una representación en cadena de la cola.
     *
     * @return Cadena con los elementos de la cola.
     */
    @Override
    public String toString() {
        String resultado = "<- [";
        Nodo actual = tope;

        while (actual != null) {
            resultado += actual.elemento;
            if (actual.siguiente != null) {
                resultado += ", ";
                actual = actual.siguiente;
            }
        }

        resultado += "] <-";
        return resultado;
    }
    
}
