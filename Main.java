package archivoprincipal;

import controlador.ControladorTorneo;
import modelo.Torneo;
import vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        Torneo modelo = new Torneo();
        VistaConsola vista = new VistaConsola();
        ControladorTorneo controlador = new ControladorTorneo(modelo, vista);
        
        controlador.iniciar();
    }
}
