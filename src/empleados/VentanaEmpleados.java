package empleados;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaEmpleados extends JFrame {

    private SistemaEmpleados sistema = new SistemaEmpleados();
    private Empleado copia;
    private JTextField txtCodigo = new JTextField();
    private JTextField txtNombre = new JTextField();
    private JTextField txtBase = new JTextField();
    private JTextField txtBono = new JTextField();
    private JTextField txtDescuento = new JTextField();
    private JTextField txtHoras = new JTextField();
    private JTextField txtTarifa = new JTextField();
    private JComboBox<String> cboTipo = new JComboBox<>(new String[]{"Administrativo", "Por horas"});
    private DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Codigo", "Nombre", "Tipo", "Salario", "Categoria"}, 0);
    private JTable tabla = new JTable(modelo);

    public VentanaEmpleados() {
        setTitle("Empleados");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel datos = new JPanel(new GridLayout(4, 4));
        datos.add(new JLabel("Codigo"));
        datos.add(txtCodigo);
        datos.add(new JLabel("Nombre"));
        datos.add(txtNombre);
        datos.add(new JLabel("Tipo"));
        datos.add(cboTipo);
        datos.add(new JLabel("Salario base"));
        datos.add(txtBase);
        datos.add(new JLabel("Bonificacion"));
        datos.add(txtBono);
        datos.add(new JLabel("Descuento"));
        datos.add(txtDescuento);
        datos.add(new JLabel("Horas"));
        datos.add(txtHoras);
        datos.add(new JLabel("Tarifa"));
        datos.add(txtTarifa);

        JButton registrar = new JButton("Registrar");
        JButton calcular = new JButton("Calcular");
        JButton buscar = new JButton("Buscar");
        JButton clonar = new JButton("Clonar");
        JButton guardarClon = new JButton("Registrar clon");
        JButton comparar = new JButton("Comparar");
        JButton maximo = new JButton("Maximo");
        JButton promedio = new JButton("Promedio");
        JButton limpiar = new JButton("Limpiar");

        JPanel botones = new JPanel(new GridLayout(3, 3));
        botones.add(registrar);
        botones.add(calcular);
        botones.add(buscar);
        botones.add(clonar);
        botones.add(guardarClon);
        botones.add(comparar);
        botones.add(maximo);
        botones.add(promedio);
        botones.add(limpiar);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(datos, BorderLayout.CENTER);
        arriba.add(botones, BorderLayout.SOUTH);
        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        tabla.setDefaultEditor(Object.class, null);

        registrar.addActionListener(e -> registrar());
        calcular.addActionListener(e -> calcular());
        buscar.addActionListener(e -> buscar());
        clonar.addActionListener(e -> clonar());
        guardarClon.addActionListener(e -> registrarClon());
        comparar.addActionListener(e -> comparar());
        maximo.addActionListener(e -> maximo());
        promedio.addActionListener(e -> promedio());
        limpiar.addActionListener(e -> limpiar());
    }

    private void registrar() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            double base = Double.parseDouble(txtBase.getText());
            Empleado empleado;
            if (cboTipo.getSelectedIndex() == 0) {
                double bono = Double.parseDouble(txtBono.getText());
                double descuento = Double.parseDouble(txtDescuento.getText());
                empleado = new EmpleadoAdministrativo(codigo, nombre, base, bono, descuento);
            } else {
                int horas = Integer.parseInt(txtHoras.getText());
                double tarifa = Double.parseDouble(txtTarifa.getText());
                empleado = new EmpleadoPorHoras(codigo, nombre, base, horas, tarifa);
            }
            sistema.registrar(empleado);
            actualizarTabla();
            mensaje("Registrado");
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (int i=0; i < sistema.getCantidad(); i++) {
            Empleado empleado = sistema.getEmpleado(i);
            String tipo = "Por horas";
            if (empleado instanceof EmpleadoAdministrativo) tipo = "Administrativo";
            modelo.addRow(new Object[]{empleado.getCodigo(), empleado.getNombre(), tipo,
                empleado.calcularSalario(), empleado.obtenerCategoria()});
        }
    }

    private void calcular() {
        try {
            Empleado empleado = sistema.getEmpleado(tabla.getSelectedRow());
            mensaje("Salario: " + empleado.calcularSalario());
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void buscar() {
        try {
            String codigo = JOptionPane.showInputDialog(this, "Codigo");
            if (codigo == null) return;
            Empleado empleado = sistema.buscar(codigo);
            if (empleado == null) mensaje("No existe");
            else mensaje(empleado.mostrarInformacion());
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void clonar() {
        try {
            Empleado aux = sistema.clonarEmpleado(tabla.getSelectedRow());
            String codigo = JOptionPane.showInputDialog(this, "Codigo del clon");
            if (codigo == null) return;
            aux.setCodigo(codigo);
            copia = aux;
            mensaje("Clonado");
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void registrarClon() {
        try {
            sistema.registrarClon(copia);
            copia = null;
            actualizarTabla();
            mensaje("Registrado");
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void comparar() {
        int filas[] = tabla.getSelectedRows();
        if (filas.length == 2) {
            mensaje(sistema.comparar(sistema.getEmpleado(filas[0]), sistema.getEmpleado(filas[1])));
        } else if (filas.length == 1 && copia != null) {
            mensaje(sistema.comparar(sistema.getEmpleado(filas[0]), copia));
        } else mensaje("Seleccione dos empleados");
    }

    private void maximo() {
        try {
            mensaje(sistema.salarioMaximo().mostrarInformacion());
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void promedio() {
        try {
            mensaje("Promedio: " + sistema.promedio());
        } catch (Exception e) {
            mensaje("Algo salio mal");
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtBase.setText("");
        txtBono.setText("");
        txtDescuento.setText("");
        txtHoras.setText("");
        txtTarifa.setText("");
        cboTipo.setSelectedIndex(0);
        copia = null;
    }

    private void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }
}
