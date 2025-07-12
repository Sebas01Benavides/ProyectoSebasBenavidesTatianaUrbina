
package proyectoprogramacion.controller;

import proyectoprogramacion.model.Cliente;
import proyectoprogramacion.model.TipoCliente;
import proyectoprogramacion.model.ReporteCliente; // Asegúrate de tener esta clase en el modelo

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue; // Usaremos PriorityQueue para gestionar prioridades
import java.util.Queue;
import proyectoprogramacion.model.Cliente;

/**
 * Clase que gestiona la lógica central de la simulación del banco.
 * Se encarga de la fila de clientes, el despacho de clientes a los cajeros,
 * la gestión del tiempo de espera y la tolerancia, y la recolección de reportes.
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class Banco {

    public static final int MAX_CLIENTES_FILA = 25; // Límite máximo de clientes en fila [cite: 27]

    // La fila de clientes, ahora como PriorityQueue para manejar prioridades
    // Definimos un comparador para ordenar clientes: primero por prioridad (TipoCliente), luego por tiempo de llegada
    private final PriorityQueue<Cliente> filaClientes;

    // Listas para almacenar clientes atendidos y retirados para los reportes finales
    private final List<Cliente> clientesAtendidos;
    private final List<ReporteCliente> clientesRetirados; // Almacenamos ReporteCliente para clientes que se van por tolerancia

    public Banco() {
        // Inicializa la PriorityQueue con un comparador personalizado
        this.filaClientes = new PriorityQueue<>(new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                // Las prioridades se definen por el orden del enum (ordinal)
                // A menor ordinal, mayor prioridad (ej: ADULTO_MAYOR es 0, EMBARAZADA es 1, etc.)
                // Entonces, queremos que los de menor ordinal vayan primero.
                int comparacionPrioridad = Integer.compare(c1.getTipo().ordinal(), c2.getTipo().ordinal());

                // Si tienen la misma prioridad, el que llegó primero va primero (FIFO)
                // Usamos el idTiquete para simular el orden de llegada si no hay un timestamp
                if (comparacionPrioridad == 0) {
                    // Extraemos el número del ID para comparar por orden de llegada
                    int id1 = Integer.parseInt(c1.getIdTiquete().split("-")[1]);
                    int id2 = Integer.parseInt(c2.getIdTiquete().split("-")[1]);
                    return Integer.compare(id1, id2);
                }
                return comparacionPrioridad;
            }
        });

        this.clientesAtendidos = new LinkedList<>();
        this.clientesRetirados = new LinkedList<>();
    }

    /**
     * Intenta agregar un cliente a la fila.
     *
     * @param cliente El cliente a agregar.
     * @return true si el cliente fue agregado exitosamente, false si la fila está llena.
     */
    public boolean addCliente(Cliente cliente) {
        if (filaClientes.size() < MAX_CLIENTES_FILA) {
            filaClientes.offer(cliente); // Agrega a la PriorityQueue, que lo ordenará automáticamente
            return true;
        }
        return false;
    }

    /**
     * Permite a un cajero obtener el siguiente cliente de la fila.
     * Este método es synchronized porque múltiples cajeros (hilos) intentarán acceder a la fila.
     *
     * @return El siguiente Cliente en la fila según la prioridad, o null si la fila está vacía.
     */
    public synchronized Cliente getNextCliente() {
        return filaClientes.poll(); // poll() remueve y devuelve el elemento de mayor prioridad (o el siguiente)
    }

    /**
     * Retorna la cantidad actual de clientes en la fila.
     */
    public int getClientesEnFila() {
        return filaClientes.size();
    }

    /**
     * Agrega un cliente a la lista de atendidos.
     * @param cliente El cliente que ha sido atendido.
     */
    public void addClienteAtendido(Cliente cliente) {
        clientesAtendidos.add(cliente);
    }

    /**
     * Actualiza el tiempo de espera de todos los clientes en la fila y verifica su tolerancia.
     * Los clientes que exceden su tolerancia son movidos a la lista de retirados.
     * Este método es llamado periódicamente por el Timer de la GUI.
     *
     * @param minutosSimulados La cantidad de minutos que ha transcurrido en la simulación.
     * @return Una lista de ReporteCliente de los clientes que se retiraron en esta actualización.
     */
    public List<ReporteCliente> actualizarTiemposEsperaYVerificarTolerancia(int minutosSimulados) {
        List<Cliente> clientesARevisar = new ArrayList<>(filaClientes); // Copia para evitar ConcurrentModificationException
        List<ReporteCliente> clientesQueSeFueron = new ArrayList<>();

        for (Cliente cliente : clientesARevisar) {
            cliente.incrementarTiempoEspera(minutosSimulados);
            if (cliente.getTiempoEsperaEnFilaMinutos() > cliente.getToleranciaMinutos()) {
                // El cliente se fue
                filaClientes.remove(cliente); // Quitar de la fila
                ReporteCliente reporte = new ReporteCliente(
                    cliente.getIdTiquete(),
                    cliente.getTipo().toString(),
                    cliente.getTiempoTramiteMinutos(),
                    cliente.getToleranciaMinutos(),
                    cliente.getTiempoEsperaEnFilaMinutos() // Tiempo total de espera al irse
                );
                clientesRetirados.add(reporte);
                clientesQueSeFueron.add(reporte);
            }
        }
        return clientesQueSeFueron;
    }

    /**
     * Reinicia el estado del banco para una nueva simulación.
     */
    public void reset() {
        filaClientes.clear();
        clientesAtendidos.clear();
        clientesRetirados.clear();
        // Reiniciar el contador de IDs de Cliente si fuera necesario
        // Cliente.resetNextIdTiquete(); // Asumiendo que Cliente tiene este método estático
    }

    // --- Métodos para reportes (pueden ser más complejos con promedios, etc.) ---

    public List<ReporteCliente> getClientesNoAtendidos() {
        return clientesRetirados;
    }

    public List<Cliente> getClientesAtendidos() {
        return clientesAtendidos;
    }

    // Otros getters si son necesarios para consultas específicas del proyecto [cite: 38, 39, 40, 41, 42, 43, 44, 45]
    // Ejemplo:
    // public int getTotalClientesAtendidos() { return clientesAtendidos.size(); }
    // public int getTotalClientesRetirados() { return clientesRetirados.size(); }
}