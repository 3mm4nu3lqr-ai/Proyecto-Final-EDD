package modelo;

import Estructuras.ListaDoblementeLigada;

/**
 * Interfaz que define el comportamiento básico de una gráfica.
 *
 * @param <T> tipo de los elementos almacenados en los vértices.
 */
public interface Grafica<T> extends Iterable<T> {

    /**
     * Método que agrega un vértice a la gráfica.
     *
     * @param elemento elemento que se agregará como vértice.
     */
    public void agregarVertice(T elemento);

    /**
     * Método que agrega una arista entre dos vértices.
     *
     * @param e1 primer vértice.
     * @param e2 segundo vértice.
     */
    public void agregarArista(T e1, T e2);
    
    /**
     * Método que agrega una arista ponderada entre dos vértices.
     *
     * @param e1 primer vértice.
     * @param e2 segundo vértice.
     * @param peso peso asociado a la arista.
     */
    public void agregarAristaPonderada(T e1, T e2, int peso);

    /**
     * Método que realiza un recorrido BFS en la gráfica.
     *
     * @param inicio vértice desde el cual inicia el recorrido.
     * @return lista con el recorrido BFS.
     */
    public ListaDoblementeLigada<T> devolverBfs(T inicio);

    /**
     * Método que realiza un recorrido DFS en la gráfica.
     *
     * @param inicio vértice desde el cual inicia el recorrido.
     * @return lista con el recorrido DFS.
     */
    public ListaDoblementeLigada<T> devolverDfs(T inicio);

    /**
     * Método que elimina un vértice de la gráfica.
     *
     * @param elemento vértice que se eliminará.
     */
    public void eliminarVertice(T elemento);

    /**
     * Método que elimina una arista entre dos vértices.
     *
     * @param e1 primer vértice.
     * @param e2 segundo vértice.
     */
    public void eliminarArista(T e1, T e2);

    /**
     * Método que obtiene la ruta más corta no ponderada entre dos vértices.
     *
     * @param inicio vértice de inicio.
     * @param fin vértice destino.
     * @return lista con la ruta más corta no ponderada.
     */
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin);
    
    /**
     * Método que obtiene la ruta más corta ponderada entre dos vértices.
     *
     * @param inicio vértice de inicio.
     * @param fin vértice destino.
     * @return lista con la ruta más corta ponderada.
     */
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin);
    
    /**
     * Método que devuelve el número total de vértices de la gráfica.
     *
     * @return número de vértices.
     */
    public int devolverNumeroDeVertices();
    
    /**
     * Método que devuelve el número total de aristas de la gráfica.
     *
     * @return número de aristas.
     */
    public int devolverNumeroDeAristas(); 
    
    /**
     * Método que devuelve las componentes conexas de la gráfica.
     *
     * @return lista de sub-gráficas, donde cada una es una componente conexa.
     */
    public ListaDoblementeLigada<Grafica<T>> devolverComponentesConexas();
}