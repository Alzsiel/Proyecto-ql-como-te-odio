package utilidades;

import java.util.Objects;

public class Rut implements IdPersona {
    private int numero;
    private char dv;

    public Rut(int numero, char dv) {
        this.numero = numero;
        this.dv = Character.toLowerCase(dv); // normalizar
    }

    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    public static Rut of(String rutConDv) {

        rutConDv = rutConDv.trim()
                .replace(".", "")
                .replace("[", "")
                .replace("]", "");

        String[] partes = rutConDv.split("-");

        int numero = Integer.parseInt(partes[0]);
        char dv = Character.toLowerCase(partes[1].charAt(0));

        return new Rut(numero, dv);
    }

    @Override
    public String toString() {
        return numero + "-" + dv;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Rut)) return false;

        Rut otro = (Rut) obj;

        return numero == otro.numero &&
                dv == otro.dv;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, dv);
    }
}