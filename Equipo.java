package modelo;

import java.io.Serializable;

public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String ciudad;

    public Equipo(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    @Override
    public String toString() {
        return nombre + " (" + ciudad + ")";
    }
}