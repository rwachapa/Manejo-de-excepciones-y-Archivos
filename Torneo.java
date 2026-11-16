package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Equipo> equipos;
    private List<List<Partido>> etapas;

    public Torneo() {
        equipos = new ArrayList<>();
        etapas = new ArrayList<>();
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public int cantidadEquipos() {
        return equipos.size();
    }

    // ✅ Verifica si un número es potencia de 2
    private boolean esPotenciaDeDos(int n) {
        return n > 1 && (n & (n - 1)) == 0;
    }

    // ✅ Método principal para iniciar el sorteo
    public void realizarSorteo() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        int n = equipos.size();
        if (!esPotenciaDeDos(n)) {
            throw new NumeroEquiposInvalidoException(
                "El número de equipos (" + n + ") no es potencia de 2. Debe ser: 2, 4, 8, 16...");
        }
        
        etapas.clear();
        List<Equipo> equiposEtapa = new ArrayList<>(equipos);
        sorteoRecursivo(equiposEtapa, etapas);
    }

    // ✅ ALGORITMO RECURSIVO — Aquí está lo más importante
    private void sorteoRecursivo(List<Equipo> equiposEtapa, List<List<Partido>> resultadoEtapas) 
            throws SorteoInvalidoException {
        
        int cantidad = equiposEtapa.size();
        
        // Caso base: queda 1 equipo = campeón
        if (cantidad == 1) {
            return;
        }

        if (!esPotenciaDeDos(cantidad)) {
            throw new SorteoInvalidoException("Cantidad inválida en etapa: " + cantidad);
        }

        // Mezclar aleatoriamente
        Collections.shuffle(equiposEtapa);

        // Crear los partidos de esta etapa
        List<Partido> partidosEtapa = new ArrayList<>();
        List<Equipo> ganadoresSimulados = new ArrayList<>();

        for (int i = 0; i < cantidad; i += 2) {
            Equipo e1 = equiposEtapa.get(i);
            Equipo e2 = equiposEtapa.get(i + 1);
            
            // Verificación: mismo equipo no puede jugar contra sí mismo
            if (e1.equals(e2)) {
                throw new SorteoInvalidoException("Un equipo no puede jugar contra sí mismo");
            }
            
            Partido partido = new Partido(e1, e2);
            partidosEtapa.add(partido);
            
            // Simulamos un ganador aleatorio para continuar con la siguiente etapa
            if (Math.random() < 0.5) {
                ganadoresSimulados.add(e1);
            } else {
                ganadoresSimulados.add(e2);
            }
        }

        resultadoEtapas.add(partidosEtapa);
        
        // Llamada recursiva con los ganadores
        sorteoRecursivo(ganadoresSimulados, resultadoEtapas);
    }

    public List<List<Partido>> getEtapas() {
        return etapas;
    }

    // ✅ Persistencia: Guardar el torneo en archivo
    public void guardarTorneo(String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(this);
        }
    }

    // ✅ Persistencia: Cargar el torneo desde archivo
    public static Torneo cargarTorneo(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Torneo) ois.readObject();
        }
    }
}