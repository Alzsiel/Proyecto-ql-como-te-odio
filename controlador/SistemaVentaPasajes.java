package controlador;//Marisol Yañez Borquez
//Juan Henríquez Vergara

import modelo.*;
import utilidades.IdPersona;
import utilidades.Nombre;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SistemaVentaPasajes {
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<Pasajero>();
    private ArrayList<Bus> buses = new ArrayList<Bus>();
    private ArrayList<Viaje> viajes = new ArrayList<Viaje>();
    private ArrayList<Venta> ventas = new ArrayList<Venta>();

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email) {
        Cliente existente = findCliente(id);
        if (existente != null) {
            return false;
        }

        Cliente cliente = new Cliente(id, nom, email);
        cliente.setTelefono(fono);
        clientes.add(cliente);
        return true;
    }

    public boolean createPasajero(IdPersona idPersona, Nombre nom, String fono, Nombre nomContacto,
                                  String fonoContacto) {
        if (findPasajero(idPersona) != null) {
            return false;
        }
        Pasajero pasajero = new Pasajero(idPersona, nom);
        pasajero.setTelefono(fono);
        pasajero.setNomContacto(nomContacto);
        pasajero.setFonoContacto(fonoContacto);
        pasajeros.add(pasajero);
        return true;
    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos) {
        if (findBus(patente) != null) {
            return false;
        }
        Bus bus = new Bus(patente, nroAsientos);
        bus.setMarca(marca);
        bus.setModelo(modelo);
        buses.add(bus);
        return true;
    }

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus) {
        Bus busEncontrado = findBus(patBus);
        if (busEncontrado == null) {
            return false;
        }

        Viaje viajeExistente = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viajeExistente != null) {
            return false;
        }

        Viaje viaje = new Viaje(fecha, hora, precio, busEncontrado);
        viajes.add(viaje);
        busEncontrado.addViaje(viaje);
        return true;
    }

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        Venta existente = findVenta(idDoc, tipo);
        if (existente != null) {
            return false;
        }
        Cliente clienteEncontrado = findCliente(idCliente);
        if (clienteEncontrado == null) {
            return false;
        }
        Venta ventaa = new Venta(idDoc, tipo, fechaVenta, clienteEncontrado);
        ventas.add(ventaa);
        clienteEncontrado.addVenta(ventaa);
        return true;
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje) {
        ArrayList<Viaje> lista = new ArrayList<>();
        for (int i = 0; i < viajes.size(); i++) {
            if (viajes.get(i).getFecha().equals(fechaViaje)) {
                lista.add(viajes.get(i));
            }
        }
        String[][] resultado = new String[lista.size()][4];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i][0] = lista.get(i).getBus().getPatente();
            resultado[i][1] = lista.get(i).getHora().toString();
            resultado[i][2] = String.valueOf(lista.get(i).getPrecio());
            resultado[i][3] = String.valueOf(lista.get(i).getNroAsientosDisponibles());
        }

        return resultado;
    }

    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) {
            return new String[0][0];
        }
        return viaje.getAsientos();
    }

    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Venta venta = findVenta(idDocumento, tipo);
        if (venta == null) {
            return 0;
        }
        return venta.getMonto();
    }

    public String getNombrePasajero(IdPersona idPasajero) {
        Pasajero BuscarPasajero = findPasajero(idPasajero);
        if (BuscarPasajero == null) {
            return "Pasajero no existe";
        }

        return BuscarPasajero.getNombreCompleto().toString();
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento,
            IdPersona idPasajero) {
        Venta venta = findVenta(idDoc, TipoDocumento.BOLETA);
        if (venta == null) {
            venta = findVenta(idDoc, TipoDocumento.FACTURA);
        }

        if (venta == null) {
            return false;
        }

        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) {
            return false;
        }

        Pasajero pasajero = findPasajero(idPasajero);
        if (pasajero == null) {
            return false;
        }

        if (asiento < 1 || asiento > viaje.getBus().getNroAsientos()) {
            return false;
        }

        String[][] asientos = viaje.getAsientos();
        for (int i = 0; i < asientos.length; i++) {
            if (asientos[i][0].equals(String.valueOf(asiento)) && asientos[i][1].equals("*")) {
                return false;
            }
        }

        venta.createPasaje(asiento, viaje, pasajero);
        return true;
    }

    public String[][] listVentas() {
        String[][] resultado = new String[ventas.size()][7];
        for (int i = 0; i < ventas.size(); i++) {
            Venta vent = ventas.get(i);
            resultado[i][0] = vent.getIdDocumento();
            resultado[i][1] = vent.getTipo().toString();
            resultado[i][2] = vent.getFecha().toString();
            resultado[i][3] = vent.getCliente().getIdPersona().toString();
            resultado[i][4] = vent.getCliente().getNombreCompleto().toString();
            resultado[i][5] = String.valueOf(vent.getPasajes().length);
            resultado[i][6] = String.valueOf(vent.getMonto());
        }
        return resultado;
    }

    public String[][] listViajes() {
        String[][] resultado = new String[viajes.size()][5];
        for (int i = 0; i < viajes.size(); i++) {
            Viaje viaj = viajes.get(i);
            resultado[i][0] = viaj.getFecha().toString();
            resultado[i][1] = viaj.getHora().toString();
            resultado[i][2] = String.valueOf(viaj.getPrecio());
            resultado[i][3] = String.valueOf(viaj.getNroAsientosDisponibles());
            resultado[i][4] = viaj.getBus().getPatente();
        }

        return resultado;
    }

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) {
            return new String[0][0];
        }
        return viaje.getListaPasajeros();
    }

    private Cliente findCliente(IdPersona id) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            if (cliente.getIdPersona().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (int i = 0; i < ventas.size(); i++) {

            Venta venta = ventas.get(i);

            if (venta.getIdDocumento().equals(idDocumento) && venta.getTipo() == tipoDocumento) {
                return venta;
            }
        }
        return null;
    }

    private Bus findBus(String patente) {
        for (int i = 0; i < buses.size(); i++) {
            Bus bus = buses.get(i);
            if (bus.getPatente().equals(patente)) {
                return bus;
            }
        }
        return null;
    }

 private Viaje findViaje(String fecha, String hora, String patenteBus) {
    for (int i = 0; i < viajes.size(); i++) {
        Viaje viaje = viajes.get(i);

        if (viaje.getFecha().toString().equals(fecha)) {
            if (viaje.getHora().toString().equals(hora)) {
                if (viaje.getBus().getPatente().equals(patenteBus)) {
                    return viaje;
                }
            }
        }
    }
    return null;
}


    private Pasajero findPasajero(IdPersona idPersona) {
        for (int i = 0; i < pasajeros.size(); i++) {
            Pasajero pasajero = pasajeros.get(i);

            if (pasajero.getIdPersona().equals(idPersona)) {
                return pasajero;
            }
        }
        return null;
    }

}
