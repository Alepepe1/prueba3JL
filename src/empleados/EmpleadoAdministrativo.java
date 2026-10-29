package empleados;

public class EmpleadoAdministrativo extends Empleado {

    private double bonificacion;
    private double descuento;

    public EmpleadoAdministrativo(String codigo, String nombre, double salarioBase,
            double bonificacion, double descuento) {
        super(codigo, nombre, salarioBase);
        setBonificacion(bonificacion);
        setDescuento(descuento);
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        if (bonificacion < 0 || !Double.isFinite(bonificacion)) {
            throw new IllegalArgumentException("Bonificacion invalida");
        }
        this.bonificacion = bonificacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (descuento < 0 || descuento > 100 || !Double.isFinite(descuento)) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
        this.descuento = descuento;
    }

    @Override
    public double calcularSalario() {
        return (getSalarioBase() + bonificacion) * (1 - descuento / 100);
    }

}
