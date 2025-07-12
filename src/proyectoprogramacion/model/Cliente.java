package proyectoprogramacion.model;
import java.util.Random;
/**
 *Esta clase representa a un cliente del banco con nombre, prioridad, tiempo y su nivel de tolerancia.
 * @author Tatiana Urbina y Sebastian Benavides
 */
public class Cliente {
    private static final Random generadorNumerosAleatorios = new Random();
    private static int nextIdTicket=1;
    private final TipoCliente tipo;
    private final int minutosAtencion;
    private int tiempoEsperaFilaMinutos;
    private final int toleranciaMinutos;
    private final String idTicket;
    private final int numeroTicket;
    
    //Constructor, para crear un nuevo cliente con los datos básicos necesarios para el sistema.
    //Se usa para registrar un cleinte en la simulación del banco.
    public Cliente(TipoCliente tipo) {
          this.tipo = tipo;
          this.numeroTicket= nextIdTicket;
          this.idTicket= generarIdTicket(tipo);
          this.tiempoEsperaFilaMinutos= 0;
          this.toleranciaMinutos= generadorNumerosAleatorios.nextInt(146)+5;
          this.minutosAtencion= generadorNumerosAleatorios.nextInt(110)+10;
          
    } 
    /**
     * 
     * @param tipo tipocliente para determinar el sufijo del ticket (A,B,C,D,E,F,G)
     * @return ID del ticket generado
     */
    private String generarIdTicket(TipoCliente tipo){
        String id="Ticket" + "/" + this.numeroTicket + "/" + tipo.getSufijoTicket();
        nextIdTicket++;
        return id;
    }
    //
    public static void resetIdTickets(){
        nextIdTicket=1;
    }
    //
    public void incrementarTiempoEspera(int minutos){
        this.tiempoEsperaFilaMinutos= tiempoEsperaFilaMinutos + minutos;
    }
    //Getters
    public  TipoCliente getTipo() {
        return tipo;
    }
    //
    public String getIdTicket() {
        return idTicket;
    }
    //
    public int getNumeroTicket(){
        return numeroTicket;
    }
    //
    public int getMinutosAtencion() {
        return minutosAtencion;
    }
    //
    public int getToleranciaMinutos() {
        return toleranciaMinutos;
    }
    //
    public int getTiempoEsperaFilaMinutos(){
        return tiempoEsperaFilaMinutos;
    }
}
