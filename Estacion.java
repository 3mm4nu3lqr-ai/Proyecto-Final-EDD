public class Estacion {
    
    public String nombre;
    public boolean estaCerrada;

    public Estacion(String nombre, boolean estaCerrada) {
        this.nombre = nombre;
        this.estaCerrada = estaCerrada;
    }

    @Override
    public boolean equals(Estacion otro) {
        if(this.nombre.equals(otro.nombre)) {
            return true;
        } else {
            return false;
        }
    }

}
