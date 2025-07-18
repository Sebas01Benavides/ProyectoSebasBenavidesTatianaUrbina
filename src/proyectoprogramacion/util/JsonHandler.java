/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoprogramacion.util;

import java.io.*;
import java.util.List;
import proyectoprogramacion.model.Cliente;
/**
 *
 * @author tatia
 */
public class JsonHandler {
    private static final String FILE_PATH = "clientes.json";

    public static void guardarClientes(List<Cliente> clientes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Cliente c : clientes) {
                // Guarda cada cliente como línea simple: id,tipo
                writer.println(c.getIdTicket() + "," + c.getTipo().getSufijo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Cliente> cargarClientes() {
        // Implementa la lógica para leer y crear objetos Cliente si quieres,
        // o devuelve una lista vacía por ahora.
        return new java.util.ArrayList<>();
    }
}
