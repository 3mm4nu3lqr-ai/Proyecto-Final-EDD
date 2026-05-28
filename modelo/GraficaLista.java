package modelo;
import java.util.Iterator;
import Estructuras.ListaDoblementeLigada;
import Estructuras.PIlaYCola.*;

public class GraficaLista<T> implements Grafica<T> {

    private class Adyacencia {
        T nombre;
        int peso;

        public Adyacencia(T nombre, int peso) {
            this.nombre = nombre;
            this.peso = peso;
        }
    }

    private class Vertice {
        T nombre;
        ListaDoblementeLigada<Adyacencia> adyacencias;

        public Vertice(T nombre) {
            this.nombre = nombre;
            this.adyacencias = new ListaDoblementeLigada<>();
        }
    }

    private class ParPredecesor {
        T vertice;
        T predecesor;
        public ParPredecesor(T vertice, T predecesor) {
            this.vertice = vertice;
            this.predecesor = predecesor;
        }
    }

    private class ParDistancia {
        T vertice;
        int distancia;
        public ParDistancia(T vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
    }


    public ListaDoblementeLigada<Vertice> vertices;
    int numVertices;
    int numAristas;

    public GraficaLista() {
        this.vertices = new ListaDoblementeLigada<>();
        this.numVertices = 0;
        this.numAristas = 0;
    }

    public T buscarPorNombre(String nombre) {
        int totalVertices = this.numVertices;

        for(int i = 0; i < totalVertices; i++) {
            Vertice actual = (Vertice) this.vertices.acceder(i);

            if(actual.nombre.toString().trim().equalsIgnoreCase(nombre.trim())) {
                return actual.nombre;
            }
        }

        return null;
    }

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

    @Override
    public void agregarArista(T e1, T e2) {
        agregarAristaPonderada(e1, e2, 1);
    }

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

    @Override
    public void eliminarVertice(T elemento) {

        /*
        COMENTARIO RÁPIDO: Se tuvo que cambiar el método buscar que sale en el algoritmo por estas líneas de código debido a que el método buscar
        en los vertices de la gráfica espera que se le pase como parámetro un vértice, y el elemento es un objeto de tipo T, por lo que no lo
        pudimos usar.
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

    @Override
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin) {
        ListaDoblementeLigada<T> visitados = new ListaDoblementeLigada<>();
        ListaDoblementeLigada<ParPredecesor> predecesores = new ListaDoblementeLigada<>();
        Cola<Vertice> c = new Cola<>();

        Vertice vInicio = null;
        Vertice vFin = null;
        String nombreInicio = inicio.toString().trim();
        String nombreFin = fin.toString().trim();

        // 🕵️‍♂️ Imprime lo que metió el usuario:
        System.out.println("DEBUG: Buscando inicio -> [" + nombreInicio + "]");
        System.out.println("DEBUG: Buscando fin -> [" + nombreFin + "]");

        for (int i = 0; i < this.numVertices; i++) {
            Vertice v = (Vertice) this.vertices.acceder(i);
            String nombreVertice = v.nombre.toString().trim();

            // 🕵️‍♂️ Imprime las primeras 3 estaciones del grafo para ver cómo se llaman en memoria:
            if (i < 3) {
                System.out.println("DEBUG: Estación en grafo n°" + i + " -> [" + nombreVertice + "]");
            }

            if (nombreVertice.equalsIgnoreCase(nombreInicio)) {
                vInicio = v;
            }
            if (nombreVertice.equalsIgnoreCase(nombreFin)) {
                vFin = v;
            }
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

        // Reconstruimos el camino hacia atrás
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

        // Volteamos el camino usando tu Pila
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

        // Inicialización de Dijkstra
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
            // Verificar si quedan nodos no visitados
            int count = 0;
            for (T nv : noVisitados) count++;
            if (count == 0) break;

            T actual = null;
            int distMin = -1;

            // Obtener el nodo con la distancia mínima
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

            // Eliminar el actual de la lista de no visitados
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

            // Actualizar distancias de los vecinos
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

        // Verificamos si pudimos llegar al destino
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

        // Reconstruimos el camino hacia atrás y lo volteamos
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

    @Override
    public int devolverNumeroDeVertices() {
        return numVertices;
    }

    @Override
    public int devolverNumeroDeAristas() {
        return numAristas;
    }

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
}