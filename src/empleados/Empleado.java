package empleados;

public abstract class Empleado implements Cloneable {

    private String codigo;
    private String nombre;
    private double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        setCodigo(codigo);
        setNombre(nombre);
        setSalarioBase(salarioBase);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Escriba el codigo");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Escriba el nombre");
        }
        this.nombre = nombre.trim();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0 || !Double.isFinite(salarioBase)) {
            throw new IllegalArgumentException("Salario base invalido");
        }
        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();

    public String mostrarInformacion() {
        return "Codigo: " + codigo + "\nNombre: " + nombre
                + "\nSalario base: " + salarioBase
                + "\nSalario calculado: " + calcularSalario()
                + "\nCategoria: " + obtenerCategoria();
    }

    public String obtenerCategoria() {
        // Basica < 10000, Media < 20000, Alta desde 20000.
        double salario = calcularSalario();
        if (salario < 10000) {
            return "Basica";
        } else if (salario < 20000) {
            return "Media";
        }
        return "Alta";
    }

    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        } catch (Exception e) {
            throw new IllegalStateException("Algo salio mal");
        }
    }
}
