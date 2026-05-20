//Tomás Meza

package vista;

import controlador.ControladorEmpresas;
import modelo.*;
import utilidades.*;
import java.util.Scanner;


public class UISVP {

    private static UISVP instancia;

    private Scanner sc;
    private ControladorEmpresas controladorEmpresas;

    public UISVP() {
        sc = new Scanner(System.in);
        controladorEmpresas = ControladorEmpresas.getInstancia();
    }

    public static UISVP getInstancia() {
        if (instancia == null) {
            instancia = new UISVP();
        }
        return instancia;
    }

    public void menu() {

        int opcion;

        do {
            System.out.println("\n===== SISTEMA VENTA DE PASAJES =====");
            System.out.println("1. Crear Empresa");
            System.out.println("2. Crear Bus");
            System.out.println("3. Contratar Tripulante");
            System.out.println("4. Listar Empresas");
            System.out.println("5. Listar Ventas Empresa");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> createEmpresa();
                case 2 -> createBus();
                case 3 -> contrataTripulante();
                case 4 -> listEmpresas();
                case 5 -> listVentasEmpresa();
            }

        } while (opcion != 0);
    }

    private void createEmpresa() {

        System.out.println("\n...:::: Creando una nueva Empresa ::::....");

        System.out.print("R.U.T [11111111-1] : ");
        Rut rut = Rut.of(sc.nextLine());

        System.out.print("Nombre : ");
        String nombre = sc.nextLine();

        System.out.print("URL : ");
        String url = sc.nextLine();

        controladorEmpresas.createEmpresa(rut, nombre, url);

        System.out.println("...:::: Empresa guardada exitosamente ::::....");
    }

    private void createBus() {

        System.out.println("\n...::: Creando un nuevo Bus ::::....");

        System.out.print("Patente : ");
        String patente = sc.nextLine();

        System.out.print("Marca : ");
        String marca = sc.nextLine();

        System.out.print("Modelo : ");
        String modelo = sc.nextLine();

        System.out.print("Numero asientos : ");
        int nro = Integer.parseInt(sc.nextLine());

        System.out.print("R.U.T empresa: ");
        Rut rutEmpresa = Rut.of(sc.nextLine());

        controladorEmpresas.createBus(patente, marca, modelo, nro, rutEmpresa);

        System.out.println("...:::: Bus guardado exitosamente ::::....");
    }

    private void contrataTripulante() {

        System.out.println("\n...:::: Contratando Tripulante ::::....");

        System.out.print("RUT Empresa: ");
        Rut rutEmpresa = Rut.of(sc.nextLine());

        System.out.print("Auxiliar[1] o Conductor[2]: ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("Rut[1] o Pasaporte[2]: ");
        int tipoId = Integer.parseInt(sc.nextLine());

        IdPersona id;

        if (tipoId == 1) {
            System.out.print("Ingrese RUT: ");
            id = Rut.of(sc.nextLine());
        } else {
            System.out.print("Ingrese Pasaporte: ");
            id = new Pasaporte(sc.nextLine(), "Chilena");
        }

        System.out.print("Nombre: ");
        String nombres = sc.nextLine();

        System.out.print("Apellido Paterno: ");
        String apPat = sc.nextLine();

        System.out.print("Apellido Materno: ");
        String apMat = sc.nextLine();

        Nombre nom = new Nombre(Tratamiento.SR, nombres, apPat, apMat);

        System.out.print("Calle: ");
        String calle = sc.nextLine();

        System.out.print("Numero: ");
        int numero = Integer.parseInt(sc.nextLine());

        System.out.print("Comuna: ");
        String comuna = sc.nextLine();

        Direccion dir = new Direccion(calle, numero, comuna);

        if (tipo == 1) {
            controladorEmpresas.hireAuxiliarForEmpresa(rutEmpresa, id, nom, dir);
        } else {
            controladorEmpresas.hireConductorForEmpresa(rutEmpresa, id, nom, dir);
        }

        System.out.println("...:::: Tripulante contratado ::::....");
    }

    private void listEmpresas() {

        System.out.println("\n...:::: Listado de Empresas ::::....");

        String[] empresas = controladorEmpresas.listEmpresas();

        if (empresas.length == 0) {
            System.out.println("*** No existen empresas ***");
            return;
        }

        System.out.println("+----------------------+----------------------+----------------------+");
        System.out.printf("| %-20s | %-20s | %-20s |\n", "RUT", "NOMBRE", "URL");
        System.out.println("+----------------------+----------------------+----------------------+");

        for (String e : empresas) {

            String[] partes = e.split(" - ");

            String rut = partes.length > 0 ? partes[0] : "";
            String nombre = partes.length > 1 ? partes[1] : "";
            String url = partes.length > 2 ? partes[2] : "";

            System.out.printf("| %-20s | %-20s | %-20s |\n", rut, nombre, url);
        }

        System.out.println("+----------------------+----------------------+----------------------+");
    }

    private void listVentasEmpresa() {

        System.out.println("\n...:::: Ventas de Empresa ::::....");

        System.out.print("RUT empresa: ");
        Rut rut = Rut.of(sc.nextLine());

        String[] ventas = controladorEmpresas.listVentasEmpresa(rut);

        if (ventas.length == 0) {
            System.out.println("*** No hay ventas ***");
            return;
        }

        for (String v : ventas) {
            System.out.println(v);
        }
    }
}
