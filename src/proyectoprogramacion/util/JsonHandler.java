package proyectoprogramacion.util;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import proyectoprogramacion.model.Cliente;
import proyectoprogramacion.model.TipoCliente;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */

public class JsonHandler {

    private static final Gson gson = new Gson();

    // Convierte JSON a lista de Clientes
    public static List<Cliente> convertirJsonAClientes(Reader reader) {
        //Lee la lista de objetos ClienteJson 
        List<ClienteJson> clientesJson = gson.fromJson(reader, new TypeToken<List<ClienteJson>>(){}.getType());

        // Convertimos ClienteJson a Cliente
        return clientesJson.stream().map(cj -> {
            TipoCliente tipo = TipoCliente.valueOf(cj.tipoCliente);
            Cliente cliente = new Cliente(tipo);
            cliente.setNumeroTicket(cj.numeroTicket);  
            return cliente;
        }).collect(Collectors.toList());
    }

    // Convierte lista de Clientes a JSON
    public static String convertirClientesAJson(List<Cliente> clientes) {
        //Convierte la lista de Cliente a lista de ClienteJson para solo incluir los campos necesarios
        List<ClienteJson> clientesJson = clientes.stream()
            .map(c -> new ClienteJson(c.getNumeroTicket(), c.getTipo().toString()))
            .collect(Collectors.toList());

        // Gson convierte la lista a JSON
        return gson.toJson(clientesJson);
    }

    // Clase auxiliar para JSON 
    private static class ClienteJson {
        int numeroTicket;
        String tipoCliente;

        ClienteJson(int numeroTicket, String tipoCliente) {
            this.numeroTicket = numeroTicket;
            this.tipoCliente = tipoCliente;
        }
    }
}
