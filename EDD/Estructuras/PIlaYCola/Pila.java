package Estructuras.PIlaYCola;

/**
 * Implementación de una pila genérica (LIFO: Last In, First Out).
 * Permite almacenar elementos y operar sobre ellos mediante las operaciones básicas de una pila.
 *
 * @param <T> Tipo de los elementos almacenados en la pila.
 * @author Fernando Chablé Alonzo y Emmanuel Quirino Roman.
 * @date 21-Abril-2026
 */
public class Pila<T> implements PiCoLa<T> {

    /**
     * Clase interna que representa un nodo de la pila.
     */
    private class Nodo {
        // Elemento almacenado en el nodo.
        public T elemento;

        // Referencia al siguiente nodo.
        public Nodo siguiente;

        /**
         * Método constructor del nodo.
         *
         * @param elemento Elemento a almacenar en el nodo.
         */
        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
    }

    //Referencia al nodo que está en el tope de la pila.
    protected Nodo tope;

    // Número de elementos en la pila
    private int tamanio;

    /**
     * Método constructor que inicializa una pila vacía.
     */
    public Pila() {
        // Aquí va tu codigo 
        this.tope = null;
        this.tamanio = 0;
    }

    /**
     * Método que inserta un elemento en el tope de la pila.
     *
     * @param elemento Elemento a agregar.
     */
    @Override
    public void meter(T elemento) {
        // Aquí va tu codigo 
        Nodo otroNodo = new Nodo(elemento);
        otroNodo.siguiente = tope;
        tope = otroNodo;
        tamanio++;
    }

    /**
     * Método que elimina y devuelve el elemento en el tope de la pila.
     *
     * @return Elemento removido o null si la pila está vacía.
     */
    @Override
    public T sacar() {
        // Aquí va tu codigo 
        if (estaVacia()) {
            return null;
        }
        T elementoASacar = tope.elemento;
        tope = tope.siguiente;
        tamanio--;
        return elementoASacar;
    }

    /**
     * Método que devuelve el elemento en el tope sin eliminarlo.
     *
     * @return Elemento en el tope o null si la pila está vacía.
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
     * Método que verifica si la pila está vacía.
     *
     * @return true si la pila no tiene elementos, false en caso contrario.
     */
    @Override
    public boolean estaVacia() {
        // Aquí va tu codigo 
        return tamanio == 0;
    }

    /**
     * Método que devuelve el número de elementos en la pila.
     *
     * @return Tamaño de la pila.
     */
    @Override
    public int devolverTamanio() {
        // Aquí va tu codigo 
        return tamanio;
    }

    /**
     * Método que compara esta pila con otro objeto para verificar si son iguales.
     * Dos pilas son iguales si contienen los mismos elementos en el mismo orden.
     *
     * @param o Objeto a comparar.
     * @return true si las pilas son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        @SuppressWarnings("unchecked") Pila<T> m = (Pila<T>)o;
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
     * Método que devuelve una representación en cadena de la pila.
     *
     * @return Cadena con los elementos de la pila.
     */
    @Override
    public String toString() {
        String resultado = "[";
        Nodo actual = tope;

        while (actual != null) {
            resultado += actual.elemento;
            if (actual.siguiente != null) {
                resultado += ",\n ";
            }
            actual = actual.siguiente;
        }

        resultado += "]";
        return resultado;
    }

}
