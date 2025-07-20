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
// Clase auxiliar para manejo de JSON
public class JsonHandler {

    public static String convertirClientesAJson(List<Cliente> clientes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            sb.append("{")
              .append("\"numeroTicket\":").append(c.getNumeroTicket()).append(",")
              .append("\"tipoCliente\":\"").append(c.getTipo().toString()).append("\"")
              .append("}");
            if (i < clientes.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}

