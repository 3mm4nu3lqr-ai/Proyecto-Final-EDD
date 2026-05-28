package modelo;
import java.util.Iterator;
import Estructuras.ListaDoblementeLigada;
import Estructuras.PIlaYCola.*;

/**
 * Clase en la cual se representa una gráfica implementada mediante listas de adyacencias.
 * Se almacenan vértices genéricos y las conexiones (aristas) entre ellos, ya sean ponderadas o no ponderadas.
 * 
 * @param <T> Tipo de dato que se almacenará en cada vértice de la gráfica.
 * @date 27-Mayo-2026
 * @author Fernando Chablé Alonso, Pablo de Jesús Peréz Megchun y Emmanuel Quirino Roman
 */
public class GraficaLista<T> implements Grafica<T> {

    /**
     * Clase interna en la que se representa una conexión (arista) hacia otro vértice dentro de la gráfica.
     */
    private class Adyacencia {
        /** El identificador del vértice al que se dirige la conexión. */
        T nombre;
        /** El peso, costo o tiempo que se toma al transitar por esta arista. */
        int peso;

        /**
         * Constructor de la clase Adyacencia.
         * Se inicializa una nueva adyacencia con su vértice destino y peso.
         * 
         * @param nombre El vértice destino de la arista.
         * @param peso   El peso asociado a la conexión.
         */
        public Adyacencia(T nombre, int peso) {
            this.nombre = nombre;
            this.peso = peso;
        }
    }

    /**
     * Clase interna en la que se representa un vértice dentro de la estructura de la gráfica.
     */
    private class Vertice {
        /** El elemento que se almacena y que define al vértice. */
        T nombre;
        /** Lista en la que se guardan las adyacencias (aristas) que salen de este vértice. */
        ListaDoblementeLigada<Adyacencia> adyacencias;

        /**
         * Constructor de la clase Vertice.
         * Se inicializa un nuevo vértice con su nombre y una lista vacía de adyacencias.
         * 
         * @param nombre El nombre o valor del vértice.
         */
        public Vertice(T nombre) {
            this.nombre = nombre;
            this.adyacencias = new ListaDoblementeLigada<>();
        }
    }

    /**
     * Clase auxiliar en la cual se reconstruyen las rutas en los algoritmos de búsqueda.
     * Se almacena la relación entre un vértice y el vértice anterior del cual se provino.
     */
    private class ParPredecesor {
        /** El vértice que se evalúa actualmente. */
        T vertice;
        /** El vértice del cual se provino en el recorrido. */
        T predecesor;
        
        /**
         * Constructor de la clase ParPredecesor.
         * 
         * @param vertice    El vértice actual.
         * @param predecesor El vértice predecesor.
         */
        public ParPredecesor(T vertice, T predecesor) {
            this.vertice = vertice;
            this.predecesor = predecesor;
        }
    }

    /**
     * Clase auxiliar que se utiliza para el algoritmo de caminos más cortos.
     * Se almacena la distancia mínima acumulada para llegar a un vértice específico.
     */
    private class ParDistancia {
        /** El vértice que se evalúa. */
        T vertice;
        /** La distancia mínima que se calcula hacia el vértice. */
        int distancia;
        
        /**
         * Constructor de la clase ParDistancia.
         * 
         * @param vertice   El vértice evaluado.
         * @param distancia La distancia mínima hacia el vértice.
         */
        public ParDistancia(T vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
    }

    /** La lista principal en la que se contienen todos los vértices de la gráfica. */
    public ListaDoblementeLigada<Vertice> vertices;
    
    /** El número total de vértices que se registran en la gráfica. */
    int numVertices;
    
    /** El número total de aristas que se registran en la gráfica. */
    int numAristas;

    /**
     * Constructor de la clase GraficaLista.
     * Se inicializa una gráfica vacía sin vértices ni aristas.
     */
    public GraficaLista() {
        this.vertices = new ListaDoblementeLigada<>();
        this.numVertices = 0;
        this.numAristas = 0;
    }

