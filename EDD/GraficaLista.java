import java.util.Iterator;
import Estructuras.ListaDoblementeLigada;

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
    public void agregarVertice(T elemento){
        // Algoritmo 1.1
    }

    @Override
    public void agregarArista(T e1, T e2) {
        agregarAristaPonderada(e1, e2, 1);
    }

    @Override
    public void agregarAristaPonderada(T e1, T e2, int peso){
        // Algoritmo 1.2
    }

    @Override
    public ListaDoblementeLigada<T> devolverBfs(T inicio){
        // Algoritmo 1.3
        return new ListaDoblementeLigada<>(); 
    }

    @Override
    public ListaDoblementeLigada<T> devolverDfs(T inicio) {
        // Algoritmo 1.4
        return new ListaDoblementeLigada<>(); 
    }

    @Override
    public void eliminarVertice(T elemento){
        // Algoritmo 1.5
    }

    @Override
    public void eliminarArista(T e1, T e2){
        // Algoritmo 1.6
    }

    @Override
    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin){
        // Algoritmo 1.7
        return new ListaDoblementeLigada<>(); 
    }
    
    @Override
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin){
        // Algoritmo 1.8 (Dijkstra)
        return new ListaDoblementeLigada<>(); 
    }

    @Override
    public int devolverNumeroDeVertices(){
        return numVertices;
    }
    
    @Override
    public int devolverNumeroDeAristas(){
        return numAristas;
    }

    @Override
    public Iterator<T> iterator() {
        return null; 
    }
}