package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Equipo;
import modelo.NumeroEquiposInvalidoException;
import modelo.SorteoInvalidoException;
import modelo.Torneo;
import vista.VistaConsola;

public class ControladorTorneo {
    private Torneo modelo;
    private VistaConsola vista;
    private static final String ARCHIVO_TORNEO = "torneo.ser";

    public ControladorTorneo(Torneo modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        vista.mostrarMensaje("⚽ SISTEMA DE SORTEO DE TORNEO DE FÚTBOL");
        
        int opcion;
        do {
            vista.mostrarMensaje("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Crear nuevo torneo con 16 equipos");
            System.out.println("2. Realizar sorteo");
            System.out.println("3. Guardar torneo");
            System.out.println("4. Cargar torneo");
            System.out.println("5. Salir");
            
            opcion = vista.leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    crearTorneoPrueba();
                    break;
                case 2:
                    realizarSorteo();
                    break;
                case 3:
                    guardarTorneo();
                    break;
                case 4:
                    cargarTorneo();
                    break;
                case 5:
                    vista.despedirse();
                    break;
                default:
                    vista.mostrarError("Opción no válida");
            }
        } while (opcion != 5);
    }

    private void crearTorneoPrueba() {
        modelo = new Torneo();
        
        // 16 equipos de ejemplo
        String[][] datosEquipos = {
            {"Barcelona SC", "Guayaquil"},
            {"Emelec", "Guayaquil"},
            {"LDU Quito", "Quito"},
            {"Universidad Católica", "Quito"},
            {"Independiente del Valle", "Sangolquí"},
            {"CS Emelec", "Milagro"},
            {"Deportivo Cuenca", "Cuenca"},
            {"Macará", "Ambato"},
            {"El Nacional", "Quito"},
            {"Deportivo Quito", "Quito"},
            {"Olmedo", "Riobamba"},
            {"Mushuc Runa", "Ambato"},
            {"Delfín", "Manta"},
            {"Aucas", "Quito"},
            {"Gualaceo", "Gualaceo"},
            {"Orense", "Machala"}
        };
        
        for (String[] equipo : datosEquipos) {
            modelo.agregarEquipo(new Equipo(equipo[0], equipo[1]));
        }
        
        List<String> nombres = new ArrayList<>();
        for (Equipo e : modelo.getEquipos()) {
            nombres.add(e.getNombre());
        }
        vista.mostrarEquipos(nombres);
        vista.mostrarMensaje("✅ Torneo creado con " + modelo.cantidadEquipos() + " equipos");
    }

    private void realizarSorteo() {
        if (modelo.cantidadEquipos() == 0) {
            vista.mostrarError("Primero cree un torneo con equipos");
            return;
        }
        
        try {
            modelo.realizarSorteo();
            vista.mostrarEtapas(modelo.getEtapas());
        } catch (NumeroEquiposInvalidoException e) {
            vista.mostrarError(e.getMessage());
        } catch (SorteoInvalidoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void guardarTorneo() {
        try {
            modelo.guardarTorneo(ARCHIVO_TORNEO);
            vista.mostrarMensaje("✅ Torneo guardado correctamente en " + ARCHIVO_TORNEO);
        } catch (IOException e) {
            vista.mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    private void cargarTorneo() {
        try {
            Torneo cargado = Torneo.cargarTorneo(ARCHIVO_TORNEO);
            this.modelo = cargado;
            vista.mostrarMensaje("✅ Torneo cargado correctamente");
            List<String> nombres = new ArrayList<>();
            for (Equipo e : modelo.getEquipos()) {
                nombres.add(e.getNombre());
            }
            vista.mostrarEquipos(nombres);
        } catch (IOException e) {
            vista.mostrarError("Error al leer el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            vista.mostrarError("Clase no encontrada: " + e.getMessage());
        }
    }
}