package Estructuras;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación de una lista doblemente ligada genérica.
 * Permite almacenar elementos y recorrer la lista en ambos sentidos.
 * 
 * @param <T> tipo de los elementos almacenados en la lista.
 * @author Fernando Chablé Alonzo y Emmanuel Quirino Roman.
 * @date 17-Mar-2026
 */
public class ListaDoblementeLigada<T> implements Lista<T> {

     /**
      * Clase interna que representa un nodo de la lista.
      * Cada nodo contiene un elemento y referencias al nodo siguiente y anterior.
      */
     private class Nodo{
          // Elemento almacenado en el nodo.
          public T elemento;

          // Referencia al siguiente nodo.
          public Nodo siguiente;

          // Referencia al nodo anterior.
          public Nodo anterior;

          /**
           * Constructor del nodo.
           * @param e elemento a almacenar.
           */
          public Nodo(T e) {
               this.elemento = e;
          }
     }

     // Iterador para recorrer la lista doblemente ligada.
     private class IteradorDoubleLinkedList implements Iterator<T> {

          // Nodo anterior al actual.
          public Nodo anterior;

          // Nodo siguiente en el recorrido.
          public Nodo siguiente;

          /**
           * Constructor del iterador.
           * Inicializa el recorrido desde la cabeza.
           */
          public IteradorDoubleLinkedList() {
               siguiente = cabeza;
          }

          /**
           * Método que indica si hay un siguiente elemento.
           * 
           * @return true si existe un siguiente nodo, false en otro caso.
           */
          @Override
          public boolean hasNext() {
               return siguiente != null;
          }

          /**
           * Método que devuelve el siguiente elemento en la iteración.
           * 
           * @return el siguiente elemento.
           * @throws NoSuchElementException si no hay más elementos.
           */
          @Override
          public T next() {
               if (siguiente == null) {
                    throw new NoSuchElementException("El elemento es null");
               }
               anterior = siguiente;
               siguiente = siguiente.siguiente;
               return anterior.elemento;
          }
     }

     // Nodo cabeza de la lista.
     private Nodo cabeza;

     // Nodo rabo de la lista.
     private Nodo rabo;

     // Longitud de la lista.
     private int longitud;

     /**
      * Método que devuelve un iterador para la lista.
      * @return iterador de la lista.
      */
     public Iterator<T> iterator() {
          return new IteradorDoubleLinkedList();
     }

     /**
      * Constructor de la lista doblemente ligada.
      * Inicializa una lista vacía.
      */
     public ListaDoblementeLigada(){
          this.cabeza = null;
          this.rabo = null;
          this.longitud = 0;
     }

     /**
      * Método que agrega un elemento al inicio de la lista.
      * 
      * @param elemento elemento a agregar.
      * @throws IllegalArgumentException si el elemento es null.
      */
     @Override
     public void agregar(T elemento) throws IllegalArgumentException {
          /*Aqui va tu código*/
          if (elemento == null) {
               throw new IllegalArgumentException("Es un elemento null.");
          }

          Nodo nuevo = new Nodo(elemento);

          if (longitud == 0) {
               cabeza = nuevo;
               rabo = nuevo;
               longitud++;
               return;
          } else {
               nuevo.siguiente = cabeza;
               cabeza.anterior = nuevo;
               cabeza = nuevo;
               longitud++;
          }
     }

     /**
      * Método que elimina la primera aparición de un elemento en la lista.
      * @param elemento elemento a eliminar.
      */
     @Override
     public void eliminar(T elemento){
          /*Aqui va tu código*/
          if (elemento == null || longitud == 0) {
               return;
          }

          Nodo otroNodo = cabeza;

          while (otroNodo != null) {
               if (otroNodo.elemento.equals(elemento)) {
                    if(otroNodo == cabeza){
                         cabeza = cabeza.siguiente;
                         if (cabeza != null) {
                              cabeza.anterior = null;
                         } else {
                              rabo = null;
                         }
                    } else if (otroNodo == rabo) {
                         rabo = rabo.anterior;
                         rabo.siguiente = null;
                    } else {
                         otroNodo.anterior.siguiente = otroNodo.siguiente;
                         otroNodo.siguiente.anterior = otroNodo.anterior;
                    } 
                    longitud--;
                    return;
               }
               otroNodo = otroNodo.siguiente;
          }
     }

