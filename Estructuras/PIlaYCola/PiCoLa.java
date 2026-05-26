package Estructuras.PIlaYCola;

/**
 * Interfaz que define las operaciones básicas de una estructura lineal que puede comportarse como pila (LIFO) o cola (FIFO).
 *
 * @param <T> Tipo de los elementos que almacena la estructura.
 */
public interface PiCoLa<T> {
    
    /**
     * Método que inserta un elemento en la estructura.
     * En una pila se inserta en el tope, en una cola al final.
     *
     * @param elemento Elemento a insertar.
     */
    public void meter(T elemento);   
    
    /**
     * Método que elimina y devuelve un elemento de la estructura.
     * En una pila se extrae del tope, en una cola del frente.
     *
     * @return Elemento eliminado o null si la estructura está vacía.
     */
    public T sacar();                
    
    /**
     * Método que devuelve el elemento siguiente a salir sin eliminarlo.
     * En una pila corresponde al tope, en una cola al frente.
     *
     * @return Elemento a consultar o null si está vacía.
     */
    public T mira();
    
    /**
     * Método que indica si la estructura está vacía.
     *
     * @return true si no contiene elementos, false en caso contrario.
     */
    public boolean estaVacia();
    
    /**
     * Método que devuelve el número de elementos almacenados.
     *
     * @return Tamaño de la estructura.
     */
    public int devolverTamanio();

    /**
     * Método que compara esta estructura con otro objeto.
     * Se consideran iguales si contienen los mismos elementos en el mismo orden.
     *
     * @param obj Objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj);

    /**
     * Método que devuelve una representación en cadena de la estructura.
     *
     * @return Cadena con los elementos.
     */
    @Override
    public String toString();
}