//Camila Valeska Márquez Burgos
package modelo;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;
import java.util.List;

public class Tripulante extends Persona {
    private Direccion direccion;
    private ArrayList<Viaje> viajes;

    public Tripulante(IdPersona idPersona, Nombre nombre, Direccion direccion) {
        super(idPersona, nombre);
        this.direccion = direccion;
        this.viajes = new ArrayList<Viaje>();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void addViaje(Viaje viaje) {
        viajes.add(viaje);
    }

    public int getNroViaje() {
        return viajes.size();
    }
}