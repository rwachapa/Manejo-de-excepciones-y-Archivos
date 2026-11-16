package vista;

import java.util.List;
import java.util.Scanner;
import modelo.Partido;

public class VistaConsola {
    private Scanner scanner;

    public VistaConsola() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("\n❌ ERROR: " + error);
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar el salto de línea
        return valor;
    }

    public void mostrarEquipos(List<String> nombresEquipos) {
        System.out.println("\n✅ Equipos registrados:");
        for (String nombre : nombresEquipos) {
            System.out.println("  - " + nombre);
        }
    }

    public void mostrarEtapas(List<List<Partido>> etapas) {
        String[] nombresEtapas = {"Octavios de Final", "Cuartos de Final", "Semifinales", "FINAL"};
        
        System.out.println("\n🏆 === RESULTADO DEL SORTEO ===");
        
        for (int i = 0; i < etapas.size(); i++) {
            String nombreEtapa = (i < nombresEtapas.length) ? nombresEtapas[i] : "Etapa " + (i + 1);
            System.out.println("\n📋 " + nombreEtapa + ":");
            
            List<Partido> partidos = etapas.get(i);
            for (int j = 0; j < partidos.size(); j++) {
                System.out.println("  Partido " + (j + 1) + ": " + partidos.get(j));
            }
        }
    }

    public void despedirse() {
        System.out.println("\n👋 ¡Gracias por usar el sistema de sorteo!");
    }
}