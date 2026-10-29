package empleados;

import java.util.ArrayList;

public class SistemaEmpleados {

    private ArrayList<Empleado> empleados = new ArrayList<>();

    public int getCantidad() {
        return empleados.size();
    }

    public Empleado getEmpleado(int posicion) {
        return empleados.get(posicion);
    }

    public Empleado buscar(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (int i=0; i < empleados.size(); i++) {
            if (empleados.get(i).getCodigo().equalsIgnoreCase(codigo.trim())) {
                return empleados.get(i);
            }
        }
        return null;
    }

    public void registrar(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("No hay empleado para registrar");
        }
        if (buscar(empleado.getCodigo()) != null) {
            throw new IllegalArgumentException("Ese codigo ya existe");
        }
        empleados.add(empleado);
    }

    public Empleado clonarEmpleado(int posicion) {
        return empleados.get(posicion).clone();
    }

    public void registrarClon(Empleado copia) {
        if (copia == null) {
            throw new IllegalArgumentException("Primero debe clonar un empleado");
        }
        empleados.add(copia);
    }

    public Empleado salarioMaximo() {
        if (empleados.isEmpty()) {
            throw new IllegalStateException("No hay empleados registrados");
        }
        Empleado mayor = empleados.get(0);
        for (int i=1; i < empleados.size(); i++) {
            if (empleados.get(i).calcularSalario() > mayor.calcularSalario()) {
                mayor = empleados.get(i);
            }
        }
        return mayor;
    }

    public double promedio() {
        if (empleados.isEmpty()) {
            throw new IllegalStateException("No hay empleados registrados");
        }
        int suma = 0;
        for (int i=0; i < empleados.size(); i++) {
            suma += empleados.get(i).calcularSalario();
        }
        return suma / (double) empleados.size();
    }

    public boolean mismosDatos(Empleado uno, Empleado dos) {
        if (uno.getClass() != dos.getClass()) {
            return false;
        }
        boolean iguales = uno.getNombre() == dos.getNombre()
                && uno.getSalarioBase() == dos.getSalarioBase();

        if (uno instanceof EmpleadoAdministrativo) {
            EmpleadoAdministrativo a = (EmpleadoAdministrativo) uno;
            EmpleadoAdministrativo b = (EmpleadoAdministrativo) dos;
            iguales = iguales && a.getBonificacion() == b.getBonificacion()
                    && a.getDescuento() == b.getDescuento();
        } else if (uno instanceof EmpleadoPorHoras) {
            EmpleadoPorHoras a = (EmpleadoPorHoras) uno;
            EmpleadoPorHoras b = (EmpleadoPorHoras) dos;
            iguales = iguales && a.getHorasTrabajadas() == b.getHorasTrabajadas()
                    && a.getTarifaPorHora() == b.getTarifaPorHora();
        }
        return iguales;
    }

    public String comparar(Empleado uno, Empleado dos) {
        return "Mismo objeto: " + (uno == dos)
                + "\nMismo codigo: " + uno.getCodigo().equals(dos.getCodigo())
                + "\nMismos datos: " + mismosDatos(uno, dos);
    }
}
