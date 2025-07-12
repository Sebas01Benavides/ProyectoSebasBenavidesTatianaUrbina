package proyectoprogramacion.controller;
import java.util.PriorityQueue;
import proyectoprogramacion.model.Cliente;
import proyectoprogramacion.model.TipoCliente;
import proyectoprogramacion.model.ReporteCliente;
import java.util.Comparator; 
import java.util.List;       
import java.util.ArrayList;  
import java.util.PriorityQueue; 
import java.util.LinkedList;

/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class Banco{
    public static final int MAX_FILA=25;
    private final PriorityQueue<Cliente> filaClientes;
    //En las siguientes listas se asignam los clientres que fueron atendidos o se fueron del banco
    private final List<Cliente> clientesAtendidos;
    private final List<ReporteCliente> clientesRetirados;
    //Este siguiente atributo lleva el control del ultimo ticket que se esta atendiendo en el sistema
    private int ticketActual=0;
    /**
     * Metodo constructor del banco que prepara las filas y listas
     */
    public Banco (){
        this.filaClientes= new PriorityQueue<>(new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                //Compara la prioridad segun el numero
                int comparacionPrioridad= Integer.compare(c1.getTipo().getPrioridad(), c2.getTipo().getPrioridad());
                //Si es la mimsa prioridad en cuanto a numero, busca cual cliente llego primero
                if(comparacionPrioridad==0){
                    return Integer.compare(c1.getNumeroTicket(), c2.getNumeroTicket());
                }
                return comparacionPrioridad; 
            }
        });
        //Inicializamos listas para guardar a los clientes
        this.clientesAtendidos=new LinkedList<>();
        this.clientesRetirados=new LinkedList<>();
    }
    public boolean addCliente(Cliente nuevoCliente){
        if (filaClientes.size()>=MAX_FILA){
            return false; //Indica que la fila esta llena
        }
        filaClientes.offer(nuevoCliente);
        return true;//CLiente agregado
    }
    /**
     * Este metodo lo debe llamar un cajero para obtener el siguiente cliente por atender
     * @return 
     */
    public synchronized Cliente getNextCliente(){
        Cliente next=filaClientes.poll();
        if(next!=null){
            if(next.getNumeroTicket()>ticketActual){
                ticketActual=next.getNumeroTicket();
            }
        }
        return next; //devuelve el cliente o un null en caso de no haber
    }
    // este metodo retorna la cantidad de clientes actuales en la fila
    public int getClienteEnFila(){
        return filaClientes.size();
    }
    //este metodo retorna el ult8imo ticket atendido
    public int getTicketActual(){
        return ticketActual;
    }
    //Este metodo mueve a la linkedlist clientesAtendidos a los clientes que ya fueron atendidos
    public void addClienteAtendido(Cliente cliente){
        clientesAtendidos.add(cliente);
    }
    //
    public List<ReporteCliente> actualizarTiemposEsperaYTolerancia(int minutos){
        List<Cliente> clientesARevisar= new ArrayList<>(filaClientes);
        List<ReporteCliente> clientesQueSeFueron= new ArrayList<>();
        
        for (Cliente cliente : clientesARevisar){
            cliente.incrementarTiempoEspera(minutos); //el cliente espera mas tiempo
            if (cliente.getTiempoEsperaFilaMinutos()>cliente.getToleranciaMinutos()){
                ReporteCliente reporte=new ReporteCliente(
                        cliente.getIdTicket(),
                        cliente.getTipo().toString(), 
                        cliente.getMinutosAtencion(), 
                        cliente.getToleranciaMinutos(),
                        cliente.getTiempoEsperaFilaMinutos()
                );
                clientesRetirados.add(reporte);
                clientesQueSeFueron.add(reporte);
                       
            }
        }
        return clientesQueSeFueron;
    }
    //Reinicia la simulacion
    public void reset(){
        filaClientes.clear();
        clientesAtendidos.clear();
        clientesRetirados.clear();
        Cliente.resetIdTickets();
        ticketActual=0;
                
    }
    //Metodos para obtener reportes
    
    public List<ReporteCliente> getClientesNoAtendidos(){//clientes que se fueron sin ser atendidos
        return clientesRetirados;
    }
    public List<Cliente> getClientesAtendidos(){
        return clientesAtendidos;//lista de clientes que si fueron atendidos
    }
    public List<Cliente> getFilaClientesActual(){
        return new ArrayList<>(filaClientes); //inncesesario
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}