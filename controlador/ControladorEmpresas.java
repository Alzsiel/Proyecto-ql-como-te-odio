package controlador;

import modelo.*;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.util.*;

public class ControladorEmpresas {

    private static ControladorEmpresas instancia;

    private ControladorEmpresas() {}

    public static ControladorEmpresas getInstancia() {
        if (instancia == null) {
            instancia = new ControladorEmpresas();
        }
        return instancia;
    }

    private List<Empresa> empresas = new ArrayList<>();
    private List<Bus> buses = new ArrayList<>();
    private List<Terminal> terminales = new ArrayList<>();

    public void createEmpresa(Rut rut, String nombre, String url) {
        Empresa emp = new Empresa(rut, nombre);
        emp.setUrl(url);
        empresas.add(emp);
    }

    public void createBus(String patente, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Empresa> emp = findEmpresa(rutEmp);
        if (emp.isPresent()) {
            Bus bus = new Bus(patente, nroAsientos);
            bus.setMarca(marca);
            bus.setModelo(modelo);

            emp.get().addBus(bus); // delegación correcta
            buses.add(bus);
        }
    }

    public void createTerminal(String nombre, Direccion direccion) {
        Terminal terminal = new Terminal(nombre, direccion);
        terminales.add(terminal);
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> emp = findEmpresa(rutEmp);
        if (emp.isPresent()) {
            emp.get().addConductor(id, nom, dir);
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> emp = findEmpresa(rutEmp);
        if (emp.isPresent()) {
            emp.get().addAuxiliar(id, nom, dir);
        }
    }

    public String[] listEmpresas() {
        return empresas.stream()
                .map(Empresa::toString)
                .toArray(String[]::new);
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, Date fecha) {
        return new String[0][0]; // placeholder
    }

    public String[] listVentasEmpresa(Rut rut) {
        Optional<Empresa> emp = findEmpresa(rut);
        if (emp.isPresent()) {
            return Arrays.stream(emp.get().getVentas())
                    .map(Venta::toString)
                    .toArray(String[]::new);
        }
        return new String[0];
    }

    private Optional<Empresa> findEmpresa(Rut rut) {
        return empresas.stream()
                .filter(e -> e.getRut().equals(rut))
                .findFirst();
    }

    private Optional<Terminal> findTerminal(String nombre) {
        return terminales.stream()
                .filter(t -> t.getNombre().equals(nombre))
                .findFirst();
    }

    private Optional<Terminal> findTerminalPorComuna(String comuna) {
        return terminales.stream()
                .filter(t -> t.getDireccion().getComuna().equals(comuna))
                .findFirst();
    }

    private Optional<Bus> findBus(String patente) {
        return buses.stream()
                .filter(b -> b.getPatente().equals(patente))
                .findFirst();
    }
}
