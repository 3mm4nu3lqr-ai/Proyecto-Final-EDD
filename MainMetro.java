import controlador.*;
import vista.*;

public class MainMetro {
    public static void main(String[] args) {
        VistaMetro vista = new VistaMetro();
        ControladorMetro controlador = new ControladorMetro(vista);

        controlador.iniciarGrafica();

        vista.mostrarMenu();

        controlador.iniciar();
    }
}