     /**
      * Método que busca un elemento en la lista.
      * @param elemento elemento a buscar.
      * @return true si el elemento está en la lista, false en caso contrario.
      */
     @Override
     public boolean buscar(T elemento) {
          /*Aqui va tu código*/
          Nodo nuevoNodo = cabeza;

          while (nuevoNodo != null) {
               if (nuevoNodo.elemento.equals(elemento)) {
                    return true;
               }
               nuevoNodo = nuevoNodo.siguiente;
          }
          return false;
     }

     /**
      * Método que elimina el elemento en la posición i.
      * @param i índice del elemento a eliminar.
      */
     @Override
     public void eliminar(int i) {
          /*Aqui va tu código*/
          Nodo nodoAEliminar = accederNodo(i);

          if (nodoAEliminar == cabeza) {
               cabeza = cabeza.siguiente;
               if (cabeza != null) {
                    cabeza.anterior = null;
               } else {
                    rabo = null;
               }
          } else if (nodoAEliminar == rabo) {
               rabo = rabo.anterior;
               rabo.siguiente = null;
          } else {
               nodoAEliminar.anterior.siguiente = nodoAEliminar.siguiente;
               nodoAEliminar.siguiente.anterior = nodoAEliminar.anterior;
          }
          longitud--;
     }

     /**
      * Método que accede al elemento en la posición i.
      * 
      * @param i índice del elemento.
      * @return elemento en la posición i.
      * @throws IllegalArgumentException si el índice es inválido.
      */
     @Override
     public T acceder(int i) throws IllegalArgumentException {
          /*Aqui va tu código*/
          return accederNodo(i).elemento;
     }

     /**
      * Método que devuelve el índice de la primera aparición de un elemento.
      * 
      * @param elemento elemento a buscar.
      * @return índice del elemento.
      * @throws IllegalArgumentException si el elemento no está en la lista.
      */
     @Override
     public int devolverIndiceElemento(T elemento) throws IllegalArgumentException{
          /*Aqui va tu código*/
          if (!buscar(elemento)) {
               throw new IllegalArgumentException("El elemento no se encuentra en la lista.");
          }

          int contador = 0;

          for(T aux : this) {
               if (elemento.equals(aux)) {
                    return contador;
               }
               contador++;
          }       
          return -1;
     }


     /**
      * Método que devuelve la longitud de la lista.
      * @return número de elementos.
      */
     @Override
     public int devolverLongitud(){
          /*Aqui va tu código*/
          return longitud;
     }

     /**
      * Método que agrega un elemento al final de la lista.
      * 
      * @param elemento elemento a agregar.
      * @throws IllegalArgumentException si el elemento es null.
      */
     public void agregarFinal(T elemento) throws IllegalArgumentException {
          /*Aqui va tu código*/
          if (elemento == null) {
               throw new IllegalArgumentException("El elemento es nulo");
          }

          Nodo nuevo = new Nodo(elemento);

          if (longitud == 0) {
               cabeza = nuevo;
               rabo = nuevo;
               longitud++;
               return;
          }

          rabo.siguiente = nuevo;
          nuevo.anterior = rabo;
          rabo = nuevo;
          longitud++;

     }

     /**
      * Método que devuelve una nueva lista con los elementos en orden inverso.
      * @return lista invertida.
      */
     public ListaDoblementeLigada<T> reversa(){
          /*Aqui va tu código*/
          ListaDoblementeLigada<T> reversa = new ListaDoblementeLigada<>();

          Nodo actual = rabo;

          while (actual != null) {
               reversa.agregarFinal(actual.elemento);
               actual = actual.anterior;
          }

          return reversa;
     }

     /**
      * Método que accede al nodo en la posición i.
      * 
      * @param i índice del nodo.
      * @return nodo en la posición i.
      * @throws IllegalArgumentException si el índice es inválido.
      */
     private Nodo accederNodo(int i) {
          /*Aqui va tu código*/
          if (i < 0 || i >= longitud) {
               throw new IllegalArgumentException("Índice no válido.");
          }

          Nodo actual;

          int contador;

          if (i < (longitud / 2)) {
               actual = cabeza;
               contador = 0;
               while (contador < i) {
                    actual = actual.siguiente;
                    contador++;
               }
               return actual;
          } else {
               actual = rabo;
               contador = longitud - 1;
               while (contador > i) {
                    actual = actual.anterior;
                    contador--;
               }
               return actual;
          }

     }

     /**
      * Método que devuelve una representación en cadena de la lista.
      * @return cadena con los elementos de la lista.
      */
     public String toString(){
          String s = "[";
          int cont = 0;
          for(T elem : this){
               if(cont == 0){
                    s = s + elem.toString();
                    cont++;
               }else{
                    s = s + "," +elem.toString();
               }
          }
          s = s + "]";
          return s;
     }
}