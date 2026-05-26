package Estructuras;
/**
 * La interfaz Lista define el comportamiento de una lista genérica de elementos.
 * Extiende la interfaz Coleccion, por lo que incluye los métodos para agregar, eliminar y buscar elementos.
 *
 * Agrega operaciones específicas de una lista, como acceder a elementos mediante un índice y conocer la longitud de la estructura.
 *
 * @param <T> el tipo de elementos que almacena la lista
 * @date 17-Mar-2026
 */
public interface Lista<T> extends Coleccion<T>{

    /**
     * Método que elimina el elemento que se encuentra en la posición indicada.
     *
     * @param indice la posición del elemento que se desea eliminar
     * @throws IndexOutOfBoundsException si el índice está fuera de los límites de la lista.
     */
    public void eliminar(int indice);

    /**
     * Método que devuelve el elemento almacenado en la posición indicada.
     *
     * @param indice la posición del elemento que se desea obtener.
     * @return el elemento que se encuentra en la posición especificada.
     * @throws IndexOutOfBoundsException si el índice está fuera de los límites de la lista.
     */
    public T acceder(int indice);

    /**
     * Método que devuelve el índice de la primera aparición de un elemento dentro de la lista.
     *
     * @param elemento el elemento cuyo índice se desea obtener.
     * @return el índice del elemento si se encuentra en la lista, o -1 si el elemento no existe en la lista.
     */
    public int devolverIndiceElemento(T elemento);

    /**
     * Método que devuelve la cantidad de elementos almacenados en la lista.
     *
     * @return el número total de elementos en la lista.
     */
    public int devolverLongitud();

}