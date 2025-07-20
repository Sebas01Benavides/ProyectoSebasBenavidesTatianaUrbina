package proyectoprogramacion.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import proyectoprogramacion.controller.Cajero;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class ReporteCajeros extends JDialog {//Este JDialog muestra el reporte de estadisticas de los cajeros

    public ReporteCajeros(Frame parent, List<Cajero> cajeros) {
        super(parent, "Reporte de Cajeros", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        String[] columnas = {"Nombre", "Clientes Atendidos", "Tiempo Total (s)", "Promedio Atención (s)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        // Crea una fila de datos para cada cajero
        for (Cajero cajero : cajeros) {
            Object[] fila = {
                cajero.getNombre(),
                cajero.getTotalClientesAtendidos(),
                // Tiempo total de atención, convertido de milisegundos a segundoss
                String.format("%.2f",cajero.getTiempoTotalAtencion() / 1000.0),
                // Formatea el promedio de atención a 2 decimales
                String.format("%.2f", cajero.getPromedioAtencionSegundos())
            };//Añade la fila de datos al modelo de la tabla
            modelo.addRow(fila);
        }
        // Crea una tabla Swing 
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        // Agregamos un scroll 
        getContentPane().add(scroll, BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(cerrar);
        getContentPane().add(panelBoton, BorderLayout.SOUTH);
    }
}
