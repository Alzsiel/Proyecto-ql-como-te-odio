//Marisol Yañez Borquez
package modelo;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;
public class Auxiliar extends Tripulante {
    private ArrayList<Viaje> viajes;

    public Auxiliar(IdPersona id, Nombre nom, Direccion dir) {
        super(id, nom, dir);
        viajes = new ArrayList<>();
    }
    @Override
    public void addViaje(Viaje viaje) {
        if (viaje != null) {
            viajes.add(viaje);
        }
    }

    public int getNroViajes() {
        return viajes.size();
    }
}