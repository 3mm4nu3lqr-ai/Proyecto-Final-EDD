import java.util.Iterator;

import Estructuras.ListaDoblementeLigada;

public class GraficaLista <T> implements Grafica<T> {
    int numVertices;
    int numAristas;

    public void agregarVertice(T elemento){
    }

    public void agregarArista(T e1, T e2) {
    }

    public void agregarAristaPonderada(T elemento){
    }

    public ListaDoblementeLigada<T> devolverBfs(T inicio){
    }

    public ListaDoblementeLigada<T> devolverDfs(T inicio) {
    }

    public void eliminarVertice(T elemento){
    }

    public void eliminarArista(T elemento){
    }

    public ListaDoblementeLigada<T> devolverRutaMasCortaNoPonderada(T inicio, T fin){
    }
    
    public ListaDoblementeLigada<T> rutaMasCortaPonderada(T inicio, T fin){
    }

    public void Iterator(){
    }
    
    public int devolverNumeroDeVertices(){
        return numVertices;
    }
    
    public int devolverNumeroDeAristas(){
        return numAristas;
    }
}
