package modelo;

//Tomás Meza

import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;

public class Conductor extends Tripulante {

    public Conductor(IdPersona id, Nombre nom, Direccion direccion) {
        super(id, nom, direccion);
    }
}
