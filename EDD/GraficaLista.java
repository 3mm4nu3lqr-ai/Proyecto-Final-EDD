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

    private ListaDoblementeLigada<Vertice> vertices;
    int numVertices;
    int numAristas;

    public GraficaLista() {
        this.vertices = new ListaDoblementeLigada<>();
        this.numVertices = 0;
        this.numAristas = 0;
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
    }

    @Override
    public void eliminarArista(T e1, T e2) {
    }

    @Override
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin) {
        return new ListaDoblementeLigada<>();
    }

    @Override
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin) {
        return new ListaDoblementeLigada<>();
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
        return null;
    }
}