    /**
     * Método en el cual se agrega un nuevo vértice a la estructura de la gráfica.
     * 
     * @param elemento El elemento que se registrará como vértice.
     */
    @Override
    public void agregarVertice(T elemento) {
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(elemento)) {
                throw new IllegalArgumentException();
            }
        }

        Vertice nuevoVertice = new Vertice(elemento);
        this.vertices.agregarFinal(nuevoVertice);
        this.numVertices++;
    }

    /**
     * Método en el cual se agrega una arista no ponderada (se asigna un peso de 1 por defecto) entre dos vértices.
     * 
     * @param e1 El primer vértice que se conectará.
     * @param e2 El segundo vértice que se conectará.
     */
    @Override
    public void agregarArista(T e1, T e2) {
        agregarAristaPonderada(e1, e2, 1);
    }

    /**
     * Método en el cual se agrega una arista ponderada bidireccional entre dos vértices.
     * 
     * @param e1   El primer vértice que se conectará.
     * @param e2   El segundo vértice que se conectará.
     * @param peso El peso, costo o distancia que se asigna a la conexión.
     */
    @Override
    public void agregarAristaPonderada(T e1, T e2, int peso) {
        if (e1.equals(e2)) {
            throw new IllegalArgumentException();
        }
        if (peso <= 0) {
            throw new IllegalArgumentException();
        }

        Vertice vertice1 = null;
        Vertice vertice2 = null;

        for (Vertice v : this.vertices) {
            if (v.nombre.equals(e1)) vertice1 = v;
            if (v.nombre.equals(e2)) vertice2 = v;
        }

        if (vertice1 == null || vertice2 == null) {
            throw new IllegalArgumentException();
        }

        for (Adyacencia ady : vertice1.adyacencias) {
            if (ady.nombre.equals(e2)) {
                return;
            }
        }

        Adyacencia adyHaciaE2 = new Adyacencia(e2, peso);
        Adyacencia adyHaciaE1 = new Adyacencia(e1, peso);

        vertice1.adyacencias.agregarFinal(adyHaciaE2);
        vertice2.adyacencias.agregarFinal(adyHaciaE1);

        this.numAristas++;
    }

    /**
     * Método en el cual se realiza un recorrido a lo ancho (BFS) comenzando desde el vértice indicado.
     * 
     * @param inicio El vértice a partir del cual se comenzará la búsqueda.
     * @return Una lista con los vértices ordenados conforme se visitaron.
     */
    @Override
    public ListaDoblementeLigada<T> devolverBfs(T inicio) {
        ListaDoblementeLigada<T> recorrido = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<T> visitados = new ListaDoblementeLigada<>();
        Cola<Vertice> c = new Cola<>();

        Vertice vInicio = null;
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(inicio)) {
                vInicio = v;
                break;
            }
        }

        if (vInicio == null) {
            throw new IllegalArgumentException();
        }

        c.meter(vInicio);
        visitados.agregarFinal(vInicio.nombre);

        while (!c.estaVacia()) {
            Vertice actual = c.sacar();
            recorrido.agregarFinal(actual.nombre);

            for (Adyacencia ady : actual.adyacencias) {
                boolean visitado = false;
                for (T visit : visitados) {
                    if (visit.equals(ady.nombre)) {
                        visitado = true;
                        break;
                    }
                }

                if (!visitado) {
                    visitados.agregarFinal(ady.nombre);
                    for (Vertice v : this.vertices) {
                        if (v.nombre.equals(ady.nombre)) {
                            c.meter(v);
                            break;
                        }
                    }
                }
            }
        }

        return recorrido;
    }

    /**
     * Método en el cual se realiza un recorrido en profundidad (DFS) comenzando desde el vértice indicado.
     * 
     * @param inicio El vértice a partir del cual se comenzará la búsqueda.
     * @return Una lista con los vértices ordenados conforme se visitaron.
     */
    @Override
    public ListaDoblementeLigada<T> devolverDfs(T inicio) {
        ListaDoblementeLigada<T> recorrido = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<T> visitados = new ListaDoblementeLigada<>();
        Pila<Vertice> p = new Pila<>();

        Vertice vInicio = null;
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(inicio)) {
                vInicio = v;
                break;
            }
        }

        if (vInicio == null) {
            throw new IllegalArgumentException();
        }

        p.meter(vInicio);

        while (!p.estaVacia()) {
            Vertice actual = p.sacar();

            boolean visitadoActual = false;
            for (T visit : visitados) {
                if (visit.equals(actual.nombre)) {
                    visitadoActual = true;
                    break;
                }
            }

            if (!visitadoActual) {
                visitados.agregarFinal(actual.nombre);
                recorrido.agregarFinal(actual.nombre);

                for (Adyacencia ady : actual.adyacencias) {
                    boolean visitado = false;
                    for (T visit : visitados) {
                        if (visit.equals(ady.nombre)) {
                            visitado = true;
                            break;
                        }
                    }

                    if (!visitado) {
                        for (Vertice v : this.vertices) {
                            if (v.nombre.equals(ady.nombre)) {
                                p.meter(v);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return recorrido;
    }

    /**
     * Método en el cual se elimina un vértice del grafo junto con todas las aristas que se conectaban a él.
     * 
     * @param elemento El vértice que se desea eliminar de la gráfica.
     */
    @Override
    public void eliminarVertice(T elemento) {

        /*
        COMENTARIO RÁPIDO: Se tuvo que modificar la búsqueda del algoritmo original en estas líneas de código,
        debido a que en el método buscar de la gráfica se espera recibir un vértice como parámetro,
        y el elemento proporcionado es un objeto de tipo T, por lo que no fue posible utilizarlo directamente.
         */

        Vertice verticeAEliminar = null;
        int kVertice = 0;

        for(Vertice vertice : this.vertices) {
            if(vertice.nombre.equals(elemento)) {
                verticeAEliminar = vertice;
                break;
            }
        }

        if(verticeAEliminar == null) {
            throw new IllegalArgumentException("El vértice no existe.");
        }

        for(Vertice actual : this.vertices) {
            Adyacencia adyacencia = null;
            int kAdyacencia = 0;
            int indiceAdyacencia = -1;

            for(Adyacencia ady : actual.adyacencias) {
                if(ady.nombre.equals(elemento)) {
                    adyacencia = ady;
                    indiceAdyacencia = kAdyacencia;
                    break;
                }
                kAdyacencia++;
            }

            if(adyacencia != null) {
                actual.adyacencias.eliminar(indiceAdyacencia);
                this.numAristas--;
            }
        }

        kVertice = this.vertices.devolverIndiceElemento(verticeAEliminar);
        this.vertices.eliminar(kVertice);
        this.numVertices--;
    }

    /**
     * Método en el cual se elimina la conexión (arista) directa entre dos vértices.
     * 
     * @param e1 El primer vértice de la conexión.
     * @param e2 El segundo vértice de la conexión.
     */
    @Override
    public void eliminarArista(T e1, T e2) {
    
        Vertice uVertice = null;
        Vertice vVertice = null;
    
        int i = 0, j = 0;
        int indiceU = -1;
        int indiceV = -1;

    
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(e1)) {
                uVertice = v;
                indiceU = i;
            }
            if (v.nombre.equals(e2)) {
                vVertice = v;
                indiceV = j;
            }
            i++;
            j++;
        }

        if (uVertice == null || vVertice == null) {
            throw new IllegalArgumentException("Alguno o ambos extremos de la arista no existen.");
        }

        int k = -1;
        int l = -1;
    
        int contador = 0;
        for (Adyacencia actual : uVertice.adyacencias) {
            if (actual.nombre.equals(e2)) {
                k = contador;
                break;
            }
            contador++;
        }

        contador = 0;
        for (Adyacencia actual : vVertice.adyacencias) {
            if (actual.nombre.equals(e1)) {
                l = contador;
                break;
            }
            contador++;
        }

        if (k == -1 || l == -1) {
            throw new IllegalArgumentException("La arista entre ambos vértices no existe.");
        }

        uVertice.adyacencias.eliminar(k);
        vVertice.adyacencias.eliminar(l);

        this.numAristas--;
    }

    /**
     * Método en el cual se encuentra el camino más corto (menor número de estaciones/vértices) entre un origen y un destino.
     * 
     * @param inicio El vértice de partida.
     * @param fin    El vértice destino.
     * @return Una lista enlazada con la secuencia de vértices que conforman la ruta óptima.
     */
    @Override
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin) {
        ListaDoblementeLigada<T> visitados = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<ParPredecesor> predecesores = new ListaDoblementeLigada<>();
        Cola<Vertice> c = new Cola<>();

        Vertice vInicio = null;
        Vertice vFin = null;
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(inicio)) vInicio = v;
            if (v.nombre.equals(fin)) vFin = v;
        }

        if (vInicio == null || vFin == null) {
            throw new IllegalArgumentException("Alguna de las estaciones indicadas no existe.");
        }

        c.meter(vInicio);
        visitados.agregarFinal(vInicio.nombre);
        predecesores.agregarFinal(new ParPredecesor(vInicio.nombre, null));

        boolean encontrado = false;

        while (!c.estaVacia() && !encontrado) {
            Vertice actual = c.sacar();

            for (Adyacencia ady : actual.adyacencias) {
                boolean visitado = false;
                for (T visit : visitados) {
                    if (visit.equals(ady.nombre)) {
                        visitado = true;
                        break;
                    }
                }

                if (!visitado) {
                    visitados.agregarFinal(ady.nombre);
                    predecesores.agregarFinal(new ParPredecesor(ady.nombre, actual.nombre));

                    if (ady.nombre.equals(fin)) {
                        encontrado = true;
                        break;
                    } else {
                        for (Vertice v : this.vertices) {
                            if (v.nombre.equals(ady.nombre)) {
                                c.meter(v);
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("No hay una ruta disponible entre esas estaciones.");
        }

        // Se reconstruye el camino hacia atrás
        ListaDoblementeLigada<T> caminoInverso = new ListaDoblementeLigada<>();
        T nodoActual = fin;
        while (nodoActual != null) {
            caminoInverso.agregarFinal(nodoActual);
            
            T pred = null;
            for (ParPredecesor p : predecesores) {
                if (p.vertice.equals(nodoActual)) {
                    pred = p.predecesor;
                    break;
                }
            }
            nodoActual = pred;
        }

        // Se invierte el orden del camino usando la estructura Pila
        ListaDoblementeLigada<T> camino = new ListaDoblementeLigada<>();
        Pila<T> pilaReversa = new Pila<>();
        for (T nodo : caminoInverso) {
            pilaReversa.meter(nodo);
        }
        while (!pilaReversa.estaVacia()) {
            camino.agregarFinal(pilaReversa.sacar());
        }

        return camino;
    }

    /**
     * Método en el cual se encuentra la ruta de menor costo (ruta más rápida en tiempo) entre un origen y un destino.
     * 
     * @param inicio El vértice de origen.
     * @param fin    El vértice destino.
     * @return Una lista enlazada con la secuencia de vértices que representan el camino de menor peso.
     */
    @Override
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin) {
        Vertice vInicio = null;
        Vertice vFin = null;
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(inicio)) vInicio = v;
            if (v.nombre.equals(fin)) vFin = v;
        }

        if (vInicio == null || vFin == null) {
            throw new IllegalArgumentException("Alguna de las estaciones indicadas no existe.");
        }

        ListaDoblementeLigada<ParDistancia> distancias = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<ParPredecesor> predecesores = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<T> noVisitados = new ListaDoblementeLigada<>();

        // Se inicializan los datos para el algoritmo de Dijkstra
        for (Vertice v : this.vertices) {
            if (v.nombre.equals(inicio)) {
                distancias.agregarFinal(new ParDistancia(v.nombre, 0));
            } else {
                distancias.agregarFinal(new ParDistancia(v.nombre, -1)); // -1 representa infinito
            }
            predecesores.agregarFinal(new ParPredecesor(v.nombre, null));
            noVisitados.agregarFinal(v.nombre);
        }

        while (true) {
            // Se verifica si quedan nodos no visitados
            int count = 0;
            for (T nv : noVisitados) count++;
            if (count == 0) break;

            T actual = null;
            int distMin = -1;

            // Se obtiene el nodo con la distancia mínima
            for (T nv : noVisitados) {
                int d = -1;
                for (ParDistancia pd : distancias) {
                    if (pd.vertice.equals(nv)) {
                        d = pd.distancia;
                        break;
                    }
                }
                if (d != -1) {
                    if (distMin == -1 || d < distMin) {
                        distMin = d;
                        actual = nv;
                    }
                }
            }

            if (actual == null) {
                break; // No hay más nodos alcanzables
            }

            // Se elimina el actual de la lista de no visitados
            int idx = 0;
            int idxAEliminar = -1;
            for (T nv : noVisitados) {
                if (nv.equals(actual)) {
                    idxAEliminar = idx;
                    break;
                }
                idx++;
            }
            if (idxAEliminar != -1) {
                noVisitados.eliminar(idxAEliminar);
            }

            Vertice vActual = null;
            for (Vertice v : this.vertices) {
                if (v.nombre.equals(actual)) {
                    vActual = v;
                    break;
                }
            }

            // Se actualizan las distancias de los vecinos
            for (Adyacencia ady : vActual.adyacencias) {
                boolean estaEnNoVisitados = false;
                for (T nv : noVisitados) {
                    if (nv.equals(ady.nombre)) {
                        estaEnNoVisitados = true;
                        break;
                    }
                }

                if (estaEnNoVisitados) {
                    int distAlt = distMin + ady.peso;

                    int distVecino = -1;
                    for (ParDistancia pd : distancias) {
                        if (pd.vertice.equals(ady.nombre)) {
                            distVecino = pd.distancia;
                            break;
                        }
                    }

                    if (distVecino == -1 || distAlt < distVecino) {
                        for (ParDistancia pd : distancias) {
                            if (pd.vertice.equals(ady.nombre)) {
                                pd.distancia = distAlt;
                                break;
                            }
                        }
                        for (ParPredecesor pp : predecesores) {
                            if (pp.vertice.equals(ady.nombre)) {
                                pp.predecesor = actual;
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Se verifica si fue posible llegar al destino
        int distFin = -1;
        for (ParDistancia pd : distancias) {
            if (pd.vertice.equals(fin)) {
                distFin = pd.distancia;
                break;
            }
        }

        if (distFin == -1) {
            throw new IllegalArgumentException("No hay una ruta disponible entre esas estaciones.");
        }

        // Se reconstruye el camino hacia atrás y se invierte
        ListaDoblementeLigada<T> caminoInverso = new ListaDoblementeLigada<>();
        T nodoActual = fin;
        while (nodoActual != null) {
            caminoInverso.agregarFinal(nodoActual);
            
            T pred = null;
            for (ParPredecesor p : predecesores) {
                if (p.vertice.equals(nodoActual)) {
                    pred = p.predecesor;
                    break;
                }
            }
            nodoActual = pred;
        }

        ListaDoblementeLigada<T> camino = new ListaDoblementeLigada<>();
        Pila<T> pilaReversa = new Pila<>();
        for (T nodo : caminoInverso) {
            pilaReversa.meter(nodo);
        }
        while (!pilaReversa.estaVacia()) {
            camino.agregarFinal(pilaReversa.sacar());
        }

        return camino;
    }

    /**
     * Método en el cual se consulta el total de vértices en la estructura.
     * 
     * @return El número entero equivalente a la cantidad de vértices.
     */
    @Override
    public int devolverNumeroDeVertices() {
        return numVertices;
    }

    /**
     * Método en el cual se consulta el total de aristas conectadas en la estructura.
     * 
     * @return El número entero equivalente a la cantidad de aristas.
     */
    @Override
    public int devolverNumeroDeAristas() {
        return numAristas;
    }

    /**
     * Método en el cual se proporciona un iterador para recorrer todos los vértices de la gráfica.
     * 
     * @return Un objeto Iterator para la gráfica.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<Vertice> iter = vertices.iterator();

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public T next() {
                return iter.next().nombre;
            }
        };
    }

    /**
     * Método en el cual se encuentran y devuelven todos los subgrafos aislados (componentes conexas) dentro de la gráfica.
     * 
     * @return Una lista enlazada que contiene cada una de las sub-gráficas que se detectan.
     */
    @Override
    public ListaDoblementeLigada<Grafica<T>> devolverComponentesConexas() {
        if (this.numVertices == 0) {
            throw new IllegalArgumentException("La gráfica no tiene vértices.");
        }

        ListaDoblementeLigada<Grafica<T>> compConexas = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<T> visitados = new ListaDoblementeLigada<>();

        for (Vertice nodoVerticeG : this.vertices) {
            T nombre = nodoVerticeG.nombre;

            boolean estaVisitado = false;
            for (T v : visitados) {
                if (v.equals(nombre)) {
                    estaVisitado = true;
                    break;
                }
            }

            if (!estaVisitado) {
                GraficaLista<T> gPrima = devolverComponenteAux(nombre);
                compConexas.agregarFinal(gPrima);

                ListaDoblementeLigada<T> nuevosVisitados = devolverBfs(nombre);
                for (T nombreNodoActual : nuevosVisitados) {
                    visitados.agregarFinal(nombreNodoActual);
                }
            }
        }

        return compConexas;
    }

    /**
     * Método auxiliar en el cual se crea una sub-gráfica copiando los nodos y conexiones de una componente específica.
     * 
     * @param v El vértice inicial que servirá de semilla para detectar la componente.
     * @return La nueva gráfica que contiene los datos que se replicaron.
     */
    private GraficaLista<T> devolverComponenteAux(T v) {
        boolean existeV = false;
        for (Vertice vert : this.vertices) {
            if (vert.nombre.equals(v)) {
                existeV = true;
                break;
            }
        }
        
        if (!existeV) {
            throw new IllegalArgumentException("El vértice no existe.");
        }

        GraficaLista<T> gPrima = new GraficaLista<>();
        ListaDoblementeLigada<T> verticesComponente = devolverBfs(v);

        for (T verticeNombre : verticesComponente) {
            gPrima.agregarVertice(verticeNombre);
        }

        for (T uNombre : verticesComponente) {
            Vertice uVerticePri = null;
            
            for (Vertice vert : this.vertices) {
                if (vert.nombre.equals(uNombre)) {
                    uVerticePri = vert;
                    break;
                }
            }

            if (uVerticePri != null) {
                for (Adyacencia nodoAdyaOri : uVerticePri.adyacencias) {
                    T vNombre = nodoAdyaOri.nombre;
                    int pesoArista = nodoAdyaOri.peso;

                    if (uNombre.toString().compareTo(vNombre.toString()) < 0) {
                        gPrima.agregarAristaPonderada(uNombre, vNombre, pesoArista);
                    }
                }
            }
        }

        return gPrima;
    }
}