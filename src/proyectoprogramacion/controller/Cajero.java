package proyectoprogramacion.controller;
import proyectoprogramacion.model.Cliente;
import javax.swing.SwingUtilities;
/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 * Esta clase simula el comportamiento de un cajero de banco
 * Cada cajero es un hilo que atiende clientes de la fila del banco
 */
public class Cajero implements Runnable {
    private final String nombre;
    private final Banco banco;
    private volatile boolean cajeroActivo = false;
    private Cliente clienteActual;
    private int tiempoRestanteTramite;
    //Estadisticas necesarias 
    private int totalClientesAtendidos = 0;
    private long tiempoTotalAtencion = 0;
    private long tiempoInicioAtencion = 0; // Para llevar tiempo por cliente

    public interface EstadoCajeroListener {
        void onStatusChange(String nombreCajero, String estado, Cliente cliente);
    }
    private EstadoCajeroListener listener; 
    public Cajero(String nombre, Banco banco) {
        this.nombre = nombre;
        this.banco = banco;
    }

    public void setEstadoCajeroListener(EstadoCajeroListener listener) { 
        this.listener = listener;
    }

    public void startCajero() {
        cajeroActivo = true;
        new Thread(this, nombre).start();
        notifyStatusChange("Esperando cliente...", null);
    }

    public void stopCajero() {
        cajeroActivo = false;
        notifyStatusChange("Inactivo", null);
    }

    @Override
    public void run() {
        while (cajeroActivo) {
            if (clienteActual == null) {
                clienteActual = banco.getNextCliente();
                if (clienteActual != null) {
                    tiempoRestanteTramite = clienteActual.getMinutosAtencion();
                    tiempoInicioAtencion = System.currentTimeMillis(); // Obtiene el tiempo de inicio en que se atiende un cliente
                    notifyStatusChange("Atendiendo a " + clienteActual.getIdTicket() + " (" + clienteActual.getTipo().name() + ")", clienteActual);
                } else {
                    notifyStatusChange("Esperando cliente...", null);
                    try {
                        Thread.sleep(500); //  pausa para evitar consumo de CPU
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        cajeroActivo = false;
                    }
                }
            } else {
                try {
                    Thread.sleep(100); // pausa para simular tiempo real
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    cajeroActivo = false;
                }
            }
        }
    }
    
    public synchronized void avanzarMinutoSimulacion(int minutosSimulados) {
        if (!cajeroActivo) return;

        if (clienteActual != null) {
            tiempoRestanteTramite -= minutosSimulados;
            if (tiempoRestanteTramite <= 0) {
                tiempoTotalAtencion += clienteActual.getMinutosAtencion() * 1000L;
                totalClientesAtendidos++;
                long duracion = System.currentTimeMillis() - tiempoInicioAtencion; // duracion de la sesion con el cliente
                tiempoTotalAtencion += duracion; // Guarda el tiempo total de atencion
                totalClientesAtendidos++;//obtiene el total de clientes atendidos del cajero 
                banco.addClienteAtendido(clienteActual);
                notifyStatusChange("Cliente " + clienteActual.getIdTicket() + " atendido.", clienteActual);
                clienteActual = null;
                tiempoRestanteTramite = 0;
            } else {
                notifyStatusChange("Atendiendo a " + clienteActual.getIdTicket() + 
                                   " (" + clienteActual.getTipo().name() + ") - Restan " + 
                                   tiempoRestanteTramite + "min", clienteActual);
            }
        }
    }
    // 
    private void notifyStatusChange(String estado, Cliente cliente) {
        if (listener != null) {
            SwingUtilities.invokeLater(() -> listener.onStatusChange(nombre, estado, cliente));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }
    
    public boolean isCajeroActivo() {
        return cajeroActivo;
    }
    // Getters para estadísticas
    public int getTotalClientesAtendidos() {
        return totalClientesAtendidos;
    }

    public long getTiempoTotalAtencion() {
        return tiempoTotalAtencion;
    }

    public double getPromedioAtencionSegundos() {
        return totalClientesAtendidos == 0 ? 0 : (tiempoTotalAtencion / 1000.0) / totalClientesAtendidos;
    }
}

