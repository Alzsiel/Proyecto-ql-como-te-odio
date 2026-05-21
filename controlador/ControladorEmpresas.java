//Juan José Henríquez Vergara

package controlador;

import modelo.*;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.util.ArrayList;
import java.util.List;

public class ControladorEmpresas {

    private static ControladorEmpresas instancia;

    private List<Empresa> empresas;
    private List<Terminal> terminales;

    private ControladorEmpresas() {
        empresas = new ArrayList<>();
        terminales = new ArrayList<>();
    }

    public static ControladorEmpresas getInstancia() {
        if (instancia == null) {
            instancia = new ControladorEmpresas();
        }
        return instancia;
    }

    public boolean addEmpresa(Empresa empresa) {
        if (empresa == null) return false;

        if (buscarEmpresa(empresa.getRut()) != null) {
            return false;
        }

        empresas.add(empresa);
        return true;
    }

    public Empresa buscarEmpresa(Rut rut) {
        for (Empresa e : empresas) {
            if (e.getRut().equals(rut)) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Empresa> getEmpresas() {
        return new ArrayList<>(empresas);
    }

    public boolean addBusAEmpresa(Rut rutEmp, Bus bus) {
        Empresa emp = buscarEmpresa(rutEmp);

        if (emp == null || bus == null) {
            return false;
        }

        emp.addBus(bus);
        return true;
    }

    public boolean createTerminal(String nombre, Direccion direccion) {
        if (nombre == null || direccion == null) {
            return false;
        }

        if (findTerminal(nombre) != null) {
            return false;
        }

        Terminal terminal = new Terminal(nombre, direccion);
        terminales.add(terminal);
        return true;
    }

    public Terminal findTerminal(String nombre) {
        for (Terminal t : terminales) {
            if (t.getNombre().equals(nombre)) {
                return t;
            }
        }
        return null;
    }

    public boolean hireConductor(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Empresa emp = buscarEmpresa(rutEmp);

        if (emp == null) return false;

        return emp.addConductor(id, nom, dir);
    }

    public boolean hireAuxiliar(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Empresa emp = buscarEmpresa(rutEmp);

        if (emp == null) return false;

        return emp.addAuxiliar(id, nom, dir);
    }

    public String[] listVentasEmpresa(Rut rut) {
        Empresa emp = buscarEmpresa(rut);

        if (emp == null) {
            return new String[0];
        }

        Venta[] ventas = emp.getVentas();
        String[] resultado = new String[ventas.length];

        for (int i = 0; i < ventas.length; i++) {
            resultado[i] = ventas[i].toString();
        }

        return resultado;
    }
}