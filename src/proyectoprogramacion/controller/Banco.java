package proyectoprogramacion.controller;
import proyectoprogramacion.model.Cliente;
import proyectoprogramacion.model.ReporteCliente;
import java.util.Comparator; 
import java.util.List;       
import java.util.ArrayList;  
import java.util.PriorityQueue; 
import java.util.LinkedList;
import proyectoprogramacion.model.TipoCliente;


/**
 *
 * @author Tatiana Urbina y Sebastian Benavides 
 */
public class Banco{//Representamos el banco con una fila de clientes y con cajeros
    public static final int MAX_FILA=25;
    private final PriorityQueue<Cliente> filaClientes;//Cola de prioridad que ordena a los clientes
    private final List<Cliente> clientesAtendidos;//En las siguientes listas se asignam los clientres que fueron atendidos o se fueron del banco
     private List<Cajero> listaCajeros = new ArrayList<>();
    private final List<ReporteCliente> clientesRetirados;
    //Este siguiente atributo lleva el control del ultimo ticket que se esta atendiendo en el sistema
    private int ticketActual=0;
    /**
     * Metodo constructor del banco que prepara las filas y listas
     */
    public Banco (){
        this.filaClientes= new PriorityQueue<>(new Comparator<Cliente>() { //Crea la cola con prioridad
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
    //Inicializa la lista de cajeros (del 1 al 5)
    public void inicializarCajeros() {
        listaCajeros.clear();
        for (int i = 1; i <= 5; i++) {
            Cajero cajero = new Cajero("Cajero" + i, this);
            listaCajeros.add(cajero);
        }
    }
    //Agrega un cliente a la fila si no está llena
    public boolean addCliente(Cliente nuevoCliente){
        if (filaClientes.size()>=MAX_FILA){
            return false; //Indica que la fila esta llena
        }
        filaClientes.offer(nuevoCliente);
        return true;//CLiente agregado
    }
    /**
     * Obtiene y remueve al siguiente cliente de la fila para que sea atendido
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
    public int getCantidadClientesEnFila(){
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
            // Si supera tolerancia, se retira y se crea reporte
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
                filaClientes.remove(cliente);
                       
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
    //Metodos para obtener reportes y cajeros

    public List<Cajero> getListaCajeros() {
        return listaCajeros;
    }
    // devuelve listaclientes que se fueron sin ser atendidos
    public List<ReporteCliente> getClientesNoAtendidos(){
        return clientesRetirados;
    }
    //devuelve lista de clientes que si fueron atendidos
    public List<Cliente> getClientesAtendidos(){
        return clientesAtendidos;
    }
    // devuelve lista actual de la fila
    public List<Cliente> getClientesEnFila() {
     return new ArrayList<>(filaClientes);
    }
    //Inicia la fila con clientes aleatorios hasta llenar toda la fila
   public void inicializarClientes() {
    for (int i = 0; i < MAX_FILA; i++) {
        TipoCliente tipoAleatorio = TipoCliente.getRandomTipoCliente();
        Cliente cliente = new Cliente(tipoAleatorio);
        addCliente(cliente);
        }
    }
   public List<Cajero> getCajeros() {
    return listaCajeros;
    }
}