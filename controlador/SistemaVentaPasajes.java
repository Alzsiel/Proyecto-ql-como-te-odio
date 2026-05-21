package controlador;//Marisol Yañez Borquez
//Juan Henríquez Vergara

import modelo.*;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SistemaVentaPasajes {

    private ControladorEmpresas controladorEmpresas = ControladorEmpresas.getInstancia();

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private ArrayList<Bus> buses = new ArrayList<>();
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();

    private static SistemaVentaPasajes instancia;

    private SistemaVentaPasajes() {}

    public static SistemaVentaPasajes getInstancia() {
        if (instancia == null) {
            instancia = new SistemaVentaPasajes();
        }
        return instancia;
    }

    public boolean createTerminal(String nombre, Direccion dir) {
        return controladorEmpresas.createTerminal(nombre, dir);
    }

    public String[] listVentasEmpresa(Rut rut) {
        return controladorEmpresas.listVentasEmpresa(rut);
    }

    public boolean createEmpresa(String rutStr, String nombre, String url) {
        Rut rut = Rut.of(rutStr);

        if (controladorEmpresas.buscarEmpresa(rut) != null) {
            return false;
        }

        Empresa e = new Empresa(rut, nombre);
        e.setUrl(url);

        return controladorEmpresas.addEmpresa(e);
    }

    public String[][] listEmpresas() {
        ArrayList<Empresa> lista = controladorEmpresas.getEmpresas();

        String[][] data = new String[lista.size()][3];

        for (int i = 0; i < lista.size(); i++) {
            Empresa e = lista.get(i);
            data[i][0] = e.getRut().toString();
            data[i][1] = e.getNombre();
            data[i][2] = e.getUrl();
        }

        return data;
    }

    public boolean addAuxiliar(IdPersona id, Nombre nombre, Direccion dir, Rut rutEmpresa) {
        Empresa e = controladorEmpresas.buscarEmpresa(rutEmpresa);
        return (e != null) && e.addAuxiliar(id, nombre, dir);
    }

    public boolean addConductor(IdPersona id, Nombre nombre, Direccion dir, Rut rutEmpresa) {
        Empresa e = controladorEmpresas.buscarEmpresa(rutEmpresa);
        return (e != null) && e.addConductor(id, nombre, dir);
    }

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email) {
        if (findCliente(id) != null) return false;

        Cliente c = new Cliente(id, nom, email);
        c.setTelefono(fono);
        clientes.add(c);
        return true;
    }

    private Cliente findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) return c;
        }
        return null;
    }

    public boolean createPasajero(IdPersona id, Nombre nom, String fono,
                                  Nombre nomContacto, String fonoContacto) {

        if (findPasajero(id) != null) return false;

        Pasajero p = new Pasajero(id, nom);
        p.setTelefono(fono);
        p.setNomContacto(nomContacto);
        p.setFonoContacto(fonoContacto);

        pasajeros.add(p);
        return true;
    }

    private Pasajero findPasajero(IdPersona id) {
        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(id)) return p;
        }
        return null;
    }

    public boolean createBus(String patente, String marca, String modelo,
                             int nroAsientos, Rut rutEmpresa) {

        if (findBus(patente) != null) return false;

        Empresa e = controladorEmpresas.buscarEmpresa(rutEmpresa);
        if (e == null) return false;

        Bus bus = new Bus(patente, nroAsientos);
        bus.setMarca(marca);
        bus.setModelo(modelo);

        buses.add(bus);
        e.addBus(bus);

        return true;
    }

    private Bus findBus(String patente) {
        for (Bus b : buses) {
            if (b.getPatente().equals(patente)) return b;
        }
        return null;
    }

    public boolean createViaje(LocalDate fecha, LocalTime hora,
                               int precio, int duracion, String patente) {

        Bus bus = findBus(patente);
        if (bus == null) return false;

        if (findViaje(fecha, hora, patente) != null) return false;

        Viaje v = new Viaje(fecha, hora, precio, bus);
        viajes.add(v);
        bus.addViaje(v);

        return true;
    }

    private Viaje findViaje(LocalDate fecha, LocalTime hora, String patente) {
        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patente)) {
                return v;
            }
        }
        return null;
    }

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo,
                               LocalDate fecha, IdPersona idCliente) {

        if (findVenta(idDoc, tipo) != null) return false;

        Cliente c = findCliente(idCliente);
        if (c == null) return false;

        Venta v = new Venta(idDoc, tipo, fecha, c);
        ventas.add(v);
        c.addVenta(v);

        return true;
    }

    private Venta findVenta(String idDoc, TipoDocumento tipo) {
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDoc) && v.getTipo() == tipo) {
                return v;
            }
        }
        return null;
    }

    private Venta findVentaFlexible(String idDoc) {
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDoc)) return v;
        }
        return null;
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha,
                               LocalTime hora, String patente,
                               int asiento, IdPersona idPasajero) {

        Venta venta = findVentaFlexible(idDoc);
        if (venta == null) return false;

        Viaje viaje = findViaje(fecha, hora, patente);
        if (viaje == null) return false;

        Pasajero pasajero = findPasajero(idPasajero);
        if (pasajero == null) return false;

        if (asiento < 1 || asiento > viaje.getBus().getNroAsientos()) {
            return false;
        }

        for (String[] a : viaje.getAsientos()) {
            if (a[0].equals(String.valueOf(asiento)) && a[1].equals("*")) {
                return false;
            }
        }

        venta.createPasaje(asiento, viaje, pasajero);
        return true;
    }

    public String[][] listVentas() {
        String[][] res = new String[ventas.size()][7];

        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);

            res[i][0] = v.getIdDocumento();
            res[i][1] = v.getTipo().toString();
            res[i][2] = v.getFecha().toString();
            res[i][3] = v.getCliente().getIdPersona().toString();
            res[i][4] = v.getCliente().getNombreCompleto().toString();
            res[i][5] = String.valueOf(v.getPasajes().length);
            res[i][6] = String.valueOf(v.getMonto());
        }

        return res;
    }

    public String[][] listViajes() {
        String[][] res = new String[viajes.size()][5];

        for (int i = 0; i < viajes.size(); i++) {
            Viaje v = viajes.get(i);

            res[i][0] = v.getFecha().toString();
            res[i][1] = v.getHora().toString();
            res[i][2] = String.valueOf(v.getPrecio());
            res[i][3] = String.valueOf(v.getNroAsientosDisponibles());
            res[i][4] = v.getBus().getPatente();
        }

        return res;
    }
}
