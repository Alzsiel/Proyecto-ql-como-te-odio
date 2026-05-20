
//Camila Márquez Burgos
import controlador.SistemaVentaPasajes;
import modelo.TipoDocumento;
import utilidades.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private SistemaVentaPasajes sistema = new SistemaVentaPasajes();

    public static void main(String[] args) {
        Main mn = new Main();
        mn.menu();
    }

    private void menu() {
        int opcion;
        do {
            System.out.println("====================");
            System.out.println("--MENU DE OPCIONES--");
            System.out.println("1- Crear cliente");
            System.out.println("2- Crear bus");
            System.out.println("3- Crear viaje");
            System.out.println("4- Vender pasaje");
            System.out.println("5- Lista de pasajeros");
            System.out.println("6- Lista de ventas");
            System.out.println("7- Lista de viajes");
            System.out.println("8- Salir");
            System.out.println("====================");
            System.out.print("Ingrese el numero de opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    createCliente();
                    break;
                case 2:
                    createBus();
                    break;
                case 3:
                    createViaje();
                    break;
                case 4:
                    vendePasajes();
                    break;
                case 5:
                    listPasajerosViaje();
                    break;
                case 6:
                    listVentas();
                    break;
                case 7:
                    listViajes();
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("ERROR! La opcion ingresada no es valida");
                    break;
            }
        } while (opcion != 8);
    }

 private void createCliente() {
    IdPersona idPersona;
    System.out.println(" ");
    System.out.println("--CREAR UN NUEVO CLIENTE--");
    System.out.print("utilidades.Rut [1] o utilidades.Pasaporte [2]: ");
    int tipoId = sc.nextInt();
    sc.nextLine();

    if (tipoId == 1) {
        System.out.print("RUT [11.111.111-1]: ");
        String rutTexto = sc.nextLine();
        rutTexto = rutTexto.replace(".", "");
        idPersona = Rut.of(rutTexto);
    } else {
        System.out.print("utilidades.Pasaporte [Solo texto]: ");
        String pasaporteTexto = sc.nextLine();
        idPersona = new Pasaporte(pasaporteTexto, "Chilena");
    }

    System.out.print("utilidades.Tratamiento: Sr [1] o Sra [2]: ");
    int num = sc.nextInt();
    sc.nextLine();

    Tratamiento tratamiento;
    if (num == 1) {
        tratamiento = Tratamiento.SR;
    } else {
        tratamiento = Tratamiento.SRA;
    }

    System.out.print("Nombres: ");
    String nombres = sc.nextLine();

    System.out.print("Apellido Paterno: ");
    String apPaterno = sc.nextLine();

    System.out.print("Apellido Materno: ");
    String apMaterno = sc.nextLine();

    Nombre nombre = new Nombre(tratamiento, nombres, apPaterno, apMaterno);

    System.out.print("Telefono movil: ");
    String fono = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    boolean creado = sistema.createCliente(idPersona, nombre, fono, email);
    if (creado) {
        System.out.println("Creado exitosamente!!");
    } else {
        System.out.println("No se ha podido crear el cliente");
    }
}


    private void createBus() {
        System.out.println(" ");
        System.out.println("--CREACION DE UN NUEVO BUS--");
        System.out.print("Patente: ");
        String patente = sc.nextLine();

        System.out.print("Marca: ");
        String marca = sc.nextLine();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Numero de asientos: ");
        int nroAsientos = sc.nextInt();
        sc.nextLine();

        boolean creado = sistema.createBus(patente, marca, modelo, nroAsientos);
        if (creado) {
            System.out.println("Creado exitosamente!!");
        } else {
            System.out.println("No se ha podido crear el bus");
        }
    }

    private void createViaje() {
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println(" ");
        System.out.println("--CREACION DE UN NUEVO VIAJE--");
        System.out.print("Fecha [dd/mm/yyyy]: ");
        String fechaTexto = sc.nextLine();
        LocalDate fecha = LocalDate.parse(fechaTexto, formatterFecha);

        System.out.print("Hora [hh:mm]: ");
        String horaTexto = sc.nextLine();
        LocalTime hora = LocalTime.parse(horaTexto, formatterHora);

        System.out.print("Precio: $");
        int precio = sc.nextInt();
        sc.nextLine();

        System.out.print("Patente bus: ");
        String patBus = sc.nextLine();

        boolean creado = sistema.createViaje(fecha, hora, precio, patBus);
        if (creado) {
            System.out.println("Creado exitosamente!!");
        } else {
            System.out.println("No se ha podido guardar el viaje");
        }
    }

  private void vendePasajes() {
    DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

    System.out.println(" ");
    System.out.println("--VENTA DE PASAJES--");
    System.out.println("Datos de la venta:");
    System.out.print("-> ID documento: ");
    String idDoc = sc.nextLine();
    System.out.print("-> Tipo documento: [1] Boleta [2] Factura: ");
    int tipoDoc = sc.nextInt();
    sc.nextLine();

    TipoDocumento tipo;
    if (tipoDoc == 1) {
        tipo = TipoDocumento.BOLETA;
    } else {
        tipo = TipoDocumento.FACTURA;
    }

    System.out.print("-> Fecha de venta [dd/mm/yyyy]: ");
    String fechaVentaTexto = sc.nextLine();
    LocalDate fechaVenta = LocalDate.parse(fechaVentaTexto, formatterFecha);

    System.out.println("Datos del cliente:");
    System.out.print("-->  utilidades.Rut [1] o utilidades.Pasaporte [2]: ");
    int tipoId = sc.nextInt();
    sc.nextLine();

    IdPersona idPersona;
    if (tipoId == 1) {
        System.out.print("RUT [11.111.111-1]: ");
        String rutTexto = sc.nextLine();
        rutTexto = rutTexto.replace(".", "");
        idPersona = Rut.of(rutTexto);
    } else {
        System.out.print("utilidades.Pasaporte [Solo texto]: ");
        String pasaporteTexto = sc.nextLine();
        idPersona = new Pasaporte(pasaporteTexto, "Chilena");
    }

    boolean creado = sistema.iniciaVenta(idDoc, tipo, fechaVenta, idPersona);
    if (creado) {
        System.out.println("Creado exitosamente!!");
    } else {
        System.out.println("No se ha podido realizar la venta");
    }

    System.out.println(" ");
    System.out.println("Pasajes a vender");
    System.out.print("--> Cantidad de pasajes: ");
    int cantPasajes = sc.nextInt();
    sc.nextLine();

    System.out.print("--> Fecha de viaje [dd/mm/yyyy]: ");
    String fechaViajeTexto = sc.nextLine();
    LocalDate fechaViaje = LocalDate.parse(fechaViajeTexto, formatterFecha);
    System.out.println("");
    System.out.println("Listado de horarios disponibles");

    String[][] horario = sistema.getHorariosDisponibles(fechaViaje);
    if (horario.length == 0) {
        System.out.println("No hay viajes disponibles para esa fecha");
        return;
    }

    System.out.println("---------------------------------------------------------------------------------");
    System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n",
            "BUS", "SALIDA", "VALOR", "ASIENTOS");
    System.out.println("---------------------------------------------------------------------------------");
    for (int i = 0; i < horario.length; i++) {
        System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n",
                horario[i][0],
                horario[i][1],
                horario[i][2],
                horario[i][3]);
        if (i < horario.length - 1) {
            System.out.println("---------------------------------------------------------------------------------");
        }
    }
    System.out.println("---------------------------------------------------------------------------------");

    System.out.print("Seleccione viaje en [" + 1 + "..." + horario.length + "]: ");
    int viaje = sc.nextInt();
    sc.nextLine();

    String patBus = horario[viaje - 1][0];
    LocalTime hora = LocalTime.parse(horario[viaje - 1][1], formatterHora);
    String[][] asientosB = sistema.listAsientosDeViaje(fechaViaje, hora, patBus);

    System.out.println(" ");
    System.out.println("Asientos disponibles para el viaje seleccionado");

    System.out.println("------------------------");
    for (int i = 0; i < asientosB.length; i++) {
        int mitad = asientosB[i].length / 2;
        for (int j = 0; j < asientosB[i].length; j++) {
            if (j == mitad) {
                System.out.print("   ");
            }
            System.out.printf("[%2s]", asientosB[i][j]);
        }
        System.out.println(" ");
    }

    System.out.print("Seleccione sus asientos [separado por ,]: ");
    String ingreso = sc.nextLine();
    String[] asientosArreglo = ingreso.split(",");

    if (asientosArreglo.length != cantPasajes) {
        System.out.println("La cantidad de asientos no coincide con la cantidad de pasajes");
        return;
    }

    int[] asientos = new int[asientosArreglo.length];
    for (int i = 0; i < asientosArreglo.length; i++) {
        asientos[i] = Integer.parseInt(asientosArreglo[i].trim());
    }

    IdPersona[] ids = new IdPersona[asientos.length];
    int[] npasaje = new int[asientos.length];

    for (int i = 0; i < asientos.length; i++) {
        System.out.println("Datos pasajero " + (i + 1));
        int asiento = asientos[i];
        System.out.println("--> Asiento: " + asiento);
        System.out.print(" ");
        System.out.print("-->  utilidades.Rut [1] o utilidades.Pasaporte [2]: ");
        tipoId = sc.nextInt();
        sc.nextLine();

        if (tipoId == 1) {
            System.out.print("RUT [11.111.111-1]: ");
            String rutTexto = sc.nextLine();
            rutTexto = rutTexto.replace(".", "");
            idPersona = Rut.of(rutTexto);
        } else {
            System.out.print("utilidades.Pasaporte: ");
            String pasaporteTexto = sc.nextLine();
            idPersona = new Pasaporte(pasaporteTexto, "Chilena");
        }

        ids[i] = idPersona;

        System.out.print("utilidades.Tratamiento: Sr [1] o Sra [2]: ");
        int num = sc.nextInt();
        sc.nextLine();

        Tratamiento tratamiento;
        if (num == 1) {
            tratamiento = Tratamiento.SR;
        } else {
            tratamiento = Tratamiento.SRA;
        }

        System.out.print("Nombres: ");
        String nombres = sc.nextLine();

        System.out.print("Apellido Paterno: ");
        String apPaterno = sc.nextLine();

        System.out.print("Apellido Materno: ");
        String apMaterno = sc.nextLine();

        Nombre nombre = new Nombre(tratamiento, nombres, apPaterno, apMaterno);

        System.out.print("Telefono movil: ");
        String fono = sc.nextLine();

        boolean creadoPasajero = sistema.createPasajero(idPersona, nombre, fono, nombre, fono);

        if (creadoPasajero) {
            System.out.println("Pasajero creado exitosamente!!");
        } else {
            System.out.println("Pasajero ya existe");
        }

        creado = sistema.vendePasaje(idDoc, fechaViaje, hora, patBus, asiento, idPersona);
        if (creado) {
            System.out.println("Creado exitosamente!!");
        } else {
            System.out.println("No se ha podido crear el pasaje");
            return;
        }
        npasaje[i] = sistema.listVentas().length + 1;
    }

    for (int i = 0; i < asientos.length; i++) {
        System.out.println("------- PASAJE -------");
        System.out.println("Numero del pasaje: " + npasaje[i]);
        System.out.println("Fecha de viaje: " + fechaViaje);
        System.out.println("Hora de viaje: " + horario[viaje - 1][1]);
        System.out.println("Patente bus: " + patBus);
        System.out.println("Asiento: " + asientos[i]);
        System.out.println("RUT/PASAPORTE: " + ids[i]);
        System.out.println("utilidades.Nombre Pasajero: " + sistema.getNombrePasajero(ids[i]));
    }
}


     private void listPasajerosViaje() {
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        String fecha1;
        String hora1;
        String patente;
        
        System.out.println("--LISTADO DE PASAJEROS DE UN VIAJE--");
        System.out.print("Fecha del viaje [dd/mm/yyyy]: ");
        fecha1 = sc.nextLine();
        System.out.print("Hora del viaje [hh:mm]: ");
        hora1 = sc.nextLine();
        System.out.print("Patente bus: ");
        patente = sc.nextLine();
        LocalTime hora = LocalTime.parse(hora1, formatterHora);
        LocalDate fecha = LocalDate.parse(fecha1, formatterFecha);

        String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);
        if (pasajeros.length == 0) {
            System.out.println("\nNo existe el viaje o no hay pasajeros.");
            return;
        }

        System.out.println(
                "*------------*----------------*------------------------------*------------------------------*----------------------*");

        System.out.printf("| %-10s | %-14s | %-28s | %-28s | %-20s |\n",
                "ASIENTO", "RUT/PASS", "PASAJERO", "CONTACTO", "TELEFONO CONTACTO");

        System.out.println(
                "|------------+----------------+------------------------------+------------------------------+----------------------|");

        for (int i = 0; i < pasajeros.length; i++) {

            System.out.printf("| %-10s | %-14s | %-28s | %-28s | %-20s |\n",
                    pasajeros[i][0],
                    pasajeros[i][1],
                    pasajeros[i][2],
                    pasajeros[i][3],
                    pasajeros[i][4]);

            if (i < pasajeros.length - 1) {
                System.out.println(
                        "|------------+----------------+------------------------------+------------------------------+----------------------|");
            }
        }
        System.out.println(
                "*------------*----------------*------------------------------*------------------------------*----------------------*");

    }

    private void listVentas() {
        String[][] ventas = sistema.listVentas();

        if (ventas.length == 0) {
            System.out.println("No existen ventas");
            return;
        }

        System.out.println(":::::::: LISTADO DE VENTAS ::::::::");
        System.out.printf("| %-10s | %-13s | %-10s | %-16s | %-28s | %-13s | %-13s |\n",
                "ID DOC", "TIPO DOC", "FECHA", "RUT/PASS", "CLIENTE", "CANT", "TOTAL");

        for (int i = 0; i < ventas.length; i++) {
            System.out.printf("| %-10s | %-13s | %-10s | %-16s | %-28s | %-13s | %-13s |\n",
                    ventas[i][0],
                    ventas[i][1],
                    ventas[i][2],
                    ventas[i][3],
                    ventas[i][4],
                    ventas[i][5],
                    ventas[i][6]);
        }
    }

    private void listViajes() {
        String[][] viajes = sistema.listViajes();
        if (viajes.length == 0) {
            System.out.println("No existen viajes");
            return;
        }

        System.out.println(":::::::LISTADO DE VIAJES:::::::");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                "FECHA", "HORA", "PRECIO", "DISPONIBLES", "PATENTE");

        for (int i = 0; i < viajes.length; i++) {
            System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                    viajes[i][0],
                    viajes[i][1],
                    viajes[i][2],
                    viajes[i][3],
                    viajes[i][4]);
        }
    }

}
